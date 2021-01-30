package com.shimizukenta.secs.hsmsss;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.CompletionHandler;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;

import com.shimizukenta.secs.SecsException;

/**
 * This abstract class is implementation of HSMS-SS-Active-Communicator(SEMI-E37.1).
 * 
 * <p>
 * This class is called from {@link HsmsSsCommunicator#newInstance(HsmsSsCommunicatorConfig)<br />
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractHsmsSsActiveCommunicator extends AbstractHsmsSsCommunicator {

	public AbstractHsmsSsActiveCommunicator(HsmsSsCommunicatorConfig config) {
		super(Objects.requireNonNull(config));
	}
	
	@Override
	public void open() throws IOException {
		
		if ( hsmsSsConfig().protocol().get() != HsmsSsProtocol.ACTIVE ) {
			throw new IOException("HsmsSsCommunicatorConfig#protocol is not ACTIVE");
		}
		
		super.open();
		
		executeLoopTask(() -> {
			
			activeCircuit();
			
			sendReplyManager.clear();
			
			hsmsSsConfig().timeout().t5().sleep();
		});
	}
	
	@Override
	public void close() throws IOException {
		super.close();
	}
	
	protected void activeCircuit() throws InterruptedException {
		
		try (
				AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
				) {
			
			final SocketAddress socketAddr = hsmsSsConfig().socketAddress().getSocketAddress();
			
			try {
				notifyLog(HsmsSsConnectionLog.tryConnect(socketAddr));

				channel.connect(socketAddr, null, new CompletionHandler<Void, Void>(){
					
					@Override
					public void completed(Void none, Void attachment) {
						
						SocketAddress local = null;
						SocketAddress remote = null;
						
						try {
							local = channel.getLocalAddress();
							remote = channel.getRemoteAddress();
							
							notifyLog(HsmsSsConnectionLog.connected(local, remote));
							
							if ( ! addChannel(channel) ) {
								return;
							}
							
							completionAction(channel);
						}
						catch ( IOException e ) {
							notifyLog(e);
						}
						catch ( InterruptedException ignore ) {
						}
						finally {
							
							removeChannel(channel);
							
							try {
								channel.shutdownOutput();
							}
							catch (IOException giveup) {
							}
							
							synchronized ( channel ) {
								channel.notifyAll();
							}
							
							notifyLog(HsmsSsConnectionLog.closed(local, remote));
						}
					}
					
					@Override
					public void failed(Throwable t, Void attachment) {
						
						if ( ! (t instanceof ClosedChannelException) ) {
							notifyLog(t);
						}
						
						synchronized ( channel ) {
							channel.notifyAll();
						}
					}
				});
				
				synchronized ( channel ) {
					channel.wait();
				}
			}
			finally {
				notifyHsmsSsCommunicateStateChange(HsmsSsCommunicateState.NOT_CONNECTED);
			}
		}
		catch ( IOException e ) {
			notifyLog(e);
		}
	}
	
	private void completionAction(AsynchronousSocketChannel channel) throws InterruptedException {
		
		notifyHsmsSsCommunicateStateChange(HsmsSsCommunicateState.NOT_SELECTED);
		
		final BlockingQueue<HsmsSsMessage> queue = new LinkedBlockingQueue<>();
		
		final HsmsSsByteReader reader = new HsmsSsByteReader(AbstractHsmsSsActiveCommunicator.this, channel);
		final HsmsSsCircuitAssurance linktest = new HsmsSsCircuitAssurance(AbstractHsmsSsActiveCommunicator.this);
		
		reader.addHsmsSsMessageReceiveListener(msg -> {
			sendReplyManager.put(msg).ifPresent(queue::offer);
		});
		
		reader.addHsmsSsMessageReceiveListener(msg -> {
			linktest.reset();
		});
		
		final Callable<Void> mainTask = () -> {
			
			try {
				HsmsSsMessageSelectStatus ss = send(createSelectRequest())
						.map(HsmsSsMessageSelectStatus::get)
						.orElse(HsmsSsMessageSelectStatus.NOT_SELECT_RSP);
			
				switch ( ss ) {
				case SUCCESS:
				case ACTIVED: {
					
					notifyHsmsSsCommunicateStateChange(HsmsSsCommunicateState.SELECTED);
					break;
				}
				default: {
					return null;
				}
				}
			}
			catch ( SecsException e ) {
				notifyLog(e);
				return null;
			}
			catch ( InterruptedException e ) {
				return null;
			}
			
			final Callable<Void> selectTask = () -> {
				
				try {
					for ( ;; ) {
						
						HsmsSsMessage msg = queue.take();
						
						HsmsSsMessageType mt = HsmsSsMessageType.get(msg);
						
						try {
							switch ( mt ) {
							case DATA: {
								
								notifyReceiveMessage(msg);
								break;
							}
							case SELECT_REQ: {
								
								send(createRejectRequest(msg, HsmsSsMessageRejectReason.NOT_SUPPORT_TYPE_S));
								break;
							}
							case LINKTEST_REQ: {
								
								send(createLinktestResponse(msg));
								break;
							}
							case SEPARATE_REQ: {
								
								return null;
								/* break; */
							}
							case SELECT_RSP:
							case DESELECT_REQ:
							case DESELECT_RSP:
							case LINKTEST_RSP:
							case REJECT_REQ:
							default: {
								
								/* ignore */
							}
							}
						}
						catch ( SecsException e ) {
							notifyLog(e);
						}
					}
				}
				catch ( InterruptedException ignore ) {
				}
				
				return null;
			};
			
			try {
				executeInvokeAny(
						Arrays.asList(
								linktest,
								selectTask
								)
						);
			}
			catch ( InterruptedException ignore ) {
			}
			catch ( ExecutionException e ) {
				
				Throwable t = e.getCause();
				
				if ( t instanceof RuntimeException ) {
					throw (RuntimeException)t;
				}
				
				notifyLog(t);
			}
			
			return null;
		};
		
		try {
			executeInvokeAny(
					Arrays.asList(
							reader,
							mainTask
							)
					);
		}
		catch ( ExecutionException e ) {
			
			Throwable t = e.getCause();
			
			if ( t instanceof RuntimeException ) {
				throw (RuntimeException)t;
			}
			
			if ( ! (t instanceof ClosedChannelException) ) {
				notifyLog(t);
			}
		}
	}
	
}
