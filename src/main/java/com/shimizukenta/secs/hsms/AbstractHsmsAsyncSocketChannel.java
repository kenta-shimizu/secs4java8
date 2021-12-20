package com.shimizukenta.secs.hsms;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import com.shimizukenta.secs.AbstractSecsLog;
import com.shimizukenta.secs.ReadOnlyTimeProperty;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2BuildException;
import com.shimizukenta.secs.secs2.Secs2BytesPack;
import com.shimizukenta.secs.secs2.Secs2BytesPackBuilder;
import com.shimizukenta.secs.secs2.Secs2BytesParseException;

public abstract class AbstractHsmsAsyncSocketChannel implements HsmsAsyncSocketChannel {
	
	private final AsynchronousSocketChannel channel;
	
	public AbstractHsmsAsyncSocketChannel(AsynchronousSocketChannel channel) {
		this.channel = channel;
	}
	
	private static final byte[] length4Zero = new byte[] {(byte)0, (byte)0, (byte)0, (byte)0};
	
	@Override
	public void reading() throws HsmsException, InterruptedException {
		
		try {
			final ByteBuffer lengthBuffer = ByteBuffer.allocate(8);
			final ByteBuffer headerBuffer = ByteBuffer.allocate(10);
			List<ByteBuffer> bodyBuffers = new ArrayList<>();
			
			for ( ;; ) {
				
				/* reading length */
				((Buffer)lengthBuffer).clear();
				lengthBuffer.put(length4Zero);
				
				this.readingBufferFirst(lengthBuffer);
				
				this.resetLinktestTimer();
				
				while ( lengthBuffer.hasRemaining() ) {
					this.readingBuffer(lengthBuffer);
				}
				
				((Buffer)lengthBuffer).flip();
				long msgLength = lengthBuffer.getLong();
				
				if ( msgLength < 10L ) {
					throw new HsmsMessageLengthBytesLowerThanTenException(msgLength);
				}
				
				/* reading header */
				((Buffer)headerBuffer).clear();
				while ( headerBuffer.hasRemaining() ) {
					this.readingBuffer(headerBuffer);
				}
				
				/* reading body */
				bodyBuffers.clear();
				for (long rem = msgLength - 10L; rem > 0L;) {
					
					long bodySize = prototypeDefaultReceiveBodySize();
					int size = (int)(rem > bodySize ? bodySize : rem);
					ByteBuffer bf = ByteBuffer.allocate(size);
					
					while ( bf.hasRemaining() ) {
						rem -= (long)(this.readingBuffer(bf));
					}
					
					bodyBuffers.add(bf);
					
					this.resetLinktestTimer();
				}
				
				/* build message */
				((Buffer)headerBuffer).flip();
				byte[] headerBytes = new byte[10];
				headerBuffer.get(headerBytes);
				
				final List<byte[]> bodyBytes = bodyBuffers.stream()
						.map(bf -> {
							((Buffer)bf).flip();
							byte[] bs = new byte[bf.remaining()];
							bf.get(bs);
							return bs;
						})
						.collect(Collectors.toList());
				
				AbstractHsmsMessage msg = this.buildMessageFromBytes(headerBytes, bodyBytes);
				
				HsmsMessageType type = msg.messageType();
				if ( ! prototypeCheckControlMessageLength(type, msgLength) ) {
					throw new HsmsControlMessageLengthBytesUpperThanTenException(type, msgLength);
				}
				
				AbstractHsmsMessage r = this.transactionManager().put(msg);
				
				if ( r != null ) {
					this.recvHsmsMsgLstnrs.forEach(l -> {
						l.received(r);
					});
				}
				
				this.notifyReceiveHsmsMessagePassThrough(msg);
				this.notifyLog(new HsmsReceiveMessageLog(msg));
			}
		}
		catch ( Secs2BytesParseException e ) {
			throw new HsmsException(e);
		}
		catch ( ExecutionException e ) {
			
			Throwable t = e.getCause();
			
			if ( t instanceof RuntimeException ) {
				throw (RuntimeException)t;
			}
			
			if ( ! (t instanceof ClosedChannelException) ) {
				throw new HsmsException(t);
			}
		}
	}
	
