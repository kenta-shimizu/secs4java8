package com.shimizukenta.secs.secs1;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import com.shimizukenta.secs.AbstractSecsCommunicator;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsLog;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.secs2.Secs2;

public abstract class Secs1Communicator extends AbstractSecsCommunicator {
	
	protected static final byte ENQ = (byte)0x05;
	protected static final byte EOT = (byte)0x04;
	protected static final byte ACK = (byte)0x06;
	protected static final byte NAK = (byte)0x15;
	
	
	private final ExecutorService execServ = Executors.newCachedThreadPool(r -> {
		Thread th = new Thread(r);
		th.setDaemon(true);
		return th;
	});
	
	private final Secs1CommunicatorConfig secs1Config;
	private final Secs1SendReplyManager sendReplyManager;
	
	
	public Secs1Communicator(Secs1CommunicatorConfig config) {
		super(config);
		
		this.secs1Config = config;
		this.sendReplyManager = new Secs1SendReplyManager(this);
	}
	
	protected Secs1CommunicatorConfig secs1Config() {
		return secs1Config;
	}
	
	protected ExecutorService executorService() {
		return execServ;
	}
	
	@Override
	public void open() throws IOException {
		super.open();
		
		execServ.execute(taskRecvMsg);
		execServ.execute(taskNotifyLog);
		execServ.execute(taskTrySendMsgPassThroughQueue);
		execServ.execute(taskSendedMsgPassThroughQueue);
		execServ.execute(taskRecvMsgPassThroughQueue);
		
		execServ.execute(new Loop());
	}

	@Override
	public void close() throws IOException {
		
		IOException ioExcept = null;
		
		try {
			synchronized ( this ) {
				if ( isClosed() ) {
					return;
				}
				
				super.close();
			}
		}
		catch ( IOException e ) {
			ioExcept = e;
		}
		
		try {
			execServ.shutdown();
			if ( ! execServ.awaitTermination(10L, TimeUnit.MILLISECONDS) ) {
				execServ.shutdownNow();
				if ( ! execServ.awaitTermination(5L, TimeUnit.SECONDS) ) {
					ioExcept = new IOException("ExecutorService#shutdown failed");
				}
			}
		}
		catch ( InterruptedException giveup ) {
		}
		
		if ( ioExcept != null ) {
			throw ioExcept;
		}
	}
	
	
	/* Receive Message Queue */
	private final BlockingQueue<Secs1Message> recvDataMsgQueue = new LinkedBlockingQueue<>();
	
	protected void putReceiveDataMessage(Secs1Message msg) throws InterruptedException {
		recvDataMsgQueue.put(msg);
	}
	
	@Override
	protected void notifyReceiveMessage(SecsMessage msg) {
		throw new UnsupportedOperationException("use #putReceiveDataMessage");
	}
	
	private final Runnable taskRecvMsg = new Runnable() {

		@Override
		public void run() {
			
			try {
				for ( ;; ) {
					Secs1Communicator.super.notifyReceiveMessage(recvDataMsgQueue.take());
				}
			}
			catch ( InterruptedException ignore ) {
			}
		}
	};
	
	
	/* Log Queue */
	private final BlockingQueue<SecsLog> logQueue = new LinkedBlockingQueue<>();
	
	@Override
	protected void notifyLog(SecsLog log) {
		logQueue.offer(log);
	}
	
	private final Runnable taskNotifyLog = new Runnable() {

		@Override
		public void run() {
			try {
				for ( ;; ) {
					Secs1Communicator.super.notifyLog(logQueue.take());
				}
			}
			catch ( InterruptedException ignore ) {
			}
		}
	};
	
	
	/* trySendMsgPassThroughQueue */
	private final BlockingQueue<Secs1Message> trySendMsgPassThroughQueue = new LinkedBlockingQueue<>();
	
	@Override
	protected void notifyTrySendMessagePassThrough(SecsMessage msg) {
		throw new UnsupportedOperationException("use #putTrySendMessagePassThrough");
	}
	
	protected void putTrySendMessagePassThrough(Secs1Message msg) throws InterruptedException {
		trySendMsgPassThroughQueue.put(msg);
	}
	
