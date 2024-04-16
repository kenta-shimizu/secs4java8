package com.shimizukenta.secs.hsmsss.impl;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
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
import com.shimizukenta.secs.hsms.HsmsMessageSelectStatus;
import com.shimizukenta.secs.hsms.HsmsMessageType;
import com.shimizukenta.secs.hsms.HsmsSendMessageException;
import com.shimizukenta.secs.hsms.HsmsTimeoutT7Exception;
import com.shimizukenta.secs.hsms.HsmsWaitReplyMessageException;
import com.shimizukenta.secs.hsms.impl.AbstractHsmsMessage;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicatorConfig;
import com.shimizukenta.secs.hsmsss.HsmsSsPassiveReceiveNotSelectRequestException;

/**
 * This abstract class is implementation of HSMS-SS-Passive Communicator(SEMI-E37.1).
 * 
 * <p>
 * This class is called from {@link HsmsSsCommunicator#newInstance(HsmsSsCommunicatorConfig)<br />
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractHsmsSsPassiveCommunicator extends AbstractHsmsSsCommunicator {
	
	private final HsmsSsCommunicatorConfig config;
	
	public AbstractHsmsSsPassiveCommunicator(HsmsSsCommunicatorConfig config) {
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
				while (this.config.doRebindIfPassive().booleanValue() && ! this.isClosed()) {
					this.config.timeout().t5().sleep();
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
		catch (IOException e) {
			this.offerThrowableToLog(e);
		}
	}
	
	private void passiveAccepting(AsynchronousServerSocketChannel server)
			throws IOException, InterruptedException {
		
		final SocketAddress addr = this.config.socketAddress().optional().orElseThrow(UnsetSocketAddressException::new);
		
		this.logObserver().offerHsmsChannelConnectionTryBind(addr);
		
		server.bind(addr);
		
		this.logObserver().offerHsmsChannelConnectionBinded(addr);
		
		server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
			
			@Override
			public void completed(AsynchronousSocketChannel channel, Void attachment) {
				
				server.accept(attachment, this);
				
				try {
					try {
						SocketAddress pLocal = channel.getLocalAddress();
						SocketAddress pRemote = channel.getRemoteAddress();
						
						try {
							
							AbstractHsmsSsPassiveCommunicator.this.logObserver().offerHsmsChannelConnectionAccepted(pLocal, pRemote);
							
							AbstractHsmsSsPassiveCommunicator.this.completionAction(channel);
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
							catch (IOException giveup) {
							}
							
							AbstractHsmsSsPassiveCommunicator.this.logObserver().offerHsmsChannelConnectionAcceptClosed(pLocal, pRemote);
						}
					}
					catch (IOException e) {
						AbstractHsmsSsPassiveCommunicator.this.offerThrowableToLog(e);
						return;
					}
				}
				catch ( InterruptedException ignore ) {
				}
			}

			@Override
			public void failed(Throwable t, Void attachment) {
				
				if (! (t instanceof ClosedChannelException)) {
					AbstractHsmsSsPassiveCommunicator.this.offerThrowableToLog(t);
				}
				
				synchronized ( server ) {
					server.notifyAll();
				}
			}
		});
		
		
	}
	
	
	private void completionAction(AsynchronousSocketChannel channel)
			throws IOException, InterruptedException {
		
		try (
				HsmsSsAsynchronousSocketChannelFacade asyncChannel = new HsmsSsAsynchronousSocketChannelFacade(this.config, channel, this);
				) {
			
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
					this.offerThrowableToLog(t);
				}
			}
		}
		catch (IOException e) {
			this.offerThrowableToLog(e);
		}
	}
	
	private final void mainTask(HsmsSsAsynchronousSocketChannelFacade asyncChannel) throws InterruptedException {
		
		final AbstractHsmsMessage initiateMsg = asyncChannel.pollPrimaryHsmsMessage(this.config.timeout().t7());
		
		if (initiateMsg == null) {
			this.offerThrowableToLog(new HsmsTimeoutT7Exception());
			return ;
		}
		
		final AbstractHsmsSsSession session = this.getSession();
		
		switch (initiateMsg.messageType()) {
		case SELECT_REQ: {
			try {
				
				if (session.setChannel(asyncChannel)) {
					
					try {
						session.notifyHsmsCommunicateStateToSelected();
						asyncChannel.send(this.getHsmsSmMessageBuilder().buildSelectResponse(initiateMsg, HsmsMessageSelectStatus.SUCCESS));
						
						for (;;) {
							AbstractHsmsMessage primaryMsg = asyncChannel.takePrimaryHsmsMessage();
							
							switch (primaryMsg.messageType()) {
							case DATA: {
								
								session.notifyHsmsMessageReceive(primaryMsg);
								break;
							}
							case SELECT_REQ: {
								
								asyncChannel.send(this.getHsmsSmMessageBuilder().buildSelectResponse(primaryMsg, HsmsMessageSelectStatus.ACTIVED));
								break;
							}
							case DESELECT_REQ:
							case DESELECT_RSP: {
								
								asyncChannel.send(this.getHsmsSmMessageBuilder().buildRejectRequest(primaryMsg, HsmsMessageRejectReason.NOT_SUPPORT_TYPE_S));
								break;
							}
							case LINKTEST_REQ: {
								
								asyncChannel.send(this.getHsmsSmMessageBuilder().buildLinktestResponse(primaryMsg));
								break;
							}
							case SEPARATE_REQ: {
								return;
								/* break; */
							}
							case REJECT_REQ: {
								return;
								/* break; */
							}
							case SELECT_RSP:
							case LINKTEST_RSP: {
								
								asyncChannel.send(this.getHsmsSmMessageBuilder().buildRejectRequest(primaryMsg, HsmsMessageRejectReason.TRANSACTION_NOT_OPEN));
								return;
								/* break; */
							}
							default: {
								
								if (HsmsMessageType.supportSType(primaryMsg)) {
									
									if (! HsmsMessageType.supportPType(primaryMsg)) {
										
										asyncChannel.send(this.getHsmsSmMessageBuilder().buildRejectRequest(primaryMsg, HsmsMessageRejectReason.NOT_SUPPORT_TYPE_P));
									}
									
								} else {
									
									asyncChannel.send(this.getHsmsSmMessageBuilder().buildRejectRequest(primaryMsg, HsmsMessageRejectReason.NOT_SUPPORT_TYPE_S));
								}
								
								return;
							}
							}
							
						}
					}
					finally {
						session.unsetChannel();
					}
					
				} else {
					
					asyncChannel.send(this.getHsmsSmMessageBuilder().buildSelectResponse(initiateMsg, HsmsMessageSelectStatus.ALREADY_USED));
					return;
				}
			}
			catch (HsmsSendMessageException | HsmsWaitReplyMessageException e) {
				this.offerThrowableToLog(e);
			}
			
			break;
		}
		default: {
			
			this.offerThrowableToLog(new HsmsSsPassiveReceiveNotSelectRequestException());
			return;
		}
		}
	}
	
}
