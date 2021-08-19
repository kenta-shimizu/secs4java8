package com.shimizukenta.secs.hsmsss;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import com.shimizukenta.secs.AbstractSecsCommunicator;
import com.shimizukenta.secs.AbstractSecsWaitReplyMessageExceptionLog;
import com.shimizukenta.secs.ByteArrayProperty;
import com.shimizukenta.secs.Property;
import com.shimizukenta.secs.PropertyChangeListener;
import com.shimizukenta.secs.ReadOnlyTimeProperty;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2BuildException;
import com.shimizukenta.secs.secs2.Secs2BytesPack;
import com.shimizukenta.secs.secs2.Secs2BytesPackBuilder;
import com.shimizukenta.secs.secs2.Secs2BytesParser;
import com.shimizukenta.secs.secs2.Secs2Exception;

/**
 * This abstract class is implementation of HSMS-SS (SEMI-E37.1).
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractHsmsSsCommunicator extends AbstractSecsCommunicator
		implements HsmsSsCommunicator {
	
	private final HsmsSsCommunicatorConfig hsmsSsConfig;
	
	private final ByteArrayProperty sessionIdBytes = ByteArrayProperty.newInstance(new byte[] {0, 0});
	private final Property<HsmsSsCommunicateState> hsmsSsCommStateProperty = Property.newInstance(HsmsSsCommunicateState.NOT_CONNECTED);
	
	private AbstractInnerConnection selectedConnection;
	
	public AbstractHsmsSsCommunicator(HsmsSsCommunicatorConfig config) {
		super(config);
		
		this.hsmsSsConfig = config;
		
		this.hsmsSsConfig.sessionId().addChangeListener(n -> {
			int v = n.intValue();
			byte[] bs = new byte[] {
					(byte)(v >> 8),
					(byte)v
			};
			sessionIdBytes.set(bs);
		});
		
		this.selectedConnection = null;
	}
	
	protected final HsmsSsCommunicatorConfig hsmsSsConfig() {
		return this.hsmsSsConfig;
	}
	
	@Override
	public void open() throws IOException {
		super.open();
		
		this.hsmsSsCommStateProperty.addChangeListener(state -> {
			notifyLog(HsmsSsCommunicateStateChangeLog.get(state));
		});
		
		this.hsmsSsCommStateProperty.addChangeListener(state -> {
			notifyCommunicatableStateChange(state.communicatable());
		});
	}
	
	@Override
	public void close() throws IOException {
		
		synchronized ( this ) {
			if ( isClosed() ) {
				return;
			}
		}
		
		super.close();
	}
	
	private final Object syncSelectedConnection = new Object();
	
	protected boolean addSelectedConnection(AbstractInnerConnection c) {
		synchronized ( syncSelectedConnection ) {
			if ( this.selectedConnection == null ) {
				this.selectedConnection = c;
				return true;
			} else {
				return false;
			}
		}
	}
	
	protected boolean removeSelectedConnection(AbstractInnerConnection c) {
		synchronized ( this.syncSelectedConnection ) {
			if ( this.selectedConnection == null ) {
				return false;
			} else {
				this.selectedConnection = null;
				return true;
			}
		}
	}
	
	protected AbstractInnerConnection getSelectedConnection() {
		synchronized ( this.syncSelectedConnection ) {
			return this.selectedConnection;
		}
	}
	
	/* HSMS Communicate State */
	protected HsmsSsCommunicateState hsmsSsCommunicateState() {
		return hsmsSsCommStateProperty.get();
	}
	
	protected void notifyHsmsSsCommunicateStateChange(HsmsSsCommunicateState state) {
		hsmsSsCommStateProperty.set(state);
	}
	
	
	
	@Override
	public boolean linktest() throws InterruptedException {
		try {
			return send(createLinktestRequest()).isPresent();
		}
		catch ( SecsException e ) {
			return false;
		}
	}
	
	
	private final AtomicInteger autoNumber = new AtomicInteger();
	
	private int autoNumber() {
		return autoNumber.incrementAndGet();
	}
	
	protected byte[] systemBytes() {
		
		byte[] bs = new byte[4];
		
		if ( hsmsSsConfig().isEquip().booleanValue() ) {
			
			byte[] xs = sessionIdBytes.get();
			bs[0] = xs[0];
			bs[1] = xs[1];
			
		} else {
			
			bs[0] = (byte)0;
			bs[1] = (byte)0;
		}
		
		int n = autoNumber();
		
		bs[2] = (byte)(n >> 8);
		bs[3] = (byte)n;
		
		return bs;
	}
	
	@Override
	public Optional<HsmsSsMessage> send(HsmsSsMessage msg)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException,
			InterruptedException {
		
		final AbstractInnerConnection c = this.getSelectedConnection();
		
		if ( c == null ) {
			
			throw new HsmsSsNotConnectedException(msg);
			
		} else {
			
			try {
				return c.send(msg);
			}
			catch ( SecsWaitReplyMessageException e ) {
				
				notifyLog(new AbstractSecsWaitReplyMessageExceptionLog(e) {
					
					private static final long serialVersionUID = -1896655030432810962L;
				});
				
				throw e;
			}
			catch ( SecsException e ) {
				notifyLog(e);
				throw e;
			}
		}
	}
	
	@Override
	public Optional<SecsMessage> send(int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		HsmsSsMessageType mt = HsmsSsMessageType.DATA;
		byte[] xs = sessionIdBytes.get();
		byte[] sysbytes = systemBytes();
		
		byte[] head = new byte[] {
				xs[0],
				xs[1],
				(byte)strm,
				(byte)func,
				mt.pType(),
				mt.sType(),
				sysbytes[0],
				sysbytes[1],
				sysbytes[2],
				sysbytes[3]
		};
		
		if ( wbit ) {
			head[2] |= 0x80;
		}
		
		return send(new HsmsSsMessage(head, secs2)).map(msg -> (SecsMessage)msg);
	}

	@Override
	public Optional<SecsMessage> send(SecsMessage primary, int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		byte[] pri = primary.header10Bytes();
		
		HsmsSsMessageType mt = HsmsSsMessageType.DATA;
		byte[] xs = sessionIdBytes.get();
		
		byte[] head = new byte[] {
				xs[0],
				xs[1],
				(byte)strm,
				(byte)func,
				mt.pType(),
				mt.sType(),
				pri[6],
				pri[7],
				pri[8],
				pri[9]
		};
		
		if ( wbit ) {
			head[2] |= 0x80;
		}
		
		return send(createHsmsSsMessage(head, secs2)).map(msg -> (SecsMessage)msg);
	}
	
	@Override
	public HsmsSsMessage createHsmsSsMessage(byte[] header) {
		return createHsmsSsMessage(header, Secs2.empty());
	}
	
	@Override
	public HsmsSsMessage createHsmsSsMessage(byte[] header, Secs2 body) {
		return new HsmsSsMessage(header, body);
	}
	
	@Override
	public HsmsSsMessage createSelectRequest() {
		return createHsmsSsControlPrimaryMessage(HsmsSsMessageType.SELECT_REQ);
	}
	
	@Override
	public HsmsSsMessage createSelectResponse(HsmsSsMessage primary, HsmsSsMessageSelectStatus status) {
		
		HsmsSsMessageType mt = HsmsSsMessageType.SELECT_RSP;
		byte[] pri = primary.header10Bytes();
		
		return createHsmsSsMessage(new byte[] {
				pri[0],
				pri[1],
				(byte)0,
				status.statusCode(),
				mt.pType(),
				mt.sType(),
				pri[6],
				pri[7],
				pri[8],
				pri[9]
		});
	}
	
	@Override
	public HsmsSsMessage createLinktestRequest() {
		return createHsmsSsControlPrimaryMessage(HsmsSsMessageType.LINKTEST_REQ);
	}
	
	@Override
	public HsmsSsMessage createLinktestResponse(HsmsSsMessage primary) {
		
		HsmsSsMessageType mt = HsmsSsMessageType.LINKTEST_RSP;
		byte[] pri = primary.header10Bytes();
		
		return createHsmsSsMessage(new byte[] {
				pri[0],
				pri[1],
				(byte)0,
				(byte)0,
				mt.pType(),
				mt.sType(),
				pri[6],
				pri[7],
				pri[8],
				pri[9]
		});
	}
	
	@Override
	public HsmsSsMessage createRejectRequest(HsmsSsMessage ref, HsmsSsMessageRejectReason reason) {
		
		HsmsSsMessageType mt = HsmsSsMessageType.REJECT_REQ;
		byte[] bs = ref.header10Bytes();
		byte b = reason == HsmsSsMessageRejectReason.NOT_SUPPORT_TYPE_P ? bs[4] : bs[5];
		
		return createHsmsSsMessage(new byte[] {
				bs[0],
				bs[1],
				b,
				reason.reasonCode(),
				mt.pType(),
				mt.sType(),
				bs[6],
				bs[7],
				bs[8],
				bs[9]
		});
	}
	
	@Override
	public HsmsSsMessage createSeparateRequest() {
		return createHsmsSsControlPrimaryMessage(HsmsSsMessageType.SEPARATE_REQ);
	}
	
	private HsmsSsMessage createHsmsSsControlPrimaryMessage(HsmsSsMessageType mt) {
		
		byte[] sysbytes = systemBytes();
		
		return createHsmsSsMessage(new byte[] {
				(byte)0xFF,
				(byte)0xFF,
				(byte)0,
				(byte)0,
				mt.pType(),
				mt.sType(),
				sysbytes[0],
				sysbytes[1],
				sysbytes[2],
				sysbytes[3]
		});
	}
	
	
	private static final long MAX_BUFFER_SIZE = 256L * 256L;
	private static final byte[] emptyBytes = new byte[] {0x0, 0x0, 0x0, 0x0};
	private static final long bodyBufferSize = 1024L;
	
	protected abstract class AbstractInnerConnection {
		
		private final AsynchronousSocketChannel channel;
		private boolean linktestResetted;
		
		protected AbstractInnerConnection(AsynchronousSocketChannel channel) {
			this.channel = channel;
			this.linktestResetted = false;
		}
		
		private final BlockingQueue<HsmsSsMessage> recvMsgQueue = new LinkedBlockingQueue<>();
		
		protected HsmsSsMessage takeReceiveMessage() throws InterruptedException {
			return this.recvMsgQueue.take();
		}
		
		protected HsmsSsMessage pollReceiveMessage(ReadOnlyTimeProperty timeout) throws InterruptedException {
			return timeout.poll(this.recvMsgQueue);
		}
		
		private final HsmsSsReplyMessageManager replyMgr = new HsmsSsReplyMessageManager();
		
		protected Optional<HsmsSsMessage> send(HsmsSsMessage msg)
				throws SecsSendMessageException, SecsWaitReplyMessageException,
				SecsException, InterruptedException {
			
			switch ( HsmsSsMessageType.get(msg) ) {
			case SELECT_REQ:
			case LINKTEST_REQ: {
				
				try {
					this.replyMgr.entry(msg);
					
					this.innerSend(msg);
					
					final HsmsSsMessage r = replyMgr.reply(
							msg,
							hsmsSsConfig().timeout().t6()
							).orElse(null);
					
					if ( r == null ) {
						throw new HsmsSsTimeoutT6Exception(msg);
					} else {
						return Optional.of(r);
					}
				}
				finally {
					this.replyMgr.exit(msg);
				}
				
				/* break; */
			}
			case DATA: {
				
				if ( msg.wbit() ) {
					
					try {
						replyMgr.entry(msg);
						
						this.innerSend(msg);
						
						final HsmsSsMessage r = replyMgr.reply(
								msg,
								hsmsSsConfig().timeout().t3()
								).orElse(null);
						
						if ( r == null ) {
							throw new HsmsSsTimeoutT3Exception(msg);
						} else {
							return Optional.of(r);
						}
					}
					finally {
						replyMgr.exit(msg);
					}
					
				} else {
					
					this.innerSend(msg);
					return Optional.empty();
				}
				
				/* break */
			}
			default: {
				
				this.innerSend(msg);
				return Optional.empty();
			}
			}
		}
		
		protected long prototypeMaxBufferSize() {
			return MAX_BUFFER_SIZE;
		}
		
		private void innerSend(HsmsSsMessage msg)
				throws SecsSendMessageException, SecsException,InterruptedException {
			
			try {
				notifyLog(new HsmsSsTrySendMessageLog(msg));
				
				final Secs2BytesPack pack = Secs2BytesPackBuilder.build(1024, msg.secs2());
				
				long len = pack.size() + 10L;
				
				if ((len > 0x00000000FFFFFFFFL) || (len < 10L)) {
					throw new HsmsSsTooBigSendMessageException(msg);
				}
				
				notifyTrySendMessagePassThrough(msg);
				
				long bufferSize = len + 4L;
				
				if ( bufferSize > this.prototypeMaxBufferSize() ) {
					
					final List<ByteBuffer> buffers = new ArrayList<>();
					{
						ByteBuffer buffer = ByteBuffer.allocate(14);
						
						buffer.put((byte)(len >> 24));
						buffer.put((byte)(len >> 16));
						buffer.put((byte)(len >>  8));
						buffer.put((byte)(len      ));
						buffer.put(msg.header10Bytes());
						
						((Buffer)buffer).flip();
						buffers.add(buffer);
					}
					
					for (byte[] bs : pack.getBytes()) {
						ByteBuffer buffer = ByteBuffer.allocate(bs.length);
						buffer.put(bs);
						
						((Buffer)buffer).flip();
						buffers.add(buffer);
					}
					
					synchronized ( this.channel ) {
						for ( ByteBuffer buffer : buffers ) {
							innerSend(buffer);
						}
					}
					
				} else {
					
					ByteBuffer buffer = ByteBuffer.allocate((int)bufferSize);
					
					buffer.put((byte)(len >> 24));
					buffer.put((byte)(len >> 16));
					buffer.put((byte)(len >>  8));
					buffer.put((byte)(len      ));
					buffer.put(msg.header10Bytes());
					
					for (byte[] bs : pack.getBytes()) {
						buffer.put(bs);
					}
					
					((Buffer)buffer).flip();
					
					synchronized ( this.channel ) {
						innerSend(buffer);
					}
				}
				
				notifySendedMessagePassThrough(msg);
				
				notifyLog(new HsmsSsSendedMessageLog(msg));
			}
			catch ( ExecutionException e ) {
				
				Throwable t = e.getCause();
				
				if ( t instanceof RuntimeException ) {
					throw (RuntimeException)t;
				}
				
				throw new HsmsSsSendMessageException(msg, t);
			}
			catch ( Secs2BuildException | HsmsSsDetectTerminateException e ) {
				throw new HsmsSsSendMessageException(msg, e);
			}
		}
		
		private void innerSend(ByteBuffer buffer)
				throws ExecutionException, HsmsSsDetectTerminateException, InterruptedException {
			
			while ( buffer.hasRemaining() ) {
				
				final Future<Integer> f = this.channel.write(buffer);
				
				try {
					int w = f.get().intValue();
					
					if ( w <= 0 ) {
						throw new HsmsSsDetectTerminateException();
					}
				}
				catch ( InterruptedException e ) {
					f.cancel(true);
					throw e;
				}
			}
		}
		
		protected void reading() throws InterruptedException {
			
			final ByteBuffer lenBf = ByteBuffer.allocate(8);
			final ByteBuffer headBf = ByteBuffer.allocate(10);
			final byte[] headbs = new byte[10];
			final List<byte[]> bodybss = new ArrayList<>();

			try {
				
				for ( ;; ) {
					
					((Buffer)lenBf).clear();
					((Buffer)headBf).clear();
					bodybss.clear();
					
					lenBf.put(emptyBytes);
					
					readToBuffer(channel, lenBf, false);
					
					while ( lenBf.hasRemaining() ) {
						readToBuffer(channel, lenBf);
					}
					
					while ( headBf.hasRemaining() ) {
						readToBuffer(channel, headBf);
					}
					
					((Buffer)lenBf).flip();
					long len = lenBf.getLong() - 10L;
					
					if ( len < 0L ) {
						continue;
					}
					
					this.resetLinktesting();
					
					while ( len > 0L ) {
						
						final ByteBuffer buffer = ByteBuffer.allocate(
								(int)((len > bodyBufferSize) ? bodyBufferSize : len)); 
						
						len -= bodyBufferSize;
						
						while ( buffer.hasRemaining() ) {
							readToBuffer(channel, buffer);
						}
						
						this.resetLinktesting();
						
						((Buffer)buffer).flip();
						byte[] bs = new byte[buffer.remaining()];
						buffer.get(bs);
						
						bodybss.add(bs);
					}
					
					((Buffer)headBf).flip();
					headBf.get(headbs);
					
					try {
						HsmsSsMessage msg = new HsmsSsMessage(
								headbs,
								Secs2BytesParser.getInstance().parse(bodybss));
						
						notifyReceiveMessagePassThrough(msg);
						notifyLog(new HsmsSsReceiveMessageLog(msg));
						
						HsmsSsMessage r = replyMgr.put(msg).orElse(null);
						if ( r != null ) {
							this.recvMsgQueue.put(r);
						}
					}
					catch ( Secs2Exception e ) {
						notifyLog(e);
					}
				}
			}
			catch ( HsmsSsDetectTerminateException | HsmsSsTimeoutT8Exception e ) {
				notifyLog(e);
			}
			catch ( ExecutionException e ) {
				
				Throwable t = e.getCause();
				
				if ( t instanceof RuntimeException ) {
					throw (RuntimeException)t;
				}
				
				notifyLog(t);
			}
		}
		
		private int readToBuffer(AsynchronousSocketChannel channel, ByteBuffer buffer)
				throws HsmsSsDetectTerminateException, HsmsSsTimeoutT8Exception, ExecutionException, InterruptedException {
			
			return readToBuffer(channel, buffer, true);
		}

		private int readToBuffer(AsynchronousSocketChannel channel, ByteBuffer buffer, boolean detectT8Timeout)
				throws HsmsSsDetectTerminateException, HsmsSsTimeoutT8Exception, ExecutionException, InterruptedException {
			
			final Future<Integer> f = channel.read(buffer);
			
			try {
				
				if ( detectT8Timeout ) {
					
					try {
						int r = hsmsSsConfig().timeout().t8().future(f).intValue();
						
						if ( r < 0 ) {
							throw new HsmsSsDetectTerminateException();
						}
						
						return r;
					}
					catch ( TimeoutException e ) {
						throw new HsmsSsTimeoutT8Exception(e);
					}
						
				} else {
					
					int r = f.get().intValue();
					
					if ( r < 0 ) {
						throw new HsmsSsDetectTerminateException();
					}
					
					return r;
				}
			}
			catch ( InterruptedException e ) {
				f.cancel(true);
				throw e;
			}
		}
		
		private final Object syncLinktesting = new Object();
		
		protected void linktesting() throws InterruptedException {
			
			final ReadOnlyTimeProperty tpLinktest = hsmsSsConfig().linktest();
			
			final PropertyChangeListener<Number> lstnr = (Number n) -> {
				synchronized ( this.syncLinktesting ) {
					this.linktestResetted = true;
					this.syncLinktesting.notifyAll();
				}
			};
			
			try {
				
				tpLinktest.addChangeListener(lstnr);
				
				for ( ;; ) {
					
					synchronized ( this.syncLinktesting ) {
						
						if ( tpLinktest.geZero() ) {
							tpLinktest.wait(this.syncLinktesting);
						} else {
							this.syncLinktesting.wait();
						}
						
						if ( this.linktestResetted ) {
							this.linktestResetted = false;
							continue;
						}
					}
					
					if ( this.send(AbstractHsmsSsCommunicator.this.createLinktestRequest())
							.map(HsmsSsMessageType::get)
							.filter(t -> t == HsmsSsMessageType.LINKTEST_RSP)
							.isPresent()) {
						
						continue;
						
					} else {
						
						return;
					}
				}
			}
			catch ( SecsException e ) {
				notifyLog(e);
				return;
			}
			finally {
				tpLinktest.removeChangeListener(lstnr);
			}
		}
		
		private void resetLinktesting() {
			synchronized ( this.syncLinktesting ) {
				this.linktestResetted = true;
			}
		}
		
	}
	
}
