package com.shimizukenta.secs.hsmsss.impl;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.CompletionHandler;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import com.shimizukenta.secs.UnsetSocketAddressException;
import com.shimizukenta.secs.hsms.HsmsConnectionMode;
import com.shimizukenta.secs.hsms.HsmsMessageRejectReason;
import com.shimizukenta.secs.hsms.HsmsSendMessageException;
import com.shimizukenta.secs.hsms.HsmsWaitReplyMessageException;
import com.shimizukenta.secs.hsms.impl.AbstractHsmsMessage;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicatorConfig;

public abstract class AbstractHsmsSsActiveCommunicator extends AbstractHsmsSsCommunicator {
	
	private final HsmsSsCommunicatorConfig config;
	
	public AbstractHsmsSsActiveCommunicator(HsmsSsCommunicatorConfig config) {
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
			catch (InterruptedException ignore) {
			}
		});
	}
	
	@Override
	public void close() throws IOException {
		super.close();
	}
	
	private void openActive() throws InterruptedException {
		
		try (
				AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
				) {
			
			final SocketAddress socketAddr = this.config.socketAddress().optional().orElseThrow(UnsetSocketAddressException::new);
			
			notifyLog(HsmsSsConnectionLog.tryConnect(socketAddr));

			channel.connect(socketAddr, null, new CompletionHandler<Void, Void>(){
				
				@Override
				public void completed(Void none, Void attachment) {
					try {
						try {
							
							SocketAddress pLocal = channel.getLocalAddress();
							SocketAddress pRemote = channel.getRemoteAddress();						
							
							try {
								notifyLog(HsmsSsConnectionLog.connected(pLocal, pRemote));
								
								AbstractHsmsSsActiveCommunicator.this.completionAction(channel);
							}
							finally {
								
								try {
									channel.shutdownOutput();
								}
								catch (IOException giveup) {
								}
								
								synchronized (channel) {
									channel.notifyAll();
								}
								
								try {
									notifyLog(HsmsSsConnectionLog.closed(pLocal, pRemote));
								}
								catch ( InterruptedException ignore ) {
								}
							}
						}
						catch (IOException e) {
							notifyLog(e);
						}
					}
					catch (InterruptedException ignore) {
					}
				}
				
				@Override
				public void failed(Throwable t, Void attachment) {
					
					if (! (t instanceof ClosedChannelException)) {
						
						try {
							notifyLog(t);
						}
						catch (InterruptedException ignore) {
						}
					}
					
					synchronized ( channel ) {
						channel.notifyAll();
					}
				}
			});
			
			synchronized (channel) {
				channel.wait();
			}
		}
		catch ( IOException e ) {
			notifyLog(e);
		}
	}
	
	private void completionAction(AsynchronousSocketChannel channel)
			throws IOException, InterruptedException {
		
		try (
				HsmsSsAsynchronousSocketChannelFacade asyncChannel = new HsmsSsAsynchronousSocketChannelFacade(this.config, channel, this);
				) {
			
			final AbstractHsmsSsSession session = this.getSession();
			
			try {
				if (session.setChannel(asyncChannel)) {
					
					final Collection<Callable<Void>> tasks = Arrays.asList(
							() -> {
								try {
									this.mainTask(asyncChannel);
								}
								catch (InterruptedException ignore) {
								}
								return null;
							},
							() -> {
								try {
									asyncChannel.waitUntilShutdown();
								}
								catch (InterruptedException ignore) {
								}
								return null;
							});
					
					try {
						this.executeInvokeAny(tasks);
					}
					catch (ExecutionException e) {
						
						Throwable t = e.getCause();
						
						if (t instanceof RuntimeException) {
							throw (RuntimeException)t;
						}
						
						if (! (t instanceof ClosedChannelException)) {
							notifyLog(t);
						}
					}
				}
			}
			finally {
				session.unsetChannel();
			}
		}
	}
	
	private void mainTask(HsmsSsAsynchronousSocketChannelFacade asyncChannel) throws InterruptedException {
		
		final AbstractHsmsSsSession session = this.getSession();
		
		if (! session.select()) {
			return ;
		}
		
		session.notifyHsmsCommunicateStateToSelected();
		
		try {
			
			for ( ;; ) {
				
				final AbstractHsmsMessage primaryMsg = asyncChannel.takePrimaryHsmsMessage();
				
				switch (primaryMsg.messageType()) {
				case DATA: {
					
					session.notifyHsmsMessageReceive(primaryMsg);
					break;
				}
				case SELECT_REQ:
				case DESELECT_REQ: {
					
					AbstractHsmsMessage r = this.getHsmsSmMessageBuilder().buildRejectRequest(primaryMsg, HsmsMessageRejectReason.NOT_SUPPORT_TYPE_S);
					asyncChannel.send(r);
					break;
				}
				case LINKTEST_REQ: {
					
					AbstractHsmsMessage r = this.getHsmsSmMessageBuilder().buildLinktestResponse(primaryMsg);
					asyncChannel.send(r);
					break;
				}
				case SEPARATE_REQ: {
					
					return;
					/* break; */
				}
				case SELECT_RSP:
				case DESELECT_RSP:
				case LINKTEST_RSP:
				case REJECT_REQ:
				default: {
					/* ignore */
				}
				}
			}
		}
		catch (HsmsSendMessageException | HsmsWaitReplyMessageException e) {
			this.notifyLog(e);
		}
	}
	
	
}
