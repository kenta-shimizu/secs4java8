package com.shimizukenta.secs.hsmsgs.impl;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.CompletionHandler;
import java.util.Objects;

import com.shimizukenta.secs.UnsetSocketAddressException;
import com.shimizukenta.secs.hsms.HsmsConnectionMode;
import com.shimizukenta.secs.hsmsgs.HsmsGsCommunicatorConfig;

public abstract class AbstractHsmsGsActiveCommunicator extends AbstractHsmsGsCommunicator {
	
	private final HsmsGsCommunicatorConfig config;
	
	public AbstractHsmsGsActiveCommunicator(HsmsGsCommunicatorConfig config) {
		super(Objects.requireNonNull(config));
		
		this.config = config;
		
		config.connectionMode().addChangeListener(mode -> {
			if (mode != HsmsConnectionMode.ACTIVE) {
				try {
					this.close();
				}
				catch (IOException giveup) {
				}
			}
		});
	}
	
	@Override
	public void open() throws IOException {
		
		super.open();
		
		this.executorService().execute(() -> {
			try {
				this.openActive();
				while (! this.isClosed()) {
					this.config.timeout().t5().sleep();
					this.openActive();
				}
			}
			catch ( InterruptedException ignore ) {
			}
		});
	}
	
	protected void openActive() throws InterruptedException {
		
		try (
				AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
				) {
			
			final SocketAddress remoteAddr = this.config.socketAddress().optional().orElseThrow(UnsetSocketAddressException::new);
			
			AbstractHsmsGsActiveCommunicator.this.hsmsLogObserver().offerHsmsChannelConnectionTryConnect(remoteAddr);
			
			channel.connect(remoteAddr, null, new CompletionHandler<Void, Void>(){
				
				@Override
				public void completed(Void none, Void attachment) {
					
					SocketAddress pLocal = null;
					SocketAddress pRemote = null;
					
					try {
						
						try {
							pLocal = channel.getLocalAddress();
							pRemote = channel.getRemoteAddress();
							
							AbstractHsmsGsActiveCommunicator.this.hsmsLogObserver().offerHsmsChannelConnectionConnected(pLocal, pRemote);
							
							AbstractHsmsGsActiveCommunicator.this.completionAction(channel);
						}
						catch ( IOException e ) {
							AbstractHsmsGsActiveCommunicator.this.offerThrowableToLog(e);
						}
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
						
						AbstractHsmsGsActiveCommunicator.this.hsmsLogObserver().offerHsmsChannelConnectionConnectClosed(pLocal, pRemote);
					}
				}
				
				@Override
				public void failed(Throwable t, Void attachment) {
					
					if (! (t instanceof ClosedChannelException)) {
						AbstractHsmsGsActiveCommunicator.this.offerThrowableToLog(t);
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
		catch ( IOException e ) {
			this.offerThrowableToLog(e);
		}
	}
	
}
