package com.shimizukenta.secs.hsmsss;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.CompletionHandler;

import com.shimizukenta.secs.ReadOnlyTimeProperty;

/**
 * This abstract class is implementation of HSMS-SS-Passive-rebind Communicator(SEMI-E37.1).
 * 
 * <p>
 * This class is called from {@link HsmsSsCommunicator#newInstance(HsmsSsCommunicatorConfig)<br />
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractHsmsSsRebindPassiveCommunicator extends AbstractHsmsSsPassiveCommunicator {
	
	public AbstractHsmsSsRebindPassiveCommunicator(HsmsSsCommunicatorConfig config) {
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
		
		executeLoopTask(() -> {
			
			passiveBind();
			
			ReadOnlyTimeProperty tp = this.hsmsSsConfig().rebindIfPassive();
			if ( tp.gtZero() ) {
				tp.sleep();
			} else {
				return;
			}
		});
	}
	
	private void passiveBind() throws InterruptedException {
		
		try (
				AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open();
				) {
			
			final SocketAddress socketAddr = hsmsSsConfig().socketAddress().getSocketAddress();
			
			notifyLog(HsmsSsPassiveBindLog.tryBind(socketAddr));
			
			server.setOption(StandardSocketOptions.SO_REUSEADDR, true);
			server.bind(socketAddr);
			
			notifyLog(HsmsSsPassiveBindLog.binded(socketAddr));
			
			server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {

				@Override
				public void completed(AsynchronousSocketChannel channel, Void attachment) {
					server.accept(attachment, this);
					
					
					SocketAddress local = null;
					SocketAddress remote = null;
					
					try {
						local = channel.getLocalAddress();
						remote = channel.getRemoteAddress();
						
						notifyLog(HsmsSsConnectionLog.accepted(local, remote));
						
						completedAction(channel);
					}
					catch ( IOException e ) {
						notifyLog(e);
					}
					catch ( InterruptedException ignore ) {
					}
					finally {
						
						try {
							channel.shutdownOutput();
						}
						catch ( IOException ignore ) {
						}
						
						try {
							channel.close();
						}
						catch ( IOException e ) {
							notifyLog(e);
						}
						
						notifyLog(HsmsSsConnectionLog.closed(local, remote));
					}
				}
				
				@Override
				public void failed(Throwable t, Void attachment) {
					
					if ( ! (t instanceof ClosedChannelException) ) {
						notifyLog(t);
					}
					
					synchronized ( server ) {
						server.notifyAll();
					}
				}
			});
			
			synchronized ( server ) {
				
				try {
					server.wait();
				}
				finally {
					notifyLog(HsmsSsPassiveBindLog.closed(socketAddr));
				}
			}
			
		}
		catch ( IOException e ) {
			notifyLog(e);
		}
	}
	
}
