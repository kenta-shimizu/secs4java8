package com.shimizukenta.secs.hsmsss;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.CompletionHandler;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeoutException;

import com.shimizukenta.secs.SecsException;

/**
 * This abstract class is implementation of HSMS-SS-Passive Communicator(SEMI-E37.1).
 * 
 * <p>
 * This class is called from {@link HsmsSsCommunicator#newInstance(HsmsSsCommunicatorConfig)<br />
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractHsmsSsPassiveCommunicator extends AbstractHsmsSsCommunicator {

	private AsynchronousServerSocketChannel server;
	
	public AbstractHsmsSsPassiveCommunicator(HsmsSsCommunicatorConfig config) {
		super(Objects.requireNonNull(config));
		
		server = null;
	}
	
	@Override
	public void open() throws IOException {
		
		if ( hsmsSsConfig().protocol().get() != HsmsSsProtocol.PASSIVE ) {
			throw new IOException("HsmsSsCommunicatorConfig#protocol is not PASSIVE");
		}
		
		super.open();
		
		passiveOpen();
	}
	
	@Override
	public void close() throws IOException {
		
		IOException ioExcept = null;
		
		try {
			synchronized ( this ) {
				if ( isClosed() ) {
					return;
				}
				
				super.close();
			}
		}
		catch ( IOException e ) {
			ioExcept = e;
		}
		
		synchronized ( this ) {
			
			if ( server != null ) {
				
				SocketAddress addr = null;
				
				try {
					addr = server.getLocalAddress();
				}
				catch ( IOException giveup ) {
				}
				
				try {
					server.close();
					
					if ( addr != null ) {
						notifyLog(HsmsSsPassiveBindLog.closed(addr));
					}
				}
				catch ( IOException e ) {
					ioExcept = e;
				}
			}
		}
		
		if ( ioExcept != null) {
			throw ioExcept;
		}
	}
	
	protected void passiveOpen() throws IOException {
		
		synchronized ( this ) {
			this.server = AsynchronousServerSocketChannel.open();
		}
		
		final SocketAddress socketAddr = hsmsSsConfig().socketAddress().getSocketAddress();
		
		notifyLog(HsmsSsPassiveBindLog.tryBind(socketAddr));
		
		server.setOption(StandardSocketOptions.SO_REUSEADDR, true);
		server.bind(socketAddr);
		
		notifyLog(HsmsSsPassiveBindLog.binded(socketAddr));
		
		server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
			
			@Override
			public void completed(AsynchronousSocketChannel channel, Void attachment) {
				server.accept(attachment, this);
				
				SocketAddress local = null;
				SocketAddress remote = null;
				
				try {
					local = channel.getLocalAddress();
					remote = channel.getRemoteAddress();
					
					notifyLog(HsmsSsConnectionLog.accepted(local, remote));
					
					completedAction(channel);
				}
				catch ( IOException e ) {
					notifyLog(e);
				}
				catch ( InterruptedException ignore ) {
				}
				finally {
					
					sendReplyManager.clear();
					
					removeChannel(channel);
					
					try {
						channel.shutdownOutput();
					}
					catch ( IOException ignore ) {
					}
					
					try {
						channel.close();
					}
					catch ( IOException e ) {
						notifyLog(e);
					}
					
					notifyLog(HsmsSsConnectionLog.closed(local, remote));
				}
			}
			
			@Override
			public void failed(Throwable t, Void attachment) {
				
				if ( ! (t instanceof ClosedChannelException) ) {
					notifyLog(t);
				}
			}
		});
	}
	
	protected void completedAction(AsynchronousSocketChannel channel) throws InterruptedException {
		
		final BlockingQueue<HsmsSsMessage> queue = new LinkedBlockingQueue<>();
		
		final HsmsSsByteReader reader = new HsmsSsByteReader(AbstractHsmsSsPassiveCommunicator.this, channel);
		final HsmsSsCircuitAssurance linktest = new HsmsSsCircuitAssurance(AbstractHsmsSsPassiveCommunicator.this);
		
		reader.addHsmsSsMessageReceiveListener(msg -> {
			sendReplyManager.put(msg).ifPresent(queue::offer);
		});
		
		reader.addHsmsSsMessageReceiveListener(msg -> {
			linktest.reset();
		});
		
		final Callable<Void> mainTask = () -> {
			
			final Callable<Boolean> connectTask = () -> {
				
				try {
					/* NOT_SELECTED */
					
					for ( ;; ) {
						
						HsmsSsMessage msg = queue.take();
						
						HsmsSsMessageType mt = HsmsSsMessageType.get(msg);
						
						try {
							switch ( mt ) {
							case DATA: {
								
								send(channel, createRejectRequest(msg, HsmsSsMessageRejectReason.NOT_SELECTED));
								break;
							}
							case SELECT_REQ: {
								
								boolean f = addChannel(channel);
								
								if ( f /* success */) {
									
									send(channel, createSelectResponse(msg, HsmsSsMessageSelectStatus.SUCCESS));
									
									return Boolean.TRUE;
									
								} else {
									
									send(channel, createSelectResponse(msg, HsmsSsMessageSelectStatus.ALREADY_USED));
								}
								
								break;
							}
							case LINKTEST_REQ: {
								
								send(channel, createLinktestResponse(msg));
								break;
							}
							case SEPARATE_REQ: {
								
								return Boolean.FALSE;
								/* break; */
							}
							case SELECT_RSP:
							case DESELECT_RSP:
							case LINKTEST_RSP:
							case REJECT_REQ: {
								
								send(channel, createRejectRequest(msg, HsmsSsMessageRejectReason.TRANSACTION_NOT_OPEN));
								break;
							}
							case DESELECT_REQ:
							default: {
								
								if ( HsmsSsMessageType.supportSType(msg) ) {
									
									if ( ! HsmsSsMessageType.supportPType(msg) ) {
										
										send(channel, createRejectRequest(msg, HsmsSsMessageRejectReason.NOT_SUPPORT_TYPE_P));
									}
									
								} else {
									
									send(channel, createRejectRequest(msg, HsmsSsMessageRejectReason.NOT_SUPPORT_TYPE_S));
								}
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
				
				return Boolean.FALSE;
			};
			
			try {
				
				boolean f = executeInvokeAny(
						Arrays.asList(connectTask),
						hsmsSsConfig().timeout().t7()
						);
				
				if ( f ) {
					/* selected */
					notifyHsmsSsCommunicateStateChange(HsmsSsCommunicateState.SELECTED);
					
				} else {
					
					/* select faield */
					return null;
				}
				
			}
			catch ( TimeoutException e ) {
				notifyLog(new HsmsSsTimeoutT7Exception(e));
				return null;
			}
			catch ( InterruptedException ignore ) {
				return null;
			}
			catch ( ExecutionException e ) {
				
				Throwable t = e.getCause();
				
				if ( t instanceof RuntimeException ) {
					throw (RuntimeException)t;
				}
				
				notifyLog(t);
				
				return null;
			}
			
			final Callable<Void> selectTask = () -> {
				
				try {
					/* SELECTED */
					
					for ( ;; ) {
						
						HsmsSsMessage msg = queue.take();
						
						HsmsSsMessageType mt = HsmsSsMessageType.get(msg);
						
						try {
							switch ( mt ) {
							case DATA : {
								
								notifyReceiveMessage(msg);
								break;
							}
							case SELECT_REQ: {
								
								send(channel, createSelectResponse(msg, HsmsSsMessageSelectStatus.ACTIVED));
								break;
							}
							case LINKTEST_REQ: {
								
								send(channel, createLinktestResponse(msg));
								break;
							}
							case SEPARATE_REQ: {
								
								return null;
								/* break; */
							}
							case SELECT_RSP:
							case DESELECT_RSP:
							case LINKTEST_RSP:
							case REJECT_REQ: {
								
								send(channel, createRejectRequest(msg, HsmsSsMessageRejectReason.TRANSACTION_NOT_OPEN));
								break;
							}
							case DESELECT_REQ:
							default: {
								
								if ( HsmsSsMessageType.supportSType(msg) ) {
									
									if ( ! HsmsSsMessageType.supportPType(msg) ) {
										
										send(channel, createRejectRequest(msg, HsmsSsMessageRejectReason.NOT_SUPPORT_TYPE_P));
									}
									
								} else {
									
									send(channel, createRejectRequest(msg, HsmsSsMessageRejectReason.NOT_SUPPORT_TYPE_S));
								}
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
			finally {
				notifyHsmsSsCommunicateStateChange(HsmsSsCommunicateState.NOT_CONNECTED);
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
			
			notifyLog(t);
		}
	}
	
}