	private static final long defaultReceiveBodySize = 1024L;
	
	protected long prototypeDefaultReceiveBodySize() {
		return defaultReceiveBodySize;
	}
	
	protected boolean prototypeCheckControlMessageLength(HsmsMessageType type, long length) {
		
		switch ( type ) {
		case SELECT_REQ:
		case SELECT_RSP:
		case DESELECT_REQ:
		case DESELECT_RSP:
		case LINKTEST_REQ:
		case LINKTEST_RSP:
		case REJECT_REQ:
		case SEPARATE_REQ: {
			
			return length == 10L;
			/* break */
		}
		case DATA:
		default: {
			
			return true;
		}
		}
	}
	
	private int readingBufferFirst(ByteBuffer buffer)
			throws HsmsDetectTerminateException,
			ExecutionException, InterruptedException {
		
		final Future<Integer> f = this.channel.read(buffer);
		
		try {
			int r = f.get().intValue();
			
			if ( r < 0 ) {
				throw new HsmsDetectTerminateException();
			}
			
			return r;
		}
		catch ( InterruptedException e ) {
			f.cancel(true);
			throw e;
		}
	}
	
	private int readingBuffer(ByteBuffer buffer)
			throws HsmsTimeoutT8Exception, HsmsDetectTerminateException,
			ExecutionException, InterruptedException {
		
		final Future<Integer> f = this.channel.read(buffer);
		
		try {
			int r = timeoutT8().future(f).intValue();
			
			if ( r < 0 ) {
				throw new HsmsDetectTerminateException();
			}
			
			return r;
		}
		catch ( TimeoutException e ) {
			f.cancel(true);
			throw new HsmsTimeoutT8Exception(e);
		}
		catch ( InterruptedException e ) {
			f.cancel(true);
			throw e;
		}
	}
	
	private final Object syncShutdown = new Object();
	
	@Override
	public void shutdown() {
		synchronized ( this.syncShutdown ) {
			this.syncShutdown.notifyAll();
		}
	}
	
	@Override
	public void waitingUntilShutdown() throws InterruptedException {
		synchronized ( this.syncShutdown ) {
			this.syncShutdown.wait();
		}
	}
	
	abstract protected void notifyLog(AbstractSecsLog log) throws InterruptedException;
	abstract protected void notifyTrySendHsmsMsgPassThrough(HsmsMessage msg) throws InterruptedException;
	abstract protected void notifySendedHsmsMsgPassThrough(HsmsMessage msg) throws InterruptedException;
	abstract protected void notifyReceiveHsmsMessagePassThrough(HsmsMessage msg) throws InterruptedException;
	
	private final Collection<HsmsMessageReceiveListener> recvHsmsMsgLstnrs = new CopyOnWriteArrayList<>();
	
	@Override
	public boolean addHsmsMessageReceiveListener(HsmsMessageReceiveListener l) {
		return this.recvHsmsMsgLstnrs.add(l);
	}
	
	@Override
	public boolean removeHsmsMessageReceiveListener(HsmsMessageReceiveListener l) {
		return this.recvHsmsMsgLstnrs.remove(l);
	}
	
	
	abstract protected HsmsMessageBuilder messageBuilder();
	abstract protected AbstractHsmsMessage buildMessageFromBytes(byte[] header, List<byte[]> bodies) throws Secs2BytesParseException;
	abstract protected AbstractHsmsMessage buildMessageFromMessage(HsmsMessage message);
	abstract protected HsmsTransactionManager<AbstractHsmsMessage> transactionManager();
	abstract protected ReadOnlyTimeProperty timeoutT3();
	abstract protected ReadOnlyTimeProperty timeoutT6();
	abstract protected ReadOnlyTimeProperty timeoutT8();
	abstract protected void resetLinktestTimer();
	
