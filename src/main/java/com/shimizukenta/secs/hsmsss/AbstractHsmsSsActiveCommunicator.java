package com.shimizukenta.secs.hsmsss;

import java.io.IOException;
import java.net.SocketAddress;
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

import com.shimizukenta.secs.hsms.AbstractHsmsAsyncSocketChannel;
import com.shimizukenta.secs.hsms.HsmsCommunicateState;
import com.shimizukenta.secs.hsms.HsmsConnectionMode;
import com.shimizukenta.secs.hsms.HsmsException;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessageRejectReason;
import com.shimizukenta.secs.hsms.HsmsMessageSelectStatus;
import com.shimizukenta.secs.hsms.HsmsSendMessageException;
import com.shimizukenta.secs.hsms.HsmsWaitReplyMessageException;

public abstract class AbstractHsmsSsActiveCommunicator extends AbstractHsmsSsCommunicator {

	private final HsmsSsCommunicatorConfig config;
	
	public AbstractHsmsSsActiveCommunicator(HsmsSsCommunicatorConfig config) {
		super(Objects.requireNonNull(config));
		this.config = config;
	}
	
	@Override
	public void open() throws IOException {
		
		if ( this.config.connectionMode().get() != HsmsConnectionMode.ACTIVE ) {
			throw new IOException("HsmsSsCommunicatorConfig#connectionMode is not ACTIVE");
		}
		
		super.open();
		
		executeLoopTask(() -> {
			activeCircuit();
			this.config.timeout().t5().sleep();
		});
	}
	
	@Override
	public void close() throws IOException {
		super.close();
	}
	
	protected void activeCircuit() throws InterruptedException {
		
		try (
				AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
				) {
			
			final SocketAddress socketAddr = this.config.socketAddress().getSocketAddress();
			
			try {
				notifyLog(HsmsSsConnectionLog.tryConnect(socketAddr));

				channel.connect(socketAddr, null, new CompletionHandler<Void, Void>(){
					
					@Override
					public void completed(Void none, Void attachment) {
						
						SocketAddress local = null;
						SocketAddress remote = null;
						
						try {
							
							try {
								local = channel.getLocalAddress();
								remote = channel.getRemoteAddress();
								
								notifyLog(HsmsSsConnectionLog.connected(local, remote));
								
								completionAction(channel);
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
								notifyLog(HsmsSsConnectionLog.closed(local, remote));
							}
							catch ( InterruptedException ignore ) {
							}
						}
					}
					
					@Override
					public void failed(Throwable t, Void attachment) {
						
						if ( ! (t instanceof ClosedChannelException) ) {
							
							try {
								notifyLog(t);
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
			finally {
				notifyHsmsCommunicateState(HsmsCommunicateState.NOT_CONNECTED);
			}
		}
		catch ( IOException e ) {
			notifyLog(e);
		}
	}
	
	private void completionAction(AsynchronousSocketChannel channel) throws InterruptedException {
		
		final AbstractHsmsAsyncSocketChannel asyncChannel = buildAsyncSocketChannel(channel);
		
		try {
			
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
								this.mainTask(asyncChannel);
							}
							catch ( HsmsSendMessageException | HsmsWaitReplyMessageException | HsmsException  e ) {
								this.notifyLog(e);
							}
						}
						catch ( InterruptedException ignore ) {
						}
						
						return null;
					}
					);
			
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
		
		final BlockingQueue<HsmsMessage> queue = new LinkedBlockingQueue<>();
		
		asyncChannel.addHsmsMessageReceiveListener(msg -> {
			try {
				queue.put(msg);
			}
			catch ( InterruptedException ignore ) {
			}
		});
		
		this.notifyHsmsCommunicateState(HsmsCommunicateState.NOT_SELECTED);
		
		HsmsMessageSelectStatus selectStatus = asyncChannel.sendSelectRequest(this.getSession())
				.map(HsmsMessageSelectStatus::get)
				.orElse(HsmsMessageSelectStatus.NOT_SELECT_RSP);
		
		switch ( selectStatus ) {
		case SUCCESS:
		case ACTIVED: {
			
			if ( this.getSession().setAsyncSocketChannel(asyncChannel) ) {
				
				try {
					this.notifyHsmsCommunicateState(HsmsCommunicateState.SELECTED);
					
					this.mainSelectedTask(asyncChannel, queue);
				}
				finally {
					this.getSession().unsetAsyncSocketChannel();
				}
			}
			
			break;
		}
		default: {
			/* Nothing */
		}
		}
	}
	
	private void mainSelectedTask(
			AbstractHsmsAsyncSocketChannel asyncChannel,
			BlockingQueue<HsmsMessage> queue)
					throws InterruptedException {
		
		final Collection<Callable<Void>> tasks = Arrays.asList(
				() -> {
					try {
						try {
							for ( ;; ) {
								HsmsMessage msg = queue.take();
								
								switch ( msg.messageType() ) {
								case DATA: {
									
									this.notifyReceiveMessage(msg);
									break;
								}
								case SELECT_REQ:
								case DESELECT_REQ: {
									
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
