package com.shimizukenta.secs.hsmsss;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsLog;

public class HsmsSsActiveCommunicator extends HsmsSsCommunicator {

	public HsmsSsActiveCommunicator(HsmsSsCommunicatorConfig config) {
		super(Objects.requireNonNull(config));
	}
	
	@Override
	public void open() throws IOException {
		
		if ( hsmsSsConfig().protocol() != HsmsSsProtocol.ACTIVE ) {
			throw new IOException("HsmsSsCommunicatorConfig#protocol is not ACTIVE");
		}
		
		super.open();
		
		executorService().execute(() -> {
			try {
				for ( ;; ) {
					activeLoop();
					
					sendReplyManager.reset();
					
					long t5 = (long)(hsmsSsConfig().timeout().t5() * 1000.0F);
					TimeUnit.MILLISECONDS.sleep(t5);
				}
			}
			catch ( InterruptedException ignore ) {
			}
		});
		
	}
	
	@Override
	public void close() throws IOException {
		super.close();
	}
	
	protected void activeLoop() throws InterruptedException {
		
		try (
				AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
				) {
			
			SocketAddress socketAddr = hsmsSsConfig().socketAddress();
			
			String socketAddrInfo = socketAddr.toString();
			
			try {
				
				notifyLog(new SecsLog("HsmsSsActiveCommunicator try-connect", socketAddrInfo));

				channel.connect(socketAddr, null, new CompletionHandler<Void, Void>(){
					
					@Override
					public void completed(Void none, Void attachment) {
						
						try {
							
							if ( ! addChannel(channel) ) {
								synchronized ( channel ) {
									channel.notifyAll();
									return;
								}
							}
							
							notifyHsmsSsCommunicateStateChange(HsmsSsCommunicateState.NOT_SELECTED);
							
							final BlockingQueue<HsmsSsMessage> queue = new LinkedBlockingQueue<>();
							
							final HsmsSsByteReader reader = new HsmsSsByteReader(HsmsSsActiveCommunicator.this, channel);
							final HsmsSsCircuitAssurance linktest = new HsmsSsCircuitAssurance(HsmsSsActiveCommunicator.this);
							
							reader.addHsmsSsMessageReceiveListener(msg -> {
								sendReplyManager.put(msg).ifPresent(queue::offer);
							});
							
							reader.addHsmsSsMessageReceiveListener(msg -> {
								linktest.reset();
							});
							
							Collection<Callable<Object>> tasks = Arrays.asList(
									reader,
									() -> {
										
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
											notifyLog(new SecsLog(e));
											return null;
										}
										catch ( InterruptedException e ) {
											return null;
										}
										
										Collection<Callable<Object>> selectTasks = Arrays.asList(
												linktest,
												() -> {
													
													try {
														for ( ;; ) {
															
															HsmsSsMessage msg = queue.take();
															
															HsmsSsMessageType mt = HsmsSsMessageType.get(msg);
															
															try {
																switch ( mt ) {
																case DATA: {
																	
																	putReceiveDataMessage(msg);
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
																case LINKTEST_RSP:
																case REJECT_REQ:
																default: {
																	
																	/* ignore */
																}
																}
															}
															catch ( SecsException e ) {
																notifyLog(new SecsLog(e));
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
										catch ( ExecutionException e ) {
											notifyLog(new SecsLog(e));
										}
										catch ( InterruptedException ignore ) {
										}
										
										return null;
									});
							
							executorService().invokeAny(tasks);
						}
						catch ( ExecutionException e ) {
							notifyLog(new SecsLog(e));
						}
						catch ( InterruptedException ignore ) {
						}
						finally {
							
							try {
								channel.shutdownOutput();
							}
							catch (IOException giveup) {
							}
						}
						
						synchronized ( channel ) {
							channel.notifyAll();
						}
					}
					
					@Override
					public void failed(Throwable t, Void attachment) {
						
						synchronized ( channel ) {
							channel.notifyAll();
						}
						
						notifyLog(new SecsLog("HsmsSsActiveCommunicator#open-AsynchronousSocketChannel#connect failed", t));
					}
				});
				
				synchronized ( channel ) {
					channel.wait();
				}
			}
			finally {
				removeChannel(channel);
				notifyHsmsSsCommunicateStateChange(HsmsSsCommunicateState.NOT_CONNECTED);
			}
		}
		catch ( IOException e ) {
			notifyLog(new SecsLog(e));
		}
	}

}
