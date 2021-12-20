package com.shimizukenta.secs.hsmsgs;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.CompletionHandler;

import com.shimizukenta.secs.hsms.HsmsCommunicateState;
import com.shimizukenta.secs.hsms.HsmsConnectionMode;
import com.shimizukenta.secs.hsms.HsmsConnectionModeIllegalStateException;

public abstract class AbstractHsmsGsActiveCommunicator extends AbstractHsmsGsCommunicator {
	
	public AbstractHsmsGsActiveCommunicator(HsmsGsCommunicatorConfig config) {
		super(config);
	}
	
	@Override
	public void open() throws IOException {
		
		if ( this.config().connectionMode().get() != HsmsConnectionMode.ACTIVE ) {
			throw new HsmsConnectionModeIllegalStateException("NOT ACTIVE");
		}
		
		super.open();
		
		this.executorService().execute(() -> {
			
			try {
				while ( ! this.isClosed() ) {
					this.activeCircuit();
					if ( this.isClosed() ) {
						return;
					}
					this.config().timeout().t5().sleep();
				}
			}
			catch ( InterruptedException ignore ) {
			}
		});
	}
	
	protected void activeCircuit() throws InterruptedException {
		
		try (
				AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
				) {
			
			final SocketAddress socketAddr = this.config().socketAddress().getSocketAddress();
			
			notifyLog(HsmsGsConnectionLog.tryConnect(socketAddr));

			channel.connect(socketAddr, null, new CompletionHandler<Void, Void>(){
				
				@Override
				public void completed(Void none, Void attachment) {
					
					SocketAddress pLocal = null;
					SocketAddress pRemote = null;
					
					try {
						
						try {
							pLocal = channel.getLocalAddress();
							pRemote = channel.getRemoteAddress();
							
							AbstractHsmsGsActiveCommunicator.this.notifyLog(HsmsGsConnectionLog.connected(pLocal, pRemote));
							
							try {
								
								AbstractHsmsGsActiveCommunicator.this.getAbstractHsmsSessions().forEach(s -> {
									s.notifyHsmsCommunicateState(HsmsCommunicateState.NOT_SELECTED);
								});
								
								completionAction(channel);
							}
							finally {
								
								AbstractHsmsGsActiveCommunicator.this.getAbstractHsmsSessions().forEach(s -> {
									s.notifyHsmsCommunicateState(HsmsCommunicateState.NOT_CONNECTED);
								});
							}
						}
						catch ( IOException e ) {
							notifyLog(e);
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
						
						try {
							AbstractHsmsGsActiveCommunicator.this.notifyLog(HsmsGsConnectionLog.closed(pLocal, pRemote));
						}
						catch ( InterruptedException ignore ) {
						}
					}
				}
				
				@Override
				public void failed(Throwable t, Void attachment) {
					
					if ( ! (t instanceof ClosedChannelException) ) {
						
						try {
							AbstractHsmsGsActiveCommunicator.this.notifyLog(t);
						}
						catch ( InterruptedException ignore ) {
						}
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
			this.notifyLog(e);
		}
	}
	
}
