package com.shimizukenta.secs.hsmsss;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.CompletionHandler;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

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
							
							completionAction(channel);
						}
						catch ( IOException e ) {
							notifyLog(e);
						}
						catch ( InterruptedException ignore ) {
						}
						finally {
							
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
		
		final ActiveInnerConnection conn = new ActiveInnerConnection(channel);
		
		try {
			
			final Collection<Callable<Void>> tasks = Arrays.asList(
					() -> {
						try {
							conn.reading();
						}
						catch ( InterruptedException ignore ) {
						}
						return null;
					},
					() -> {
						try {
							conn.mainTask();
						}
						catch ( InterruptedException ignore ) {
						}
						return null;
					});
			
			this.executeInvokeAny(tasks);
		}
		catch ( ExecutionException e ) {
			
			Throwable t = e.getCause();
			
			if ( t instanceof RuntimeException ) {
				throw (RuntimeException)t;
			}
			
			this.notifyLog(t);
		}
	}
	
	protected class ActiveInnerConnection extends AbstractInnerConnection {

		protected ActiveInnerConnection(AsynchronousSocketChannel channel) {
			super(channel);
		}
		
		protected void mainTask() throws InterruptedException {
			
			notifyHsmsSsCommunicateStateChange(HsmsSsCommunicateState.NOT_SELECTED);
			
			try {
				HsmsSsMessageSelectStatus ss = send(createSelectRequest())
						.map(HsmsSsMessageSelectStatus::get)
						.orElse(HsmsSsMessageSelectStatus.NOT_SELECT_RSP);
			
				switch ( ss ) {
				case SUCCESS:
				case ACTIVED: {
					
					/* to next Loop */
					break;
				}
				default: {
					return;
				}
				}
			}
			catch ( SecsException e ) {
				notifyLog(e);
				return;
			}
			
			try {
				
				if ( ! addSelectedConnection(this) ) {
					return;
				}
				
				notifyHsmsSsCommunicateStateChange(HsmsSsCommunicateState.SELECTED);
				
				final Collection<Callable<Void>> tasks = Arrays.asList(
						() -> {
							try {
								this.linktesting();
							}
							catch ( InterruptedException ignore ) {
							}
							return null;
						},
						() -> {
							try {
								this.receivingMsgTask();
							}
							catch ( InterruptedException ignore ) {
							}
							catch ( SecsException e ) {
								notifyLog(e);
							}
							return null;
						});
				
				executeInvokeAny(tasks);
			}
			catch ( ExecutionException e ) {
				
				Throwable t = e.getCause();
				
				if ( t instanceof RuntimeException ) {
					throw (RuntimeException)t;
				}
				
				notifyLog(t);
			}
			finally {
				notifyHsmsSsCommunicateStateChange(HsmsSsCommunicateState.NOT_CONNECTED);
				removeSelectedConnection(this);
			}
			
		}
		
		protected void receivingMsgTask() throws InterruptedException, SecsException {
			
			for ( ;; ) {
				
				final HsmsSsMessage msg = this.takeReceiveMessage();
				
				switch ( HsmsSsMessageType.get(msg) ) {
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
					
					return;
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
		}
	}
	
}