	private final Runnable taskTrySendMsgPassThroughQueue = new Runnable() {

		@Override
		public void run() {
			try {
				for ( ;; ) {
					Secs1Communicator.super.notifyTrySendMessagePassThrough(trySendMsgPassThroughQueue.take());
				}
			}
			catch ( InterruptedException ignore ) {
			}
		}
	};
	
	
	/* sendedPassThroughQueue */
	private final BlockingQueue<Secs1Message> sendedMsgPassThroughQueue = new LinkedBlockingQueue<>();
	
	@Override
	protected void notifySendedMessagePassThrough(SecsMessage msg) {
		throw new UnsupportedOperationException("use #putSendedMessagePassThrough");
	}
	
	protected void putSendedMessagePassThrough(Secs1Message msg) throws InterruptedException {
		sendedMsgPassThroughQueue.put(msg);
	}
	
	private final Runnable taskSendedMsgPassThroughQueue = new Runnable() {

		@Override
		public void run() {
			try {
				for ( ;; ) {
					Secs1Communicator.super.notifySendedMessagePassThrough(sendedMsgPassThroughQueue.take());
				}
			}
			catch ( InterruptedException ignore ) {
			}
		}
	};

	
	/* sendedPassThroughQueue */
	private final BlockingQueue<Secs1Message> recvMsgPassThroughQueue = new LinkedBlockingQueue<>();
	
	@Override
	protected void notifyReceiveMessagePassThrough(SecsMessage msg) {
		throw new UnsupportedOperationException("use #putReceiveMessagePassThrough");
	}
	
	protected void putReceiveMessagePassThrough(Secs1Message msg) throws InterruptedException {
		recvMsgPassThroughQueue.put(msg);
	}
	
	private final Runnable taskRecvMsgPassThroughQueue = new Runnable() {

		@Override
		public void run() {
			try {
				for ( ;; ) {
					Secs1Communicator.super.notifyReceiveMessagePassThrough(recvMsgPassThroughQueue.take());
				}
			}
			catch ( InterruptedException ignore ) {
			}
		}
	};
	
	
	abstract protected Optional<Byte> pollByte();
	abstract protected Optional<Byte> pollByte(long timeout, TimeUnit unit) throws InterruptedException;
	abstract protected Optional<Byte> pollByte(byte[] request, long timeout, TimeUnit unit) throws InterruptedException;
	
	private Optional<Byte> pollByteTx(float timeout) throws InterruptedException {
		return pollByte((long)(timeout * 1000.0F), TimeUnit.MILLISECONDS);
	}
	
	private Optional<Byte> pollByteT1() throws InterruptedException {
		return pollByteTx(secs1Config.timeout().t1());
	}
	
	private Optional<Byte> pollByteT2() throws InterruptedException {
		return pollByteTx(secs1Config.timeout().t2());
	}
	
	private Optional<Byte> pollByteT4() throws InterruptedException {
		return pollByteTx(secs1Config.timeout().t4());
	}
	
	protected void pollByteUntilEmpty() {
		while (pollByte().isPresent());
	}
	
	
	abstract protected void sendByte(byte[] bs) throws SecsSendMessageException, SecsException, InterruptedException;
	