	@Override
	public Optional<HsmsMessage> sendSelectRequest(
			AbstractHsmsSession session)
					throws HsmsSendMessageException,
					HsmsWaitReplyMessageException,
					HsmsException,
					InterruptedException {
		
		final AbstractHsmsMessage msg = this.messageBuilder().buildSelectRequest(session);
		
		final Optional<HsmsMessage> r = this.sendAndReplyHsmsMessage(msg);
		
		HsmsMessageType type = r.get().messageType();
		
		if ( type == HsmsMessageType.REJECT_REQ ) {
			throw new HsmsRejectException(msg);
		}
		
		if ( type != HsmsMessageType.SELECT_RSP ) {
			throw new HsmsNotExpectControlTypeReplyMessageException(msg);
		}
		
		return r;
	}
	
	@Override
	public Optional<HsmsMessage> sendSelectResponse(
			HsmsMessage primaryMsg,
			HsmsMessageSelectStatus status)
					throws HsmsSendMessageException,
					HsmsWaitReplyMessageException,
					HsmsException,
					InterruptedException {
		
		return this.sendAndReplyHsmsMessage(this.messageBuilder().buildSelectResponse(primaryMsg, status));
	}
	
	@Override
	public Optional<HsmsMessage> sendDeselectRequest(
			AbstractHsmsSession session)
					throws HsmsSendMessageException,
					HsmsWaitReplyMessageException,
					HsmsException,
					InterruptedException {
		
		final AbstractHsmsMessage msg = this.messageBuilder().buildDeselectRequest(session);
		
		final Optional<HsmsMessage> r = this.sendAndReplyHsmsMessage(msg);
		
		HsmsMessageType type = r.get().messageType();
		
		if ( type == HsmsMessageType.REJECT_REQ ) {
			throw new HsmsRejectException(msg);
		}
		
		if ( type != HsmsMessageType.DESELECT_RSP ) {
			throw new HsmsNotExpectControlTypeReplyMessageException(msg);
		}
		
		return r;
	}
	
	@Override
	public Optional<HsmsMessage> sendDeselectResponse(
			HsmsMessage primaryMsg,
			HsmsMessageDeselectStatus status)
					throws HsmsSendMessageException,
					HsmsWaitReplyMessageException,
					HsmsException,
					InterruptedException {
		
		return this.sendAndReplyHsmsMessage(this.messageBuilder().buildDeselectResponse(primaryMsg, status));
	}
	
	@Override
	public Optional<HsmsMessage> sendLinktestRequest(
			AbstractHsmsSession session)
					throws HsmsSendMessageException,
					HsmsWaitReplyMessageException,
					HsmsException,
					InterruptedException {
		
		final AbstractHsmsMessage msg = this.messageBuilder().buildLinktestRequest(session);
		
		final Optional<HsmsMessage> r = this.sendAndReplyHsmsMessage(msg);
		
		HsmsMessageType type = r.get().messageType();
		
		if ( type == HsmsMessageType.REJECT_REQ ) {
			throw new HsmsRejectException(msg);
		}
		
		if ( type != HsmsMessageType.LINKTEST_RSP ) {
			throw new HsmsNotExpectControlTypeReplyMessageException(msg);
		}
		
		return r;
	}
	
	@Override
	public Optional<HsmsMessage> sendLinktestResponse(
			HsmsMessage primaryMsg)
					throws HsmsSendMessageException,
					HsmsWaitReplyMessageException,
					HsmsException,
					InterruptedException {
		
		return this.sendAndReplyHsmsMessage(this.messageBuilder().buildLinktestResponse(primaryMsg));
	}
	
