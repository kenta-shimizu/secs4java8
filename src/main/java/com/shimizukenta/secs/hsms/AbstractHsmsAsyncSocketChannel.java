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

import com.shimizukenta.secs.ReadOnlyTimeProperty;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2BytesParseException;

public abstract class AbstractHsmsAsyncSocketChannel implements HsmsAsyncSocketChannel {
	
	private final AsynchronousSocketChannel channel;
	
	public AbstractHsmsAsyncSocketChannel(AsynchronousSocketChannel channel) {
		this.channel = channel;
	}
	
	private static final byte[] length4Zero = new byte[] {(byte)0, (byte)0, (byte)0, (byte)0};
	private static final long defaultBodySize = 1024L;
	
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
				
				while ( lengthBuffer.hasRemaining() ) {
					this.readingBuffer(lengthBuffer);
				}
				
				((Buffer)lengthBuffer).flip();
				long rem = lengthBuffer.getLong();
				
				if ( rem < 10L ) {
					
					//TODO
					//throw exception
				}
				
				/* reading header */
				((Buffer)headerBuffer).clear();
				while ( headerBuffer.hasRemaining() ) {
					this.readingBuffer(headerBuffer);
				}
				
				/* reading body */
				bodyBuffers.clear();
				rem -= 10L;
				while (rem > 0L) {
					
					int size = (int)(rem > defaultBodySize ? defaultBodySize : rem);
					ByteBuffer bf = ByteBuffer.allocate(size);
					
					rem -= (long)(this.readingBuffer(bf));
					bodyBuffers.add(bf);
				}
				
				/* build message */
				((Buffer)headerBuffer).flip();
				final byte[] headerBytes = new byte[10];
				headerBuffer.put(headerBytes);
				
				final List<byte[]> bodyBytes = bodyBuffers.stream()
						.map(bf -> {
							((Buffer)bf).flip();
							byte[] bs = new byte[bf.remaining()];
							bf.put(bs);
							return bs;
						})
						.collect(Collectors.toList());
				
				AbstractHsmsMessage msg = this.messageBuilder().fromBytes(headerBytes, bodyBytes);
				
				AbstractHsmsMessage r = this.transactionManager().put(msg);
				
				if ( r != null ) {
					this.recvLstnrs.forEach(l -> {
						l.received(r);
					});
				}
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
			throws HsmsT8TimeoutException, HsmsDetectTerminateException,
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
			throw new HsmsT8TimeoutException(e);
		}
		catch ( InterruptedException e ) {
			f.cancel(true);
			throw e;
		}
	}
	
	public void linktesting() throws InterruptedException {
		
		//TODO
	}
	
	private final Collection<HsmsMessageReceiveListener> recvLstnrs = new CopyOnWriteArrayList<>();
	
	@Override
	public boolean addHsmsMessageReceiveListener(HsmsMessageReceiveListener l) {
		return this.recvLstnrs.add(l);
	}
	
	@Override
	public boolean removeHsmsMessageReceiveListener(HsmsMessageReceiveListener l) {
		return this.recvLstnrs.remove(l);
	}
	
	abstract protected HsmsMessageBuilder messageBuilder();
	abstract protected HsmsTransactionManager<AbstractHsmsMessage> transactionManager();
	abstract ReadOnlyTimeProperty timeoutT3();
	abstract ReadOnlyTimeProperty timeoutT6();
	abstract ReadOnlyTimeProperty timeoutT8();
	abstract ReadOnlyTimeProperty linktest();
	
	@Override
	public AbstractHsmsMessage sendSelectRequest(
			AbstractHsmsSession session)
					throws SecsSendMessageException,
					SecsWaitReplyMessageException,
					SecsException,
					InterruptedException {
		
		//TODO
		
		return null;
	}
	
	@Override
	public AbstractHsmsMessage sendSelectResponse(
			HsmsMessage primaryMsg,
			HsmsMessageSelectStatus status)
					throws SecsSendMessageException,
					SecsWaitReplyMessageException,
					SecsException,
					InterruptedException {
		
		//TODO
		
		return null;
	}
	
	@Override
	public Optional<HsmsMessage> sendDeselectRequest(
			AbstractHsmsSession session)
					throws SecsSendMessageException,
					SecsWaitReplyMessageException,
					SecsException,
					InterruptedException {
		
		//TODO
		
		return null;
	}
	
	@Override
	public Optional<HsmsMessage> sendDeselectResponse(
			HsmsMessage primaryMsg,
			HsmsMessageDeselectStatus status)
					throws SecsSendMessageException,
					SecsWaitReplyMessageException,
					SecsException,
					InterruptedException {
		
		//TODO
		
		return null;
	}
			
	@Override
	public Optional<HsmsMessage> sendLinktestRequest(
			AbstractHsmsSession session)
					throws SecsSendMessageException,
					SecsWaitReplyMessageException,
					SecsException,
					InterruptedException {
		
		//TODO
		
		return null;
	}
	
	@Override
	public Optional<HsmsMessage> sendLinktestResponse(
			HsmsMessage primaryMsg)
					throws SecsSendMessageException,
					SecsWaitReplyMessageException,
					SecsException,
					InterruptedException {
		
		//TODO
		
		return null;
	}
			
	@Override
	public Optional<HsmsMessage> sendRejectRequest(
			HsmsMessage referenceMsg,
			HsmsMessageRejectReason reason)
					throws SecsSendMessageException,
					SecsWaitReplyMessageException,
					SecsException,
					InterruptedException {
		
		//TODO
		
		return null;
	}
	
	@Override
	public Optional<HsmsMessage> sendSeparateRequest(
			AbstractHsmsSession session)
					throws SecsSendMessageException,
					SecsWaitReplyMessageException,
					SecsException,
					InterruptedException {
		
		//TOOD
		
		return null;
	}
	
	@Override
	public Optional<HsmsMessage> send(AbstractHsmsSession session, int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		
		return sendAndReplyHsmsMessage(this.messageBuilder().buildDataMessage(session, strm, func, wbit, secs2));
	}
	
	@Override
	public Optional<HsmsMessage> send(SecsMessage primaryMsg, int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		
		return sendAndReplyHsmsMessage(this.messageBuilder().buildDataMessage(primaryMsg, strm, func, wbit, secs2));
	}
	
	private Optional<HsmsMessage> sendAndReplyHsmsMessage(AbstractHsmsMessage msg)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		
		switch ( msg.messageType() ) {
		case DATA: {
			
			if ( msg.wbit() ) {
				
				try {
					this.transactionManager().enter(msg);
					
					this.sendOnlyHsmsMessage(msg);
					
					AbstractHsmsMessage r = this.transactionManager().reply(msg, this.timeoutT6());
					
					if ( r == null ) {
						throw new HsmsT3TimeoutException(msg);
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
					throw new HsmsT6TimeoutException(msg);
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
	
	private void sendOnlyHsmsMessage(AbstractHsmsMessage msg)
			throws SecsSendMessageException, SecsException, InterruptedException {
		
		//TODO
		//try-send
		
		//TODO
		//send
		
		//TODO
		//sended
	}
	
}