	private void sendByte(byte b) throws SecsSendMessageException, SecsException, InterruptedException {
		sendByte(new byte[] {b});
	}
	
	
	public Optional<Secs1Message> send(Secs1Message msg)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		try {
			return sendReplyManager.send(msg);
		}
		catch (SecsException e) {
			notifyLog(new SecsLog(e));
			throw e;
		}
	}
	
	
	public Secs1Message createSecs1Message(byte[] head) {
		return createSecs1Message(head, Secs2.empty());
	}
	
	public Secs1Message createSecs1Message(byte[] head, Secs2 body) {
		return new Secs1Message(head, body);
	}
	
	
	private final AtomicInteger autoNumber = new AtomicInteger();
	
	@Override
	public Optional<SecsMessage> send(int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		int devid = secs1Config().deviceId();
		boolean rbit = secs1Config().isEquip();
		int num = autoNumber.incrementAndGet();
		
		byte[] head = new byte[] {
				(byte)(devid >> 8)
				, (byte)(devid)
				, (byte)strm
				, (byte)func
				, (byte)0
				, (byte)0
				, (byte)0
				, (byte)0
				, (byte)(num >> 8)
				, (byte)num
		};
		
		if ( rbit ) {
			head[0] |= (byte)0x80;
			head[6] = (byte)(devid >> 8);
			head[7] = (byte)devid;
		}
		
		if ( wbit ) {
			head[2] |= (byte)0x80;
		}
		
		return send(createSecs1Message(head, secs2)).map(msg -> (SecsMessage)msg);
	}
	
	@Override
	public Optional<SecsMessage> send(SecsMessage primary, int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		byte[] priHead = primary.header10Bytes();
		
		int devid = this.secs1Config().deviceId();
		boolean rbit = this.secs1Config().isEquip();
		
		byte[] head = new byte[] {
				(byte)(devid >> 8)
				, (byte)(devid)
				, (byte)strm
				, (byte)func
				, (byte)0
				, (byte)0
				, priHead[6]
				, priHead[7]
				, priHead[8]
				, priHead[9]
		};
		
		if ( rbit ) {
			head[0] |= (byte)0x80;
		}
		
		if ( wbit ) {
			head[2] |= (byte)0x80;
		}
		
		return send(createSecs1Message(head, secs2)).map(msg -> (SecsMessage)msg);
	}
	
	
	private enum PollCircuitControl {
		
		RX,
		TX,
		RETRY,
		;
	}
	
	private class Loop implements Runnable {
		
		private Secs1MessageBlock presentBlock;
		
		public Loop() {
			presentBlock = null;
		}
		
		public void run() {
			
			try {
				
				for ( ;; ) {
					
					try {
						if ( presentBlock == null ) {
							presentBlock = sendReplyManager.pollBlock();
						}
						
						if ( presentBlock == null ) {
							
							Optional<Byte> op = pollByte(10L, TimeUnit.MILLISECONDS);
							
							if ( op.isPresent() ) {
								
								if ( op.get() == ENQ ) {

									receiveBlock();
								}
							}
							
						} else {
							
							circuitControl();
						}
					}
					catch ( InterruptedException e ) {
						throw e;
					}
					catch ( SecsException e ) {
						notifyLog(new SecsLog(e));
					}
					catch ( Throwable t ) {
						throw t;
					}
				}
			}
			catch ( InterruptedException ignore ) {
			}
			catch ( RejectedExecutionException e ) {
				if ( ! isClosed() ) {
					throw e;
				}
			}
		}
		
		private final byte[] request = new byte[] {ENQ, EOT};
		
		private PollCircuitControl pollCircuitControl() throws InterruptedException {
			
			Collection<Callable<PollCircuitControl>> tasks = Arrays.asList(
					() -> {
						
						try {
							long t = (long)(secs1Config().timeout().t2() * 2000.0F);
							
							for ( ;; ) {
								
								Optional<Byte> op = pollByte(request, t, TimeUnit.MILLISECONDS);
								
								if ( op.isPresent() ) {
									
									byte b = op.get();
									
									if ( ! secs1Config().isMaster() && b == ENQ ) {
										return PollCircuitControl.RX;
									}
									
									if ( b == EOT ) {
										return PollCircuitControl.TX;
									}
								}
							}
						}
						catch ( InterruptedException ignore ) {
						}
						
						return PollCircuitControl.RETRY;
					}
					);
			
			try {
				long t = (long)(secs1Config().timeout().t2() * 1000.0F);
				return executorService().invokeAny(tasks, t, TimeUnit.MILLISECONDS);
			}
			catch ( TimeoutException giveup ) {
			}
			catch ( ExecutionException e ) {
				
				Throwable t = e.getCause();
				
				if ( t instanceof RuntimeException ) {
					throw (RuntimeException)t;
				}
				
				notifyLog(new SecsLog(e));
			}
			
			return PollCircuitControl.RETRY;
		}
		
		private void circuitControl() throws SecsException, InterruptedException {
			
			for ( int counter = 0 ; counter <= secs1Config.retry() ; ) {
				
				sendByte(ENQ);
				
				switch ( pollCircuitControl() ) {
				case RX: {
					
					receiveBlock();
					return;
					/* break; */
				}
				case TX: {
					
					if ( sendBlock() ) {
						
						return;
						
					} else {
						
						counter += 1;
					}
					
					break;
				}
				case RETRY: {
					
					notifyLog(new SecsLog("Secs1Communicator#circuitControl pollByte RETRY"));
					
					counter += 1;
					break;
				}
				}
			}
			
			{
				sendReplyManager.sendFailed(presentBlock, new Secs1RetryOverException());
				
				this.presentBlock = null;
			}

		}
		
		private void receiveBlock() throws SecsException, InterruptedException {
			
			pollByteUntilEmpty();
			
			sendByte(EOT);
			
			int lengthByte = 0;
			byte[] bs;
			
			{
				/*** read Length-Byte ***/
				
				Optional<Byte> op = pollByteT2();
				
				if ( op.isPresent() ) {
					
					byte b = op.get().byteValue();
					
					lengthByte = ((int)b) & 0xFF;
					
					if ( lengthByte < 10 || lengthByte > 254 ) {
						
						receiveBlockGarbage("Receieve Secs1Message LengthByte failed (length=" + lengthByte + ")");
						
						return;
					}
					
					bs = new byte[lengthByte + 3];
					bs[0] = b;
					
				} else {
					
					sendByte(NAK);
					
					notifyLog(new SecsLog("Receive Secs1MessageBlock T2-Timeout (Length-byte)"));
					
					return;
				}
			}
			
			for ( int i = 1, m = bs.length ; i < m ; ++i ) {
				
				Optional<Byte> op = pollByteT1();
				
				if ( op.isPresent() ) {
					
					bs[i] = op.get().byteValue();
					
				} else {
					
					sendByte(NAK);
					
					notifyLog(new SecsLog("Receive Secs1MessageBlock T1-Timeout (pos=" + i + ")"));
					
					return;
				}
			}
			
			Secs1MessageBlock block = new Secs1MessageBlock(bs);
			
			if ( block.sumCheck() ) {
				
				sendByte(ACK);
				
				sendReplyManager.received(block);
				
				if ( ! block.ebit() ) {
					
					Optional<Byte> op = pollByteT4();
					
					if ( op.isPresent() ) {
						
						byte b = op.get();
						
						if ( b == ENQ ) {
							
							receiveBlock();
							
						} else {
							
							notifyLog(new SecsLog("Wait next Block, receive not ENQ (" + String.format("%02X",  b) + ")", block));
						}
						
					} else {
						
						notifyLog(new SecsLog("Wait next Block, T4-timeout", block));
					}
				}
				
			} else {
				
				receiveBlockGarbage("Receieve Secs1Message sum-check failed");
			}
		}
		
		private void receiveBlockGarbage(String reason) throws SecsException, InterruptedException {
			
			for ( ;; ) {
				if ( ! pollByteT1().isPresent() ) {
					break;
				}
			}
			
			sendByte(NAK);
			
			notifyLog(new SecsLog(reason));
		}
		
		private boolean sendBlock() throws SecsException, InterruptedException {
			
			pollByteUntilEmpty();
			
			sendByte(presentBlock.getBytes());
			
			Optional<Byte> op = pollByteT2();
			
			if ( op.isPresent() ) {
				
				byte b = op.get();
				
				if ( b == ACK ) {
					
					sendReplyManager.sended(presentBlock);
					
					this.presentBlock = null;
					
					return true;
					
				} else {
					
					notifyLog(new SecsLog("Secs1Communicator#sendBlock revieve-not-ACK (" + String.format("%02X", b) + ")", presentBlock));
				}
				
			} else {
				
				notifyLog(new SecsLog("Secs1Communicator#sendBlock Timeout-T2", presentBlock));
			}
			
			return false;
		}
	}
	
}
