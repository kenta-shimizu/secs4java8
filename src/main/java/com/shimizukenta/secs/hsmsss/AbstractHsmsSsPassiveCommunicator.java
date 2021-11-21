package com.shimizukenta.secs.hsmsss;

import java.io.Closeable;
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
import com.shimizukenta.secs.hsms.HsmsConnectionMode;
import com.shimizukenta.secs.hsms.HsmsException;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsSendMessageException;
import com.shimizukenta.secs.hsms.HsmsT7TimeoutException;
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
	
	private final HsmsSsCommunicatorConfig config;
	private Closeable serverChannel;
	
	public AbstractHsmsSsPassiveCommunicator(HsmsSsCommunicatorConfig config) {
		super(Objects.requireNonNull(config));
		this.config = config;
		this.serverChannel = null;
	}
	
	@Override
	public void open() throws IOException {
		
		if ( this.config.connectionMode().get() != HsmsConnectionMode.PASSIVE ) {
			throw new IOException("HsmsSsCommunicatorConfig#connectionMode is not PASSIVE");
		}
		
		super.open();
		this.openPassive();
	}
	
	@Override
	public void close() throws IOException {
		
		if ( this.isClosed() ) {
			return;
		}
		
		IOException ioExcept = null;
		
		try {
			super.close();
		}
		catch ( IOException e ) {
			ioExcept = e;
		}
		
		try {
			if ( this.serverChannel != null ) {
				this.serverChannel.close();
			}
		}
		catch ( IOException e ) {
			ioExcept = e;
		}
		
		if ( ioExcept != null ) {
			throw ioExcept;
		}
	}
	
	private void openPassive() throws IOException {
		
		ReadOnlyTimeProperty tp = config.rebindIfPassive();
		
		if ( tp.gtZero() ) {
			
			this.executorService().execute(() -> {
				
				try {
					
					while ( ! this.isClosed() ) {
						
						try (
								AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open();
								) {
							
							this.serverChannel = server;
							
							passiveAccepting(server);
							
							synchronized ( server ) {
								server.wait();
							}
						}
						catch ( IOException e ) {
							this.notifyLog(e);
						}
						
						if ( ! this.isClosed() ) {
							if ( tp.gtZero() ) {
								tp.sleep();
							} else {
								return;
							}
						}
					}
				}
				catch ( InterruptedException ignore ) {
				}
			});
			
		} else {
			
			try (
					AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open();
					) {
				
				this.serverChannel = server;
				
				passiveAccepting(server);
			}
			catch ( InterruptedException giveup ) {
			}
		}
	}
	
	private void passiveAccepting(AsynchronousServerSocketChannel server) throws IOException, InterruptedException {
		
		final SocketAddress addr = config.socketAddress().getSocketAddress();
		
		this.notifyLog(HsmsSsPassiveBindLog.tryBind(addr));
		
		server.bind(addr);
		
		this.notifyLog(HsmsSsPassiveBindLog.binded(addr));
		
		server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {

			@Override
			public void completed(AsynchronousSocketChannel channel, Void attachment) {
				
				server.accept(attachment, this);
				
				SocketAddress local = null;
				SocketAddress remote = null;
				
				try {
					
					try {
						
						local = channel.getLocalAddress();
						remote = channel.getRemoteAddress();
					}
					catch ( IOException e ) {
						AbstractHsmsSsPassiveCommunicator.this.notifyLog(e);
						return;
					}
					
					AbstractHsmsSsPassiveCommunicator.this.notifyLog(HsmsSsConnectionLog.accepted(local, remote));
					
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
						AbstractHsmsSsPassiveCommunicator.this.notifyLog(HsmsSsConnectionLog.closed(local, remote));
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
						asyncChannel.reading();
					}
					catch ( HsmsException e ) {
						this.notifyLog(e);
					}
					catch ( InterruptedException ignore ) {
					}
					
					return null;
				},
				() -> {
					try {
						mainTask(asyncChannel);
					}
					catch ( HsmsException e ) {
						this.notifyLog(e);
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
		
		final BlockingQueue<HsmsMessage> queue = new LinkedBlockingQueue<>();
		
		asyncChannel.addHsmsMessageReceiveListener(msg -> {
			try {
				queue.put(msg);
			}
			catch ( InterruptedException ignore ) {
			}
		});
		
		{
			final HsmsMessage msg = config.timeout().t7().poll(queue);
			
			if ( msg == null ) {
				throw new HsmsT7TimeoutException();
			}
			
			switch ( msg.messageType() ) {
			case SELECT_REQ: {
				
				if ( this.getSession().setAsyncSocketChannel(asyncChannel) ) {
					
					//TOOD
					
					break;	/* success */
					
				} else {
					
					//TODO
					
					return;
				}
				
				/* break; */
			}
			default: {
				return;
			}
			}
		}
		
		try {
			
			//TODO
			
		}
		finally {
			this.getSession().unsetAsyncSocketChannel();
		}
	}
	
	
//	protected class PassiveInnerConnection extends AbstractInnerConnection {
//
//		protected PassiveInnerConnection(AsynchronousSocketChannel channel) {
//			super(channel);
//		}
//		
//		protected void mainTask() throws InterruptedException {
//			
//			try {
//				
//				{
//					final Collection<Callable<Boolean>> tasks = Arrays.asList(
//							() -> {
//								try {
//									return connectTask();
//								}
//								catch ( InterruptedException ignore ) {
//								}
//								
//								return Boolean.FALSE;
//							}
//							);
//					
//					try {
//						boolean f = executeInvokeAny(
//								tasks,
//								hsmsSsConfig().timeout().t7()
//								).booleanValue();
//						
//						if ( f ) {
//							
//							/* SELECTED */
//							notifyHsmsSsCommunicateStateChange(HsmsSsCommunicateState.SELECTED);
//							
//						} else {
//							
//							return;
//						}
//					}
//					catch ( TimeoutException e ) {
//						notifyLog(new HsmsSsTimeoutT7Exception(e));
//						return;
//					}
//					catch ( ExecutionException e ) {
//						
//						Throwable t = e.getCause();
//						
//						if ( t instanceof RuntimeException ) {
//							throw (RuntimeException)t;
//						}
//						
//						notifyLog(t);
//						return;
//					}
//				}
//				
//				{
//					final Collection<Callable<Void>> tasks = Arrays.asList(
//							() -> {
//								try {
//									selectedTask();
//								}
//								catch ( InterruptedException ignore ) {
//								}
//								catch ( SecsException e ) {
//									notifyLog(e);
//								}
//								return null;
//							},
//							() -> {
//								try {
//									linktesting();
//								}
//								catch ( InterruptedException ignore ) {
//								}
//								return null;
//							}
//							);
//					
//					try {
//						executeInvokeAny(tasks);
//					}
//					catch ( ExecutionException e ) {
//						
//						Throwable t = e.getCause();
//						
//						if ( t instanceof RuntimeException ) {
//							throw (RuntimeException)t;
//						}
//						
//						notifyLog(t);
//					}
//				}
//			}
//			finally {
//				notifyHsmsSsCommunicateStateChange(HsmsSsCommunicateState.NOT_CONNECTED);
//				removeSelectedConnection(this);
//			}
//		}
//		
//		protected Boolean connectTask() throws InterruptedException, SecsException {
//			
//			for ( ;; ) {
//				
//				final HsmsSsMessage msg = this.takeReceiveMessage();
//				
//				switch ( HsmsSsMessageType.get(msg) ) {
//				case DATA: {
//					
//					send(createRejectRequest(msg, HsmsSsMessageRejectReason.NOT_SELECTED));
//					return Boolean.FALSE;
//					/* break; */
//				}
//				case SELECT_REQ: {
//					
//					boolean f = addSelectedConnection(this);
//					
//					if ( f /* success */) {
//						
//						send(createSelectResponse(msg, HsmsSsMessageSelectStatus.SUCCESS));
//						
//						return Boolean.TRUE;
//						
//					} else {
//						
//						send(createSelectResponse(msg, HsmsSsMessageSelectStatus.ALREADY_USED));
//					}
//					
//					break;
//				}
//				case LINKTEST_REQ: {
//					
//					send(createRejectRequest(msg, HsmsSsMessageRejectReason.NOT_SELECTED));
//					return Boolean.FALSE;
//					/* break; */
//				}
//				case SEPARATE_REQ: {
//					
//					return Boolean.FALSE;
//					/* break; */
//				}
//				case SELECT_RSP:
//				case LINKTEST_RSP:
//				case REJECT_REQ: {
//					
//					send(createRejectRequest(msg, HsmsSsMessageRejectReason.TRANSACTION_NOT_OPEN));
//					return Boolean.FALSE;
//					/* break; */
//				}
//				default: {
//					
//					if ( HsmsSsMessageType.supportSType(msg) ) {
//						
//						if ( ! HsmsSsMessageType.supportPType(msg) ) {
//							
//							send(createRejectRequest(msg, HsmsSsMessageRejectReason.NOT_SUPPORT_TYPE_P));
//							return Boolean.FALSE;
//						}
//						
//					} else {
//						
//						send(createRejectRequest(msg, HsmsSsMessageRejectReason.NOT_SUPPORT_TYPE_S));
//						return Boolean.FALSE;
//					}
//				}
//				}
//			}
//		}
//		
//		protected void selectedTask() throws InterruptedException, SecsException {
//			
//			for ( ;; ) {
//				
//				final HsmsSsMessage msg = this.takeReceiveMessage();
//				
//				switch ( HsmsSsMessageType.get(msg) ) {
//				case DATA: {
//					
//					notifyReceiveMessage(msg);
//					break;
//				}
//				case SELECT_REQ: {
//					
//					send(createSelectResponse(msg, HsmsSsMessageSelectStatus.ACTIVED));
//					break;
//				}
//				case LINKTEST_REQ: {
//					
//					send(createLinktestResponse(msg));
//					break;
//				}
//				case SEPARATE_REQ: {
//					
//					return;
//					/* break; */
//				}
//				case SELECT_RSP:
//				case LINKTEST_RSP:
//				case REJECT_REQ: {
//					
//					send(createRejectRequest(msg, HsmsSsMessageRejectReason.TRANSACTION_NOT_OPEN));
//					return;
//					/* break; */
//				}
//				default: {
//					
//					if ( HsmsSsMessageType.supportSType(msg) ) {
//						
//						if ( ! HsmsSsMessageType.supportPType(msg) ) {
//							
//							send(createRejectRequest(msg, HsmsSsMessageRejectReason.NOT_SUPPORT_TYPE_P));
//							return;
//						}
//						
//					} else {
//						
//						send(createRejectRequest(msg, HsmsSsMessageRejectReason.NOT_SUPPORT_TYPE_S));
//						return;
//					}
//				}
//				}
//			}
//		}
//	}
	
}
