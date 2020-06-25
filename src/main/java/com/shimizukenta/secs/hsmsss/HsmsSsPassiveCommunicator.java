package com.shimizukenta.secs.hsmsss;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.shimizukenta.secs.SecsException;

public class HsmsSsPassiveCommunicator extends HsmsSsCommunicator {

	private AsynchronousServerSocketChannel server;
	
	public HsmsSsPassiveCommunicator(HsmsSsCommunicatorConfig config) {
		super(Objects.requireNonNull(config));
		
		server = null;
	}
	
	@Override
	public void open() throws IOException {
		
		if ( hsmsSsConfig().protocol() != HsmsSsProtocol.PASSIVE ) {
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
				
				try {
					server.close();
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
		
		try {
			synchronized ( this ) {
				this.server = AsynchronousServerSocketChannel.open();
			}
			
			SocketAddress socketAddr = hsmsSsConfig().socketAddress();
			
			String socketAddrInfo = socketAddr.toString();
			
			server.setOption(StandardSocketOptions.SO_REUSEADDR, true);
			server.bind(socketAddr);
			
			notifyLog("HsmsSsPassiveCommunicator#binded", socketAddrInfo);
			
			server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
				
				@Override
				public void completed(AsynchronousSocketChannel channel, Void attachment) {
					completedAction(server, this, channel, attachment);
				}

				@Override
				public void failed(Throwable t, Void attachment) {
					notifyLog("HsmsSsPassiveCommunicator AsynchronousSeverSocketChannel#accept failed", t);
				}
			});
		}
		catch ( IOException e ) {
			notifyLog(e);
			throw e;
		}
	}
	
	protected void completedAction(
			AsynchronousServerSocketChannel server
			, CompletionHandler<AsynchronousSocketChannel, Void> handler
			, AsynchronousSocketChannel channel
			, Void attachment) {
		
		server.accept(null, handler);
		
		String channelString = channel.toString();
		
		notifyLog("HsmsSsPassiveCommunicator channel#accept", channelString);
		
		final BlockingQueue<HsmsSsMessage> queue = new LinkedBlockingQueue<>();
		
		final HsmsSsByteReader reader = new HsmsSsByteReader(HsmsSsPassiveCommunicator.this, channel);
		final HsmsSsCircuitAssurance linktest = new HsmsSsCircuitAssurance(HsmsSsPassiveCommunicator.this);
		
		reader.addHsmsSsMessageReceiveListener(msg -> {
			sendReplyManager.put(msg).ifPresent(queue::offer);
		});
		
		reader.addHsmsSsMessageReceiveListener(msg -> {
			linktest.reset();
		});
		
		Collection<Callable<Object>> tasks = Arrays.asList(
				reader,
				() -> {
					
					Collection<Callable<Boolean>> connectTasks = Arrays.asList(
							() -> {
								
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
											case LINKTEST_RSP:
											case REJECT_REQ: {
												
												send(channel, createRejectRequest(msg, HsmsSsMessageRejectReason.TRANSACTION_NOT_OPEN));
												break;
											}
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
							});
					
					try {
						
						long t7 = (long)(hsmsSsConfig().timeout().t7() * 1000.0F);
						boolean f = executorService().invokeAny(connectTasks, t7, TimeUnit.MILLISECONDS);
						
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
					catch ( RejectedExecutionException e ) {
						if ( ! isClosed() ) {
							throw e;
						}
						return null;
					}
					catch ( ExecutionException e ) {
						
						Throwable t = e.getCause();
						
						if ( t instanceof RuntimeException ) {
							throw (RuntimeException)t;
						}
						
						notifyLog(e);
						return null;
					}
					
					Collection<Callable<Object>> selectTasks = Arrays.asList(
							linktest,
							() -> {
								
								try {
									/* SELECTED */
									
									for ( ;; ) {
										
										HsmsSsMessage msg = queue.take();
										
										HsmsSsMessageType mt = HsmsSsMessageType.get(msg);
										
										try {
											switch ( mt ) {
											case DATA : {
												
												putReceiveDataMessage(msg);
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
											case LINKTEST_RSP:
											case REJECT_REQ: {
												
												send(channel, createRejectRequest(msg, HsmsSsMessageRejectReason.TRANSACTION_NOT_OPEN));
												break;
											}
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
							});
					
					try {
						executorService().invokeAny(selectTasks);
					}
					catch ( InterruptedException ignore ) {
					}
					catch ( RejectedExecutionException e ) {
						if ( ! isClosed() ) {
							throw e;
						}
					}
					catch ( ExecutionException e ) {
						
						Throwable t = e.getCause();
						
						if ( t instanceof RuntimeException ) {
							throw (RuntimeException)t;
						}
						
						notifyLog(e);
					}
					finally {
						notifyHsmsSsCommunicateStateChange(HsmsSsCommunicateState.NOT_CONNECTED);
					}
					
					return null;
				});
		
		try {
			executorService().invokeAny(tasks);
		}
		catch ( InterruptedException ignore ) {
		}
		catch ( RejectedExecutionException e ) {
			if ( ! isClosed() ) {
				throw e;
			}
		}
		catch ( ExecutionException e ) {
			
			Throwable t = e.getCause();
			
			if ( t instanceof RuntimeException ) {
				throw (RuntimeException)t;
			}
			
			notifyLog(e);
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
		}
	}
	
}