	@Override
	public Optional<HsmsMessage> sendRejectRequest(
			HsmsMessage referenceMsg,
			HsmsMessageRejectReason reason)
					throws HsmsSendMessageException,
					HsmsWaitReplyMessageException,
					HsmsException,
					InterruptedException {
		
		return this.sendAndReplyHsmsMessage(this.messageBuilder().buildRejectRequest(referenceMsg, reason));
	}
	
	@Override
	public Optional<HsmsMessage> sendSeparateRequest(
			AbstractHsmsSession session)
					throws HsmsSendMessageException,
					HsmsWaitReplyMessageException,
					HsmsException,
					InterruptedException {
		
		return this.sendAndReplyHsmsMessage(this.messageBuilder().buildSeparateRequest(session));
	}
	
	@Override
	public Optional<HsmsMessage> send(
			AbstractHsmsSession session,
			int strm, int func, boolean wbit, Secs2 secs2)
					throws HsmsSendMessageException,
					HsmsWaitReplyMessageException,
					HsmsException,
					InterruptedException {
		
		return this.sendDataMsg(this.messageBuilder().buildDataMessage(session, strm, func, wbit, secs2));
	}
	
	@Override
	public Optional<HsmsMessage> send(
			SecsMessage primaryMsg,
			int strm, int func, boolean wbit, Secs2 secs2)
					throws HsmsSendMessageException,
					HsmsWaitReplyMessageException,
					HsmsException,
					InterruptedException {
		
		return this.sendDataMsg(this.messageBuilder().buildDataMessage(primaryMsg, strm, func, wbit, secs2));
	}
	
	private Optional<HsmsMessage> sendDataMsg(AbstractHsmsMessage msg)
			throws HsmsSendMessageException,
			HsmsWaitReplyMessageException,
			HsmsException,
			InterruptedException {
		
		final Optional<HsmsMessage> r = this.sendAndReplyHsmsMessage(msg);
		
		HsmsMessageType type = r.map(HsmsMessage::messageType).orElse(null);
		
		if ( type != null ) {
			
			if ( type == HsmsMessageType.REJECT_REQ ) {
				throw new HsmsRejectException(msg);
			}
			
			if ( type != HsmsMessageType.DATA ) {
				throw new HsmsNotExpectControlTypeReplyMessageException(msg);
			}
		}
		
		return r;
	}
	
	@Override
	public Optional<HsmsMessage> sendHsmsMessage(HsmsMessage msg)
			throws HsmsSendMessageException, HsmsWaitReplyMessageException, HsmsException, InterruptedException {
		
		return this.sendAndReplyHsmsMessage(this.buildMessageFromMessage(msg));
	}
	
	private Optional<HsmsMessage> sendAndReplyHsmsMessage(AbstractHsmsMessage msg)
			throws HsmsSendMessageException, HsmsWaitReplyMessageException, HsmsException, InterruptedException {
		
		switch ( msg.messageType() ) {
		case DATA: {
			
			if ( msg.wbit() ) {
				
				try {
					this.transactionManager().enter(msg);
					
					this.sendOnlyHsmsMessage(msg);
					
					AbstractHsmsMessage r = this.transactionManager().reply(msg, this.timeoutT3());
					
					if ( r == null ) {
						throw new HsmsTimeoutT3Exception(msg);
					} else {
						return Optional.of((HsmsMessage)r);
					}
				}
				finally {
					this.transactionManager().exit(msg);
				}
				
			} else {
				
				this.sendOnlyHsmsMessage(msg);
				return Optional.empty();
			}
			
			/* break; */
		}
		case SELECT_REQ:
		case DESELECT_REQ:
		case LINKTEST_REQ: {
			
			try {
				
				this.transactionManager().enter(msg);
				
				this.sendOnlyHsmsMessage(msg);
				
				AbstractHsmsMessage r = this.transactionManager().reply(msg, this.timeoutT6());
				
				if ( r == null ) {
					throw new HsmsTimeoutT6Exception(msg);
				} else {
					return Optional.of((HsmsMessage)r);
				}
			}
			finally {
				this.transactionManager().exit(msg);
			}
			
			/* break; */
		}
		case SELECT_RSP:
		case DESELECT_RSP:
		case LINKTEST_RSP:
		case REJECT_REQ:
		case SEPARATE_REQ:
		default: {
			
			this.sendOnlyHsmsMessage(msg);
			return Optional.empty();
		}
		}
	}
	
