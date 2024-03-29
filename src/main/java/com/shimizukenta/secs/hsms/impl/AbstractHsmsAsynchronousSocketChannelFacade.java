package com.shimizukenta.secs.hsms.impl;

import java.io.Closeable;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import com.shimizukenta.secs.ExecutorServiceShutdownFailedException;
import com.shimizukenta.secs.hsms.AbstractHsmsCommunicatorConfig;
import com.shimizukenta.secs.hsms.HsmChannelAlreadyShutdownException;
import com.shimizukenta.secs.hsms.HsmsControlMessageLengthBytesGreaterThanTenException;
import com.shimizukenta.secs.hsms.HsmsDetectTerminateException;
import com.shimizukenta.secs.hsms.HsmsException;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessageLengthBytesLowerThanTenException;
import com.shimizukenta.secs.hsms.HsmsMessageType;
import com.shimizukenta.secs.hsms.HsmsRejectException;
import com.shimizukenta.secs.hsms.HsmsSendMessageException;
import com.shimizukenta.secs.hsms.HsmsTimeoutT3Exception;
import com.shimizukenta.secs.hsms.HsmsTimeoutT6Exception;
import com.shimizukenta.secs.hsms.HsmsTimeoutT8Exception;
import com.shimizukenta.secs.hsms.HsmsTooBigSendMessageException;
import com.shimizukenta.secs.hsms.HsmsWaitReplyMessageException;
import com.shimizukenta.secs.local.property.BooleanProperty;
import com.shimizukenta.secs.local.property.ChangeListener;
import com.shimizukenta.secs.local.property.TimeoutAndUnit;
import com.shimizukenta.secs.local.property.TimeoutGettable;
import com.shimizukenta.secs.secs2.Secs2BytesParseException;

public abstract class AbstractHsmsAsynchronousSocketChannelFacade implements Closeable {

	private final ExecutorService executorService = Executors.newCachedThreadPool(r -> {
		Thread th = new Thread(r);
		th.setDaemon(false);
		return th;
	});
	
	private final BooleanProperty shutdownProp = BooleanProperty.newInstance(false);
	
	private final AbstractHsmsCommunicatorConfig config;
	private final AsynchronousSocketChannel channel;
	
	public AbstractHsmsAsynchronousSocketChannelFacade(
			AbstractHsmsCommunicatorConfig config,
			AsynchronousSocketChannel channel) {
		
		this.config = config;
		this.channel = channel;
		
		this.executorService.execute(() -> {
			try {
				this.taskSendMessage();
			}
			catch (InterruptedException ignore) {
			}
			catch (Throwable t) {
				this.notifyHsmsThrowableLog(t);
			}
		});
		
		this.executorService.execute(() -> {
			try {
				this.taskReceiveMessage();
			}
			catch (InterruptedException ignore) {
			}
			catch (Throwable t) {
				this.notifyHsmsThrowableLog(t);
			}
		});
		
		this.executorService.execute(() -> {
			try {
				this.taskLinktest();
			}
			catch (InterruptedException ignore) {
			}
			catch (Throwable t) {
				this.notifyHsmsThrowableLog(t);
			}
		});
	}
	
	@Override
	public void close() throws IOException {
		try {
			this.executorService.shutdown();
			if (! this.executorService.awaitTermination(1L, TimeUnit.MILLISECONDS)) {
				this.executorService.shutdownNow();
				if (! this.executorService.awaitTermination(5L, TimeUnit.SECONDS)) {
					throw new ExecutorServiceShutdownFailedException();
				}
			}
		}
		catch (InterruptedException ignore) {
		}
	}
	
	public AsynchronousSocketChannel getAsynchronousSocketChannel() {
		return this.channel;
	}
	
	abstract protected void notifyTrySendHsmsMessagePassThrough(HsmsMessage msg) throws InterruptedException;
	abstract protected void notifySendedHsmsMessagePassThrough(HsmsMessage msg) throws InterruptedException;
	abstract protected void notifyReceiveHsmsMessagePassThrough(HsmsMessage msg) throws InterruptedException;
	abstract protected AbstractHsmsMessage buildLinktestHsmsMessage();
	abstract protected boolean notifyHsmsThrowableLog(Throwable t);
	
	
	public void waitUntilShutdown() throws InterruptedException {
		this.shutdownProp.waitUntilTrue();
	}
	
