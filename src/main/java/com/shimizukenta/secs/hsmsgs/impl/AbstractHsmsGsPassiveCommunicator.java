package com.shimizukenta.secs.hsmsgs.impl;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.CompletionHandler;
import java.util.Objects;

import com.shimizukenta.secs.UnsetSocketAddressException;
import com.shimizukenta.secs.hsms.HsmsConnectionMode;
import com.shimizukenta.secs.hsmsgs.HsmsGsCommunicatorConfig;

public abstract class AbstractHsmsGsPassiveCommunicator extends AbstractHsmsGsCommunicator {
	
	private final HsmsGsCommunicatorConfig config;
	
	public AbstractHsmsGsPassiveCommunicator(HsmsGsCommunicatorConfig config) {
		super(Objects.requireNonNull(config));
		
		this.config = config;
		
		config.connectionMode().addChangeListener(mode -> {
			if (mode != HsmsConnectionMode.PASSIVE) {
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
				this.openPassive();
				while (! this.isClosed() && this.config.doRebindIfPassive().booleanValue()) {
					this.config.rebindIfPassiveTime().sleep();
					this.openPassive();
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
			
			passiveAccepting(server);
			
			synchronized ( server ) {
				server.wait();
			}
		}
		catch ( IOException e ) {
			this.notifyLog(e);
		}
	}
	
	private void passiveAccepting(AsynchronousServerSocketChannel server)
			throws IOException, InterruptedException {
		
		final SocketAddress addr = this.config.socketAddress().optional().orElseThrow(UnsetSocketAddressException::new);
		
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
				catch (InterruptedException ignore) {
				}
				finally {
					
					try {
						channel.shutdownOutput();
					}
					catch (IOException giveup) {
					}
					
					try {
						channel.close();
					}
					catch (IOException giveup) {
					}
					
					try {
						AbstractHsmsGsPassiveCommunicator.this.notifyLog(HsmsGsConnectionLog.closed(pLocal, pRemote));
					}
					catch (InterruptedException ignore) {
					}
				}
			}

			@Override
			public void failed(Throwable t, Void attachment) {
				
				if (! (t instanceof ClosedChannelException)) {
					try {
						AbstractHsmsGsPassiveCommunicator.this.notifyLog(t);
					}
					catch (InterruptedException ignore) {
					}
				}
				
				synchronized ( server ) {
					server.notifyAll();
				}
			}
		});
	}
	
}
