package secs.hsmsSs;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import secs.SecsLog;

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
						
						notifyHsmsSsCommunicateStateChange(HsmsSsCommunicateState.CONNECTED);
						
						//entryLog
						//connected.
						
						
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void failed(Throwable t, Void attachment) {
						
						synchronized ( this ) {
							this.notifyAll();
						}
						
						entryLog(new SecsLog("HsmsSsActiveCommunicator#open-AsynchronousSocketChannel#connect failed", t));
					}
					
				});
				
				synchronized ( this ) {
					this.wait();
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