	public void shutdown() {
		this.shutdownProp.setTrue();
	}
	
	private static Integer getSystemBytesKey(HsmsMessage msg) {
		byte[] bs = msg.header10Bytes();
		int i = (((int)(bs[6]) << 24) & 0xFF000000) |
				(((int)(bs[7]) << 16) & 0x00FF0000) |
				(((int)(bs[8]) <<  8) & 0x0000FF00) |
				((int)(bs[9]) & 0x000000FF);
		return Integer.valueOf(i);
	}
	
	private class SendAndReceiveMsgPack {
		
		public final HsmsMessage sendMsg;
		public boolean sended;
		public HsmsSendMessageException sendException;
		public AbstractHsmsMessage recvMsg;
		
		public SendAndReceiveMsgPack(HsmsMessage sendMsg) {
			this.sendMsg = sendMsg;
			this.sended = false;
			this.sendException = null;
			this.recvMsg = null;
		}
	}
	
	private TimeoutGettable getTimeout(HsmsMessage msg) {
		switch (msg.messageType()) {
		case DATA: {
			if (msg.wbit()) {
				return this.config.timeout().t3();
			}
			break;
		}
		case SELECT_REQ:
		case DESELECT_REQ:
		case LINKTEST_REQ: {
			return this.config.timeout().t6();
		}
		default: {
			/* Nothing */
		}
		}
		return null;
	}
	
	private final Map<Integer, SendAndReceiveMsgPack> transactionMap = new HashMap<>();
	private final BlockingQueue<SendAndReceiveMsgPack> sendMsgQueue = new LinkedBlockingQueue<>();
	
	public Optional<HsmsMessage> send(HsmsMessage msg)
			throws HsmsSendMessageException,
			HsmsWaitReplyMessageException,
			InterruptedException {
		
		if (this.shutdownProp.booleanValue()) {
			throw new HsmChannelAlreadyShutdownException(msg);
		}
		
		final Integer key = getSystemBytesKey(msg);
		
		try {
			final TimeoutGettable timeout = this.getTimeout(msg);
			final SendAndReceiveMsgPack pack = new SendAndReceiveMsgPack(msg);
			
			if (timeout != null) {
				synchronized (this.transactionMap) {
					this.transactionMap.put(key, pack);
				}
			}
			
			sendMsgQueue.put(pack);
			
			this.waitUntilSended(pack);
			
			if (timeout == null) {
				return Optional.empty();
			} else {
				return Optional.of(this.waitUntilReceived(pack, timeout));
			}
		}
		finally {
			synchronized (this.transactionMap) {
				this.transactionMap.remove(key);
			}
		}
	}
	
	private void waitUntilSended(SendAndReceiveMsgPack pack)
			throws HsmsSendMessageException,
			InterruptedException {
		
		final Future<SendAndReceiveMsgPack> future = this.executorService.submit(() -> {
			
			try {
				synchronized (this.transactionMap) {
					for ( ;; ) {
						if (pack.sended) {
							return pack;
						}
						this.transactionMap.wait();
					}
				}
			}
			catch (InterruptedException ignore) {
			}
			
			return null;
		});
		
		try {
			TimeoutGettable timeout = pack.sendMsg.isDataMessage() ? this.config.timeout().t3() : this.config.timeout().t6();
			SendAndReceiveMsgPack r = timeout.futureGet(future);
			
			if (r.sendException != null) {
				throw r.sendException;
			}
		}
		catch (TimeoutException e) {
			future.cancel(true);
			throw new HsmsSendMessageException(pack.sendMsg, e);
		}
		catch (ExecutionException e) {
			
			Throwable t = e.getCause();
			
			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			
			throw new HsmsSendMessageException(pack.sendMsg, t);
		}
		catch (InterruptedException e) {
			future.cancel(true);
			throw e;
		}
	}
	
