package com.shimizukenta.secs.hsmsgs;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.CompletionHandler;

import com.shimizukenta.secs.ReadOnlyTimeProperty;
import com.shimizukenta.secs.hsms.HsmsCommunicateState;
import com.shimizukenta.secs.hsms.HsmsConnectionMode;
import com.shimizukenta.secs.hsms.HsmsConnectionModeIllegalStateException;

public abstract class AbstractHsmsGsPassiveCommunicator extends AbstractHsmsGsCommunicator {
	
	public AbstractHsmsGsPassiveCommunicator(HsmsGsCommunicatorConfig config) {
		super(config);
	}
	
	@Override
	public void open() throws IOException {
		
		if ( this.config().connectionMode().get() != HsmsConnectionMode.PASSIVE ) {
			throw new HsmsConnectionModeIllegalStateException("NOT PASSIVE");
		}
		
		super.open();
		
		this.executorService().execute(() -> {
			try {
				final ReadOnlyTimeProperty tp = this.config().rebindIfPassive();
				while ( ! this.isClosed() ) {
					this.openPassive();
					if ( this.isClosed() ) {
						return;
					}
					if ( tp.gtZero() ) {
						tp.sleep();
					} else {
						return;
					}
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
	
	private void openPassive() throws InterruptedException {
		
		try (
				AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open();
				) {
			
			this.getAbstractHsmsSessions().forEach(s -> {
				s.notifyHsmsCommunicateState(HsmsCommunicateState.NOT_CONNECTED);
			});
			
			passiveAccepting(server);
			
			synchronized ( server ) {
				server.wait();
			}
		}
		catch ( IOException e ) {
			this.notifyLog(e);
		}
		finally {
			
			this.getAbstractHsmsSessions().forEach(s -> {
				s.notifyHsmsCommunicateState(HsmsCommunicateState.NOT_CONNECTED);
			});
		}
	}
	
	private void passiveAccepting(AsynchronousServerSocketChannel server)
			throws IOException, InterruptedException {
		
		final SocketAddress addr = this.config().socketAddress().getSocketAddress();
		
		this.notifyLog(HsmsGsPassiveBindLog.tryBind(addr));
		
		server.bind(addr);
		
		this.notifyLog(HsmsGsPassiveBindLog.binded(addr));
		
		server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
			
			@Override
			public void completed(AsynchronousSocketChannel channel, Void attachment) {
				
				server.accept(attachment, this);
				
				SocketAddress pLocal = null;
				SocketAddress pRemote = null;
				
				try {
					
					try {
						
						pLocal = channel.getLocalAddress();
						pRemote = channel.getRemoteAddress();
					}
					catch ( IOException e ) {
						AbstractHsmsGsPassiveCommunicator.this.notifyLog(e);
						return;
					}
					
					AbstractHsmsGsPassiveCommunicator.this.notifyLog(HsmsGsConnectionLog.accepted(pLocal, pRemote));
					
					AbstractHsmsGsPassiveCommunicator.this.completionAction(channel);
				}
				catch ( InterruptedException ignore ) {
				}
				finally {
					
					try {
						channel.shutdownOutput();
					}
					catch ( IOException giveup ) {
					}
					
					try {
						channel.close();
					}
					catch ( IOException giveup ) {
					}
					
					try {
						AbstractHsmsGsPassiveCommunicator.this.notifyLog(HsmsGsConnectionLog.closed(pLocal, pRemote));
					}
					catch ( InterruptedException ignore ) {
					}
				}
			}

			@Override
			public void failed(Throwable t, Void attachment) {
				
				if ( t instanceof RuntimeException ) {
					throw (RuntimeException)t;
				}
				
				if ( ! (t instanceof ClosedChannelException) ) {
					try {
						AbstractHsmsGsPassiveCommunicator.this.notifyLog(t);
					}
					catch ( InterruptedException ignore ) {
					}
				}
				
				synchronized ( server ) {
					server.notifyAll();
				}
			}
		});
	}
	
}
