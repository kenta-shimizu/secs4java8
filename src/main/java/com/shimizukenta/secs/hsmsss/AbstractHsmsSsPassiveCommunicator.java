package com.shimizukenta.secs.hsmsss;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.CompletionHandler;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;

import com.shimizukenta.secs.ReadOnlyTimeProperty;
import com.shimizukenta.secs.hsms.AbstractHsmsAsyncSocketChannel;
import com.shimizukenta.secs.hsms.AbstractHsmsMessage;
import com.shimizukenta.secs.hsms.HsmsConnectionMode;
import com.shimizukenta.secs.hsms.HsmsConnectionModeIllegalStateException;
import com.shimizukenta.secs.hsms.HsmsException;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessageRejectReason;
import com.shimizukenta.secs.hsms.HsmsMessageSelectStatus;
import com.shimizukenta.secs.hsms.HsmsMessageType;
import com.shimizukenta.secs.hsms.HsmsSendMessageException;
import com.shimizukenta.secs.hsms.HsmsTimeoutT7Exception;
import com.shimizukenta.secs.hsms.HsmsWaitReplyMessageException;

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
	
	public AbstractHsmsSsPassiveCommunicator(HsmsSsCommunicatorConfig config) {
		super(Objects.requireNonNull(config));
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
		
		final SocketAddress addr = this.config().socketAddress().getSocketAddress();
		
		this.notifyLog(HsmsSsPassiveBindLog.tryBind(addr));
		
		server.bind(addr);
		
		this.notifyLog(HsmsSsPassiveBindLog.binded(addr));
		
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
						AbstractHsmsSsPassiveCommunicator.this.notifyLog(e);
						return;
					}
					
					AbstractHsmsSsPassiveCommunicator.this.notifyLog(HsmsSsConnectionLog.accepted(pLocal, pRemote));
					
					AbstractHsmsSsPassiveCommunicator.this.completionAction(channel);
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
						AbstractHsmsSsPassiveCommunicator.this.notifyLog(HsmsSsConnectionLog.closed(pLocal, pRemote));
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
						AbstractHsmsSsPassiveCommunicator.this.notifyLog(t);
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
	
	private void completionAction(AsynchronousSocketChannel channel) throws InterruptedException {
		
		final AbstractHsmsAsyncSocketChannel asyncChannel = this.buildAsyncSocketChannel(channel);
		
		final Collection<Callable<Void>> tasks = Arrays.asList(
				() -> {
					try {
						try {
							asyncChannel.reading();
						}
						catch ( HsmsException e ) {
							this.notifyLog(e);
						}
					}
					catch ( InterruptedException ignore ) {
					}
					
					return null;
				},
				() -> {
					try {
						asyncChannel.waitingUntilShutdown();
					}
					catch ( InterruptedException ignore ) {
					}
					
					return null;
				},
				() -> {
					try {
						try {
							mainTask(asyncChannel);
						}
						catch ( HsmsSendMessageException | HsmsWaitReplyMessageException | HsmsException e ) {
							this.notifyLog(e);
						}
					}
					catch ( InterruptedException ignore ) {
					}
					
					return null;
				}
				);
		
		try {
			this.executeInvokeAny(tasks);
		}
		catch ( ExecutionException e ) {
			Throwable t = e.getCause();
			
			if ( t instanceof RuntimeException ) {
				throw (RuntimeException)t;
			}
			
			this.notifyLog(t);
		}
	}
	
	private final void mainTask(AbstractHsmsAsyncSocketChannel asyncChannel)
			throws HsmsSendMessageException, HsmsWaitReplyMessageException, HsmsException,
			InterruptedException {
		
		final BlockingQueue<AbstractHsmsMessage> queue = new LinkedBlockingQueue<>();
		
		asyncChannel.addHsmsMessageReceiveListener(msg -> {
			try {
				queue.put(msg);
			}
			catch ( InterruptedException ignore ) {
			}
		});
		
		final HsmsMessage msg = this.config().timeout().t7().poll(queue);
		
		if ( msg == null ) {
			throw new HsmsTimeoutT7Exception();
		}
		
		switch ( msg.messageType() ) {
		case SELECT_REQ: {
			
			if ( this.getSession().setAsyncSocketChannel(asyncChannel) ) {
				
				try {
					asyncChannel.sendSelectResponse(msg, HsmsMessageSelectStatus.SUCCESS);
					this.mainSelectedTask(asyncChannel, queue);
				}
				finally {
					this.getSession().unsetAsyncSocketChannel();
				}
				
			} else {
				
				asyncChannel.sendSelectResponse(msg, HsmsMessageSelectStatus.ALREADY_USED);
			}
			
			break;
		}
		default: {
			
			throw new HsmsSsPassiveReceiveNotSelectRequestException();
		}
		}
	}
	
	private void mainSelectedTask(
			AbstractHsmsAsyncSocketChannel asyncChannel,
			BlockingQueue<AbstractHsmsMessage> queue)
					throws InterruptedException {
		
		final Collection<Callable<Void>> tasks = Arrays.asList(
				() -> {
					try {
						try {
							
							for ( ;; ) {
								final AbstractHsmsMessage msg = queue.take();
								
								switch ( msg.messageType() ) {
								case DATA: {
									this.notifyReceiveMessage(msg);
									break;
								}
								case SELECT_REQ: {
									asyncChannel.sendSelectResponse(msg, HsmsMessageSelectStatus.ACTIVED);
									break;
								}
								case DESELECT_REQ:
								case DESELECT_RSP: {
									asyncChannel.sendRejectRequest(msg, HsmsMessageRejectReason.NOT_SUPPORT_TYPE_S);
									break;
								}
								case LINKTEST_REQ: {
									asyncChannel.sendLinktestResponse(msg);
									break;
								}
								case SEPARATE_REQ: {
									return null;
									/* break; */
								}
								case REJECT_REQ: {
									return null;
									/* break; */
								}
								case SELECT_RSP:
								case LINKTEST_RSP: {
									asyncChannel.sendRejectRequest(msg, HsmsMessageRejectReason.TRANSACTION_NOT_OPEN);
									return null;
									/* break; */
								}
								default: {
									
									if ( HsmsMessageType.supportSType(msg) ) {
										
										if ( ! HsmsMessageType.supportPType(msg) ) {
											asyncChannel.sendRejectRequest(msg, HsmsMessageRejectReason.NOT_SUPPORT_TYPE_P);
										}
										
									} else {
										
										asyncChannel.sendRejectRequest(msg, HsmsMessageRejectReason.NOT_SUPPORT_TYPE_S);
									}
									
									return null;
								}
								}
							}
						}
						catch ( HsmsSendMessageException | HsmsWaitReplyMessageException | HsmsException e ) {
							this.notifyLog(e);
						}
					}
					catch ( InterruptedException ignore ) {
					}
					
					return null;
				},
				() -> {
					try {
						try {
							asyncChannel.linktesting();
						}
						catch ( HsmsSendMessageException | HsmsWaitReplyMessageException | HsmsException e ) {
							this.notifyLog(e);
						}
					}
					catch ( InterruptedException ignore ) {
					}
					
					return null;
				}
				);
		
		try {
			this.executeInvokeAny(tasks);
		}
		catch ( ExecutionException e ) {
			Throwable t = e.getCause();
			
			if ( t instanceof RuntimeException ) {
				throw (RuntimeException)t;
			}
			
			this.notifyLog(t);
		}
		
	}
	
}
