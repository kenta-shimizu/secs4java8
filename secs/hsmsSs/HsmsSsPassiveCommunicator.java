package secs.hsmsSs;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import secs.SecsLog;

public class HsmsSsPassiveCommunicator extends HsmsSsCommunicator {

	public HsmsSsPassiveCommunicator(HsmsSsCommunicatorConfig config) {
		super(config);
	}
	
	@Override
	public void open() throws IOException {
		
		super.open();
		
		if ( hsmsSsConfig().protocol() != HsmsSsProtocol.PASSIVE ) {
			throw new IOException("HsmsSsCommunicatorConfig#protocol is not PASSIVE");
		}
		
		executorService().execute(() -> {
			try {
				openOnce();
			}
			catch ( InterruptedException ignore ) {
			}
		});
	}
	
	@Override
	public void close() throws IOException {
		
		final List<IOException> ioExcepts = new ArrayList<>();
		
		try {
			super.close();
		}
		catch ( IOException e ) {
			ioExcepts.add(e);
		}
		
		if ( ! ioExcepts.isEmpty() ) {
			throw ioExcepts.get(0);
		}
	}
	
	private void openOnce() throws InterruptedException {
		
		final Object sync = new Object();
		
		try (
				AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open();
				) {
			
			SocketAddress socketAddr = hsmsSsConfig().socketAddress()
					.orElseThrow(() -> new IOException("error"));
			
			String socketAddrInfo = hsmsSsConfig().socketAddress()
					.map(SocketAddress::toString)
					.orElse("not setted");

			server.setOption(StandardSocketOptions.SO_REUSEADDR, true);
			server.bind(socketAddr);
			
			entryLog(new SecsLog("HsmsSsPassiveCommunicator#binded", socketAddrInfo));
			
			server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {

				@Override
				public void completed(AsynchronousSocketChannel ch, Void attachment) {
					
					server.accept(null, this);
					
					String channelString = ch.toString();
					
					entryLog(new SecsLog("HsmsSsPassiveCommunicator#loop channel#accept", channelString));
					
					final BlockingQueue<HsmsSsMessage> queue = new LinkedBlockingQueue<>();
					
					final HsmsSsByteReader reader = new HsmsSsByteReader(hsmsSsConfig(), ch);
					final HsmsSsCircuitAssurance linktest = new HsmsSsCircuitAssurance(HsmsSsPassiveCommunicator.this);
					
					reader.addHsmsSsMessageReceiveListener(msg -> {
						sendReplyManager().receive(msg).ifPresent(queue::offer);
					});
					
					reader.addHsmsSsMessageReceiveListener(msg -> {
						linktest.reset();
					});
					
					reader.addHsmsSsMessageReceiveListener(msg -> {
						entryLog(new SecsLog("Receive HsmsSs-Message", msg));
					});
					
					final Object rtn = new Object();
					
					Collection<Callable<Object>> tasks = Arrays.asList(reader, () -> {
						
						Collection<Callable<Boolean>> connectTasks = Arrays.asList(() -> {
							
							try {
								/* NOT_SELECTED */
								for ( ;; ) {
									
									HsmsSsMessage msg = queue.take();
									
									HsmsSsMessageType mt = HsmsSsMessageType.get(msg);
									
									switch ( mt ) {
									case DATA: {
										
										send(ch, createRejectRequest(msg, HsmsSsMessageRejectReason.NOT_SELECTED));
										break;
									}
									case SELECT_REQ: {
										
										boolean f = addChannel(ch);
										
										if ( f /* success */) {
											
											send(ch, createSelectResponse(msg, HsmsSsMessageSelectStatus.SUCCESS));
											
											notifyHsmsSsCommunicateStateChange(HsmsSsCommunicateState.SELECTED);
											
											return Boolean.TRUE;
											
										} else {
											
											send(ch, createSelectResponse(msg, HsmsSsMessageSelectStatus.ALREADY_USED));
										}
										
										break;
									}
									case LINKTEST_REQ: {
										
										send(ch, createLinktestResponse(msg));
										break;
									}
									case SEPARATE_REQ: {
										
										return Boolean.FALSE;
										/* break; */
									}
									case SELECT_RSP:
									case LINKTEST_RSP:
									case REJECT_REQ: {
										
										send(ch, createRejectRequest(msg, HsmsSsMessageRejectReason.TRANSACTION_NOT_OPEN));
										break;
									}
									default: {
										
										if ( HsmsSsMessageType.supportSType(msg) ) {
											
											if ( ! HsmsSsMessageType.supportPType(msg) ) {
												
												send(ch, createRejectRequest(msg, HsmsSsMessageRejectReason.NOT_SUPPORT_TYPE_P));
											}
											
										} else {
											
											send(ch, createRejectRequest(msg, HsmsSsMessageRejectReason.NOT_SUPPORT_TYPE_S));
										}
									}
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
							
							if ( ! f ) {
								/* select faield */
								return rtn;
							}
						}
						catch ( TimeoutException e ) {
							throw new HsmsSsTimeoutT7Exception(e);
						}
						catch ( InterruptedException ignore ) {
							return rtn;
						}
						
						Collection<Callable<Object>> selectTasks = Arrays.asList(linktest, () -> {
							
							try {
								/* SELECTED */
								for ( ;; ) {
									
									HsmsSsMessage msg = queue.take();
									
									HsmsSsMessageType mt = HsmsSsMessageType.get(msg);
									
									switch ( mt ) {
									case DATA : {
										
										notifyReceiveMessage(msg);
										break;
									}
									case SELECT_REQ: {
										
										send(ch, createSelectResponse(msg, HsmsSsMessageSelectStatus.ACTIVED));
										break;
									}
									case LINKTEST_REQ: {
										
										send(ch, createLinktestResponse(msg));
										break;
									}
									case SEPARATE_REQ: {
										
										return rtn;
										/* break; */
									}
									case SELECT_RSP:
									case LINKTEST_RSP:
									case REJECT_REQ: {
										
										send(ch, createRejectRequest(msg, HsmsSsMessageRejectReason.TRANSACTION_NOT_OPEN));
										break;
									}
									default: {
										
										if ( HsmsSsMessageType.supportSType(msg) ) {
											
											if ( ! HsmsSsMessageType.supportPType(msg) ) {
												
												send(ch, createRejectRequest(msg, HsmsSsMessageRejectReason.NOT_SUPPORT_TYPE_P));
											}
											
										} else {
											
											send(ch, createRejectRequest(msg, HsmsSsMessageRejectReason.NOT_SUPPORT_TYPE_S));
										}
									}
									}
								}
							}
							catch ( InterruptedException ignore ) {
							}
							
							return rtn;
						});
						
						try {
							executorService().invokeAny(selectTasks);
						}
						catch ( InterruptedException ignore ) {
						}
						finally {
							notifyHsmsSsCommunicateStateChange(HsmsSsCommunicateState.NOT_CONNECTED);
						}
						
						return rtn;
						
					}, () -> {
						
						try {
							synchronized ( sync ) {
								sync.wait();
							}
						}
						catch (InterruptedException ignore) {
						}
						
						return rtn;
					});

					try {
						executorService().invokeAny(tasks);
					}
					catch ( ExecutionException e ) {
						entryLog(new SecsLog(e.getCause()));
					}
					catch ( InterruptedException ignore ) {
					}
					finally {
						
						removeChannel(ch);
						
						try {
							ch.shutdownOutput();
						}
						catch ( IOException ignore ) {
						}
						
						try {
							ch.close();
						}
						catch ( IOException e ) {
							entryLog(new SecsLog(e));
						}
					}
				}

				@Override
				public void failed(Throwable t, Void attachment) {
					
					entryLog(new SecsLog("HsmsSsPassiveCommunicator#loop AsynchronousSeverSocketChannel#accept failed", t));
					
					synchronized ( sync ) {
						sync.notifyAll();
					}
				}
			});
			
			synchronized ( sync ) {
				sync.wait();
			}
		}
		catch ( IOException e ) {
			entryLog(new SecsLog(e));
		}
	}

}