	private AbstractHsmsMessage waitUntilReceived(
			SendAndReceiveMsgPack pack,
			TimeoutGettable timeout)
					throws HsmsWaitReplyMessageException,
					InterruptedException {
		
		final Future<AbstractHsmsMessage> future = this.executorService.submit(() -> {
			
			try {
				synchronized (this.transactionMap) {
					for ( ;; ) {
						if (pack.recvMsg != null) {
							if (pack.recvMsg.messageType() == HsmsMessageType.REJECT_REQ) {
								throw new HsmsRejectException(pack.sendMsg);
							}
							return pack.recvMsg;
						}
						this.transactionMap.wait();
					}
				}
			}
			catch (InterruptedException ignore) {
			}
			
			return null;
		});
		
		try {
			return timeout.futureGet(future);
		}
		catch (TimeoutException e) {
			
			future.cancel(true);
			
			if (pack.sendMsg.isDataMessage()) {
				throw new HsmsTimeoutT3Exception(pack.sendMsg);
			} else {
				throw new HsmsTimeoutT6Exception(pack.sendMsg);
			}
		}
		catch (ExecutionException e) {
			throw new HsmsWaitReplyMessageException(pack.sendMsg, e.getCause());
		}
		catch (InterruptedException e) {
			future.cancel(true);
			throw e;
		}
	}
	
	private void notifySendResultSuccess(SendAndReceiveMsgPack pack) {
		synchronized (this.transactionMap) {
			pack.sended = true;
			this.transactionMap.notifyAll();
		}
	}
	
	private void notifySendResultFailed(
			SendAndReceiveMsgPack pack,
			HsmsSendMessageException e) {
		
		synchronized (this.transactionMap) {
			pack.sended = true;
			pack.sendException = e;
			this.transactionMap.notifyAll();
		}
	}
	
	private AbstractHsmsMessage putMessageToTransaction(AbstractHsmsMessage msg) throws InterruptedException {
		
		final Integer key = getSystemBytesKey(msg);
		
		synchronized (this.transactionMap) {
			
			SendAndReceiveMsgPack pack = this.transactionMap.get(key);
			
			if (pack == null) {
				return msg;
			} else {
				pack.recvMsg = msg;
				this.transactionMap.notifyAll();
				return null;
			}
		}
	}
	
	private final BlockingQueue<AbstractHsmsMessage> primaryMsgQueue = new LinkedBlockingQueue<>();
	
	public AbstractHsmsMessage takePrimaryHsmsMessage() throws InterruptedException {
		return this.primaryMsgQueue.take();
	}
	
	public AbstractHsmsMessage pollPrimaryHsmsMessage(TimeoutGettable timeout) throws InterruptedException {
		return timeout.blockingQueuePoll(this.primaryMsgQueue);
	}
	
	private void taskSendMessage() throws InterruptedException {
		
		try {
			try {
				for ( ;; ) {
					
					final SendAndReceiveMsgPack pack = this.sendMsgQueue.take();
					
					if (this.shutdownProp.booleanValue()) {
						this.notifySendResultFailed(
								pack,
								new HsmChannelAlreadyShutdownException(pack.sendMsg));
						return;
					}
					
					this.notifyTrySendHsmsMessagePassThrough(pack.sendMsg);
					
					final List<byte[]> bss = pack.sendMsg.secs2().getBytesList(1024);
					
					long len = 0L;
					
					for ( byte[] bs : bss ) {
						len += (long)(bs.length);
					}
					
					len += 10L;
					
					if ((len > 0x00000000FFFFFFFFL) || (len < 10L)) {
						
						this.notifySendResultFailed(
								pack,
								new HsmsTooBigSendMessageException(pack.sendMsg));
						
					} else {
						
						long msglen = len + 4;
						
						if (msglen > (long)(this.prototypeDefaultSendBufferSize())) {
							
							{
								final ByteBuffer buffer = ByteBuffer.allocate(14);
								
								buffer.put((byte)(len >> 24));
								buffer.put((byte)(len >> 16));
								buffer.put((byte)(len >>  8));
								buffer.put((byte)(len      ));
								buffer.put(pack.sendMsg.header10Bytes());
								
								((Buffer)buffer).flip();
								
								this.sendByteBuffer(buffer);
							}
							
							for (byte[] bs : bss) {
								
								final ByteBuffer buffer = ByteBuffer.allocate(bs.length);
								buffer.put(bs);
								((Buffer)buffer).flip();
								
								this.sendByteBuffer(buffer);
							}
							
						} else {
							
							final ByteBuffer buffer = ByteBuffer.allocate((int)msglen);
							
							buffer.put((byte)(len >> 24));
							buffer.put((byte)(len >> 16));
							buffer.put((byte)(len >>  8));
							buffer.put((byte)(len      ));
							buffer.put(pack.sendMsg.header10Bytes());
							
							for (byte[] bs : bss) {
								buffer.put(bs);
							}
							
							((Buffer)buffer).flip();
							
							this.sendByteBuffer(buffer);
						}
						
						this.notifySendResultSuccess(pack);
						
						this.notifySendedHsmsMessagePassThrough(pack.sendMsg);
					}
				}
			}
			catch (ExecutionException e) {
				Throwable t = e.getCause();
				
				if (t instanceof RuntimeException) {
					throw (RuntimeException)t;
				}
				
				if (! (t instanceof ClosedChannelException)) {
					throw new HsmsException(t);
				}
			}
		}
		catch (HsmsException e) {
			this.shutdown();
			this.notifyHsmsThrowableLog(e);
		}
	}
	
