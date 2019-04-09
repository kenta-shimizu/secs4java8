package secs.hsmsSs;

import java.io.IOException;
import java.net.SocketAddress;
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

import secs.SecsLog;
import secs.SecsWaitReplyMessageException;

public class HsmsSsActiveCommunicator extends HsmsSsCommunicator {

	public HsmsSsActiveCommunicator(HsmsSsCommunicatorConfig config) {
		super(config);
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
					loop();
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
			super.close();
		}
		catch ( IOException e ) {
			ioExcepts.add(e);
		}
		
		if ( ! ioExcepts.isEmpty() ) {
			throw ioExcepts.get(0);
		}
	}
	
	private void loop() throws InterruptedException {
		
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
						
						notifyHsmsSsCommunicateStateChange(HsmsSsCommunicateState.NOT_SELECTED);
						
						BlockingQueue<HsmsSsMessage> queue = new LinkedBlockingQueue<>();
						
						HsmsSsByteReader reader = new HsmsSsByteReader(hsmsSsConfig(), ch);
						
						reader.addHsmsSsMessageReceiveListener(msg -> {
							sendReplyManager().receive(msg).ifPresent(queue::offer);
						});
						
						
						//TODO
						//linktest
						
						
						Collection<Callable<Object>> tasks = Arrays.asList(reader, () -> {
							
							final Object rtn = new Object();
							
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
									return rtn;
								}
								}
							}
							catch ( SecsWaitReplyMessageException e ) {
								return rtn;
							}
							
							for ( ;; ) {
								
								HsmsSsMessage msg = queue.take();
								
								HsmsSsMessageType mt = HsmsSsMessageType.get(msg);
								
								switch ( mt ) {
								case DATA: {
									
									notifyReceiveMessage(msg);
									break;
								}
								case SELECT_REQ: {
									
									//TOOD
									break;
								}
								case LINKTEST_REQ: {
									
									send(createLinktestResponse(msg));
									break;
								}
								case SEPARATE_REQ: {
									
									return rtn;
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
						});
						
						try {
							executorService().invokeAny(tasks);
						}
						catch ( ExecutionException e ) {
							
							Throwable t = e.getCause();
							
							//TODOO
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
				
				//TODO
				notifyHsmsSsCommunicateStateChange(HsmsSsCommunicateState.NOT_CONNECTED);
				
				//TODO
				
			}
		}
		catch ( IOException e ) {
			entryLog(new SecsLog(e));
		}
		
		long t5 = (long)(hsmsSsConfig().timeout().t5() * 1000.0F);
		TimeUnit.MILLISECONDS.sleep(t5);
	}

}