	private static final int defaultSendBufferSize = 65536;
	
	protected int prototypeDefaultSendBufferSize() {
		return defaultSendBufferSize;
	}
	
	private final Object syncSendMsg = new Object();
	
	private void sendOnlyHsmsMessage(AbstractHsmsMessage msg)
			throws HsmsSendMessageException, HsmsException, InterruptedException {
		
		this.notifyTrySendHsmsMsgPassThrough(msg);
		this.notifyLog(new HsmsTrySendMessageLog(msg));
		
		synchronized ( this.syncSendMsg ) {
			
			try {
				
				final Secs2BytesPack pack = Secs2BytesPackBuilder.build(1024, msg.secs2());
				
				long len = pack.size() + 10L;
				
				if ( len > 0x00000000FFFFFFFFL || len < 10L ) {
					throw new HsmsTooBigSendMessageException(msg);
				}
				
				long msglen = len + 4;
				
				if ( msglen > (long)(this.prototypeDefaultSendBufferSize()) ) {
					
					{
						final ByteBuffer buffer = ByteBuffer.allocate(14);
						
						buffer.put((byte)(len >> 24));
						buffer.put((byte)(len >> 16));
						buffer.put((byte)(len >>  8));
						buffer.put((byte)(len      ));
						buffer.put(msg.header10Bytes());
						
						((Buffer)buffer).flip();
						
						this.sendByteBuffer(buffer, msg);
					}
					
					for ( byte[] bs : pack.getBytes() ) {
						
						final ByteBuffer buffer = ByteBuffer.allocate(bs.length);
						buffer.put(bs);
						((Buffer)buffer).flip();
						
						this.sendByteBuffer(buffer, msg);
					}
					
				} else {
					
					final ByteBuffer buffer = ByteBuffer.allocate((int)msglen);
					
					buffer.put((byte)(len >> 24));
					buffer.put((byte)(len >> 16));
					buffer.put((byte)(len >>  8));
					buffer.put((byte)(len      ));
					buffer.put(msg.header10Bytes());
					
					for ( byte[] bs : pack.getBytes() ) {
						buffer.put(bs);
					}
					
					((Buffer)buffer).flip();
					
					this.sendByteBuffer(buffer, msg);
				}
			}
			catch ( Secs2BuildException e ) {
				throw new HsmsSendMessageException(msg);
			}
		}
		
		this.notifySendedHsmsMsgPassThrough(msg);
		this.notifyLog(new HsmsSendedMessageLog(msg));
	}
	
	private void sendByteBuffer(ByteBuffer buffer, HsmsMessage msg)
			throws HsmsSendMessageException, HsmsException,
			InterruptedException {
		
		while ( buffer.hasRemaining() ) {
			
			final Future<Integer> f = this.channel.write(buffer);
			
			try {
				int w = f.get().intValue();
				
				if ( w <= 0 ) {
					throw new HsmsSendMessageException(
							msg,
							new HsmsDetectTerminateException());
				}
			}
			catch ( InterruptedException e ) {
				f.cancel(true);
				throw e;
			}
			catch ( ExecutionException e ) {
				
				Throwable t = e.getCause();
				
				if ( t instanceof RuntimeException ) {
					throw (RuntimeException)t;
				}
				
				throw new HsmsSendMessageException(msg, t);
			}
		}
		
		this.resetLinktestTimer();
	}
	
}