	private static final int defaultSendBufferSize = 65536;
	
	protected int prototypeDefaultSendBufferSize() {
		return defaultSendBufferSize;
	}
	
	private void sendByteBuffer(ByteBuffer buffer)
			throws HsmsException,
			ExecutionException,
			InterruptedException {
		
		while (buffer.hasRemaining()) {
			
			final Future<Integer> future = this.channel.write(buffer);
			
			try {
				int w = future.get().intValue();
				
				if (w <= 0) {
					throw new HsmsDetectTerminateException();
				}
			}
			catch (InterruptedException e) {
				future.cancel(true);
				throw e;
			}
		}
		
		this.linktestReset();
	}
	
	private static final byte[] length4Zero = new byte[] {(byte)0, (byte)0, (byte)0, (byte)0};
	
	private void taskReceiveMessage() throws InterruptedException {
		
		final ByteBuffer lengthBuffer = ByteBuffer.allocate(8);
		final ByteBuffer headerBuffer = ByteBuffer.allocate(10);
		final List<ByteBuffer> bodyBuffers = new ArrayList<>();
		
		try {
			try {
				for ( ;; ) {
					
					/* reading length-bytes */
					((Buffer)(lengthBuffer)).clear();
					lengthBuffer.put(length4Zero);
					
					this.readingBufferFirst(lengthBuffer);
					
					this.linktestReset();
					
					while (lengthBuffer.hasRemaining()) {
						this.readingBuffer(lengthBuffer);
					}
					
					((Buffer)lengthBuffer).flip();
					long msgLength = lengthBuffer.getLong();
					if ( msgLength < 10L ) {
						throw new HsmsMessageLengthBytesLowerThanTenException(msgLength);
					}
					
					/* reading header-10-bytes */
					((Buffer)(headerBuffer)).clear();
					
					((Buffer)headerBuffer).clear();
					while ( headerBuffer.hasRemaining() ) {
						this.readingBuffer(headerBuffer);
					}
					
					/* reading body */
					bodyBuffers.clear();
					for (long rem = msgLength - 10L; rem > 0L;) {
						
						long bodySize = this.prototypeDefaultReceiveBodySize();
						int size = (int)(rem > bodySize ? bodySize : rem);
						ByteBuffer bf = ByteBuffer.allocate(size);
						
						while ( bf.hasRemaining() ) {
							rem -= (long)(this.readingBuffer(bf));
						}
						
						bodyBuffers.add(bf);
						
						this.linktestReset();
					}
					
					/* build message */
					((Buffer)headerBuffer).flip();
					byte[] header10Bytes = new byte[10];
					headerBuffer.get(header10Bytes);
					
					final List<byte[]> bodyBytesList = bodyBuffers.stream()
							.map(bf -> {
								((Buffer)bf).flip();
								byte[] bs = new byte[bf.remaining()];
								bf.get(bs);
								return bs;
							})
							.collect(Collectors.toList());
					
					try {
						AbstractHsmsMessage msg = AbstractHsmsMessageBuilder.buildFromBytes(header10Bytes, bodyBytesList);
						
						{
							HsmsMessageType type = msg.messageType();
							if (! prototypeCheckControlMessageLength(type, msgLength)) {
								throw new HsmsControlMessageLengthBytesGreaterThanTenException(type, msgLength);
							}
						}
						
						AbstractHsmsMessage r = this.putMessageToTransaction(msg);
						
						if (r != null) {
							this.primaryMsgQueue.put(r);
						}
						
						this.notifyReceiveHsmsMessagePassThrough(msg);
					}
					catch (Secs2BytesParseException e) {
						throw new HsmsException(e);
					}
				}
			}
			catch (ExecutionException e) {
				Throwable t = e.getCause();
				
				if (t instanceof RuntimeException) {
					throw (RuntimeException)t;
				}
				if (! (t instanceof ClosedChannelException)) {
					throw new HsmsException(t);
				}
			}
		}
		catch (HsmsException e) {
			this.shutdown();
			this.notifyHsmsThrowableLog(e);
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
			ExecutionException,
			InterruptedException {
		
		final Future<Integer> future = this.channel.read(buffer);
		
		try {
			int r = future.get().intValue();
			
			if (r < 0) {
				throw new HsmsDetectTerminateException();
			}
			
			return r;
		}
		catch (InterruptedException e) {
			future.cancel(true);
			throw e;
		}
	}
	
	private int readingBuffer(ByteBuffer buffer)
			throws HsmsTimeoutT8Exception,
			HsmsDetectTerminateException,
			ExecutionException,
			InterruptedException {
		
		final Future<Integer> future = this.channel.read(buffer);
		
		try {
			int r = this.config.timeout().t8().futureGet(future).intValue();
			
			if (r < 0) {
				throw new HsmsDetectTerminateException();
			}
			
			return r;
		}
		catch (TimeoutException e) {
			future.cancel(true);
			throw new HsmsTimeoutT8Exception(e);
		}
		catch (InterruptedException e) {
			future.cancel(true);
			throw e;
		}
	}
	
	/**
	 * Returns true if linktest success, otherwise false.
	 * 
	 * @return true if linktest success, otherwise false.
	 * @throws InterruptedException if interrupted
	 */
	public boolean linktest() throws InterruptedException {
		boolean result = false;
		try {
			result = this.send(this.buildLinktestHsmsMessage())
					.map(m -> m.messageType())
					.filter(type -> type == HsmsMessageType.LINKTEST_RSP)
					.isPresent();
		}
		catch (HsmsSendMessageException | HsmsWaitReplyMessageException failed) {
		}
		
		if (! result) {
			this.shutdown();
		}
		
		return result;
	}
	
	private final BooleanProperty linktestResetFlag = BooleanProperty.newInstance(false);
	
	private void linktestReset() {
		synchronized (this.linktestResetFlag) {
			this.linktestResetFlag.setTrue();
			this.linktestResetFlag.notifyAll();
		}
	}
	
	private void taskLinktest() throws InterruptedException {
		
		final ChangeListener<Boolean> doLinkTestLstnr = v -> {this.linktestReset();};
		final ChangeListener<TimeoutAndUnit> linktestTimeLstnr = v -> {this.linktestReset();};
		
		try {
			this.config.doLinktest().addChangeListener(doLinkTestLstnr);
			this.config.linktestTime().addChangeListener(linktestTimeLstnr);
			
			for ( ;; ) {
				
				synchronized (this.linktestResetFlag) {
					
					this.linktestResetFlag.setFalse();
					
					if (this.config.doLinktest().booleanValue()) {
						this.linktestResetFlag.wait();
					} else {
						this.config.linktestTime().wait(this.linktestResetFlag);
					}
					
					if (this.linktestResetFlag.booleanValue()) {
						continue;
					}
					if (! this.config.doLinktest().booleanValue()) {
						continue;
					}
				}
				
				if (! this.linktest()) {
					this.shutdown();
					return;
				}
			}
		}
		finally {
			this.config.doLinktest().removeChangeListener(doLinkTestLstnr);
			this.config.linktestTime().removeChangeListener(linktestTimeLstnr);
		}
	}
	
}
