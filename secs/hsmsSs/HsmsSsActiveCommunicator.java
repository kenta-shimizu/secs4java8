package secs.hsmsSs;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import secs.SecsException;
import secs.SecsLog;

public class HsmsSsActiveCommunicator extends HsmsSsCommunicator {

	public HsmsSsActiveCommunicator(HsmsSsCommunicatorConfig config) {
		super(Objects.requireNonNull(config));
	}
	
	@Override
	public void open() throws IOException {
		super.open();
		
		if ( hsmsSsConfig().protocol() != HsmsSsProtocol.ACTIVE ) {
			throw new IOException("HsmsSsCommunicatorConfig#protocol is not ACTIVE");
		}
		
		executorService().execute(() -> {
			try {
				for ( ;; ) {
					activeLoop();
				}
			}
			catch ( InterruptedException ignore ) {
			}
		});
		
	}
	
	@Override
	public void close() throws IOException {
		
		final List<IOException> ioExcepts = new ArrayList<>();
		
		try {
			synchronized ( this ) {
				if ( closed ) {
					return;
				}
				
				super.close();
			}
		}
		catch ( IOException e ) {
			ioExcepts.add(e);
		}
		
		if ( ! ioExcepts.isEmpty() ) {
			throw ioExcepts.get(0);
		}
	}
	
	protected void activeLoop() throws InterruptedException {
		
		final Object sync = new Object();
		
		try (
				AsynchronousSocketChannel ch = AsynchronousSocketChannel.open();
				) {
			
			SocketAddress socketAddr = hsmsSsConfig().socketAddress()
					.orElseThrow(() -> new IOException("error"));
			
			String socketAddrInfo = hsmsSsConfig().socketAddress()
					.map(SocketAddress::toString)
					.orElse("not setted");
			
			try {
				
				entryLog(new SecsLog("HsmsSsActiveCommunicator try-connect", socketAddrInfo));

				ch.connect(socketAddr, null, new CompletionHandler<Void, Void>(){
					
					@Override
					public void completed(Void none, Void attachment) {
						
						if ( ! addChannel(ch) ) {
							synchronized ( sync ) {
								sync.notifyAll();
								return;
							}
						}
						
						notifyHsmsSsCommunicateStateChange(HsmsSsCommunicateState.NOT_SELECTED);
						
						final BlockingQueue<HsmsSsMessage> queue = new LinkedBlockingQueue<>();
						
						final HsmsSsByteReader reader = new HsmsSsByteReader(HsmsSsActiveCommunicator.this, ch);
						final HsmsSsCircuitAssurance linktest = new HsmsSsCircuitAssurance(HsmsSsActiveCommunicator.this);
						
						reader.addHsmsSsMessageReceiveListener(msg -> {
							sendReplyManager().receive(msg).ifPresent(queue::offer);
						});
						
						reader.addHsmsSsMessageReceiveListener(msg -> {
							linktest.reset();
						});
						
						reader.addHsmsSsMessageReceiveListener(msg -> {
							entryLog(new SecsLog("Receive HsmsSs-Message", msg));
						});
						
						Collection<Callable<Object>> tasks = Arrays.asList(reader, () -> {
							
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
								entryLog(new SecsLog(e));
								return null;
							}
							catch ( InterruptedException e ) {
								return null;
							}
							
							Collection<Callable<Object>> selectTasks = Arrays.asList(linktest, () -> {
								
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
											entryLog(new SecsLog(e));
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
								entryLog(new SecsLog(e.getCause()));
							}
							catch ( InterruptedException ignore ) {
							}
							
							return null;
						});
						
						try {
							executorService().invokeAny(tasks);
						}
						catch ( ExecutionException e ) {
							entryLog(new SecsLog(e.getCause()));
						}
						catch ( InterruptedException ignore ) {
						}
						
						synchronized ( sync ) {
							sync.notifyAll();
						}
					}
					
					@Override
					public void failed(Throwable t, Void attachment) {
						
						synchronized ( sync ) {
							sync.notifyAll();
						}
						
						entryLog(new SecsLog("HsmsSsActiveCommunicator#open-AsynchronousSocketChannel#connect failed", t));
					}
					
				});
				
				synchronized ( sync ) {
					sync.wait();
				}
			}
			finally {
				removeChannel(ch);
				notifyHsmsSsCommunicateStateChange(HsmsSsCommunicateState.NOT_CONNECTED);
			}
		}
		catch ( IOException e ) {
			entryLog(new SecsLog(e));
		}
		
		long t5 = (long)(hsmsSsConfig().timeout().t5() * 1000.0F);
		TimeUnit.MILLISECONDS.sleep(t5);
	}

}
