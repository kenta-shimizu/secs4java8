package com.shimizukenta.secs.hsmsss;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.TimeUnit;

public class HsmsSsRebindPassiveCommunicator extends HsmsSsPassiveCommunicator {
	
	public HsmsSsRebindPassiveCommunicator(HsmsSsCommunicatorConfig config) {
		super(config);
	}
	
	public void open() throws IOException {
		super.open();
	}
	
	public void close() throws IOException {
		super.close();
	}
	
	@Override
	protected void passiveOpen() {
		
		executorService().execute(createLoopTask(() -> {
			
			passiveBind();
			
			long t = this.hsmsSsConfig().rebindIfPassive()
					.map(v -> (v * 1000.0F))
					.map(v -> v.longValue())
					.orElse(-1L);
			
			if ( t > 0 ) {
				TimeUnit.MILLISECONDS.sleep(t);
			} else {
				return;
			}
		}));
	}
	
	private void passiveBind() throws InterruptedException {
		
		try (
				AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open();
				) {
			
			SocketAddress socketAddr = hsmsSsConfig().socketAddress();
			
			String socketAddrInfo = socketAddr.toString();
			
			server.setOption(StandardSocketOptions.SO_REUSEADDR, true);
			server.bind(socketAddr);
			
			notifyLog("HsmsSsRebindPassiveCommunicator#binded", socketAddrInfo);
			
			server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {

				@Override
				public void completed(AsynchronousSocketChannel channel, Void attachment) {
					completedAction(server, this, channel, attachment);
				}

				@Override
				public void failed(Throwable t, Void attachment) {
					
					notifyLog("HsmsSsRebindPassiveCommunicator AsynchronousSeverSocketChannel#accept failed", t);
					
					synchronized ( server ) {
						server.notifyAll();
					}
				}
			});
			
			synchronized ( server ) {
				server.wait();
			}
			
		}
		catch ( IOException e ) {
			notifyLog(e);
		}
	}
	
}
