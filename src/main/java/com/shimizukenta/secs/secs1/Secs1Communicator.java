package com.shimizukenta.secs.secs1;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
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
	
	protected void putReceiveDataMessage(Secs1Message msg) {
		recvDataMsgQueue.offer(msg);
	}
	
	private final Runnable taskRecvMsg = new Runnable() {

		@Override
		public void run() {
			
			try {
				for ( ;; ) {
					notifyReceiveMessage(recvDataMsgQueue.take());
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
	private final BlockingQueue<SecsMessage> trySendMsgPassThroughQueue = new LinkedBlockingQueue<>();
	
	@Override
	protected void notifyTrySendMessagePassThrough(SecsMessage msg) {
		trySendMsgPassThroughQueue.offer(msg);
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
	private final BlockingQueue<SecsMessage> sendedMsgPassThroughQueue = new LinkedBlockingQueue<>();
	
	@Override
	protected void notifySendedMessagePassThrough(SecsMessage msg) {
		sendedMsgPassThroughQueue.offer(msg);
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
	private final BlockingQueue<SecsMessage> recvMsgPassThroughQueue = new LinkedBlockingQueue<>();
	
	@Override
	protected void notifyReceiveMessagePassThrough(SecsMessage msg) {
		recvMsgPassThroughQueue.offer(msg);
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
	
	private Optional<Byte> pollByteTx(byte[] request, float timeout) throws InterruptedException {
		return pollByte(request, (long)(timeout * 1000.0F), TimeUnit.MILLISECONDS);
	}
	
	private Optional<Byte> pollByteT1() throws InterruptedException {
		return pollByteTx(secs1Config.timeout().t1());
	}
	
	private Optional<Byte> pollByteT2() throws InterruptedException {
		return pollByteTx(secs1Config.timeout().t2());
	}
	
	private Optional<Byte> pollByteT2(byte[] request) throws InterruptedException {
		return pollByteTx(request, secs1Config.timeout().t2());
	}
	
	private Optional<Byte> pollByteT4() throws InterruptedException {
		return pollByteTx(secs1Config.timeout().t4());
	}
	
	private void pollByteUntilEmpty() {
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
		
		return send(new Secs1Message(head, secs2)).map(msg -> (SecsMessage)msg);
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
		
		return send(new Secs1Message(head, secs2)).map(msg -> (SecsMessage)msg);
	}
	
	
	private enum PollCircuitControl {
		
		RX,
		TX,
		RETRY,
		;
	}
	
	private class Loop implements Runnable {
		
		private Secs1MessageBlock block;
		
		public Loop() {
			block = null;
		}
		
		public void run() {
			
			try {
				
				for ( ;; ) {
					
					try {
						if ( block == null ) {
							block = sendReplyManager.pollBlock();
						}
						
						if ( block == null ) {
							
							Optional<Byte> op = pollByte(new byte[]{ENQ}, 10L, TimeUnit.MILLISECONDS);
							
							if ( op.isPresent() ) {
								
								if ( op.get() == ENQ ) {

									receiveBlock(new LinkedList<>());
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
		}
		
		private PollCircuitControl pollCircuitControl() throws InterruptedException {
			
			byte[] request = secs1Config.isMaster() ? new byte[]{EOT} : new byte[]{ENQ, EOT};
			
			return pollByteT2(request)
					.map(b -> {
						if ( b == ENQ ) {
							return PollCircuitControl.RX;
						} else if ( b == EOT ) {
							return PollCircuitControl.TX;
						} else {
							return PollCircuitControl.RETRY;
						}
					})
					.orElse(PollCircuitControl.RETRY);
		}
		
		private void circuitControl() throws SecsException, InterruptedException {
			
			for ( int counter = 0 ; counter < secs1Config.retry() ; ) {
				
				sendByte(ENQ);
				
				switch ( pollCircuitControl() ) {
				case RX: {
					
					receiveBlock(new LinkedList<>());
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
				sendReplyManager.sendFailed(block, new Secs1RetryOverException());
				
				this.block = null;
			}

		}
		
		private void receiveBlock(LinkedList<Secs1MessageBlock> blocks) throws SecsException, InterruptedException {
			
			pollByteUntilEmpty();
			
			sendByte(EOT);
			
			
			//TODO
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
			
			sendByte(block.getBytes());
			
			Optional<Byte> op = pollByteT2();
			
			if ( op.isPresent() ) {
				
				byte b = op.get();
				
				if ( b == ACK ) {
					
					sendReplyManager.sended(block);
					
					notifyLog(new SecsLog("Secs1Communicator#sendBlock completed", block));
					
					this.block = null;
					
					return true;
					
				} else {
					
					notifyLog(new SecsLog("Secs1Communicator#sendBlock revieve-not-ACK (" + String.format("%02X", b) + ")", block));
				}
				
			} else {
				
				notifyLog(new SecsLog("Secs1Communicator#sendBlock Timeout-T2", block));
			}
			
			return false;
		}
	}
	
	
	
	
//	private CircuitControlPoll pollCircuitControl() throws InterruptedException {
//		
//		Collection<Callable<CircuitControlPoll>> tasks = Arrays.asList(() -> {
//			
//			try {
//				for ( ;; ) {
//					byte b = recvByteQueue.take();
//					
//					if ( ! secs1Config.isMaster() && b == ENQ ) {
//						
//						return CircuitControlPoll.RX;
//						
//					} else if ( b == EOT ){
//						
//						return CircuitControlPoll.TX;
//					}
//				}
//			}
//			catch ( InterruptedException ignore ) {
//			}
//			
//			return null;
//		});
//		
//		try {
//			long t2 = (long)(secs1Config.timeout().t2() * 1000.0F);
//			return executorService().invokeAny(tasks, t2, TimeUnit.MILLISECONDS);
//		}
//		catch ( ExecutionException none ) {
//			
//			return CircuitControlPoll.RETRY;
//		}
//		catch ( TimeoutException e ) {
//			
//			return CircuitControlPoll.RETRY;
//		}
//	}
	
//	private void circuitControl() throws IOException, InterruptedException {
//		
//		Secs1MessageBlock block = sendBlockQueue.peek();
//		if ( block == null ) {
//			return;
//		}
//		
//		for ( int counter = 0 ; counter < secs1Config.retry() ; ) {
//			
//			this.sendByte(ENQ);
//			
//			switch ( pollCircuitControl() ) {
//			case RX: {
//				
//				receiveBlock();
//				return;
//				/* break; */
//			}
//			case TX: {
//				
//				if ( trySendMessageBlock(block) ) {
//					
//					sendBlockQueue.poll();
//					
//					if ( block.ebit() ) {
//						
//						replyManager.notifySendCompleted(block);
//						
//						Secs1Message sendedMsg = removeSendMessageMap(block);
//						if ( sendedMsg != null ) {
//							notifySendedMessagePassThrough(sendedMsg);
//						}
//					}
//					
//					return;
//					
//				} else {
//					
//					counter += 1;
//				}
//				
//				break;
//			}
//			case RETRY: {
//				
//				entryLog(new Secs1MessageBlockLog("Secs1Communicator#circuitControl-poll failed", block));
//				counter += 1;
//				
//				break;
//			}
//			}
//		}
//		
//		/* Send-Retry-Over */
//		{
//			replyManager.notifySendFailed(block);
//			
//			sendBlockQueue.removeIf(blk -> blk.equalsSystemBytesKey(block));
//			
//			entryLog(new Secs1MessageBlockLog("Secs1Communicator#try-send retry-over", block));
//			
//			removeSendMessageMap(block);
//		}
//	}
	
	private void receiveBlock() throws IOException, InterruptedException {
		
		recvByteQueue.clear();
		
		sendByte(EOT);
		
		int lengthByte = 0;
		byte[] bs;
		
		{
			/*** read Length-Byte ***/
			
			Optional<Byte> op = pollByteT2();
			
			if ( op.isPresent() ) {
				
				byte b = op.get();
				
				lengthByte = ((int)b) & 0xFF;
				
				if ( lengthByte < 10 || lengthByte > 254 ) {
					
					receiveBlockGarbage("Receieve Secs1Message LengthByte failed (length=" + lengthByte + ")");
					
					return;
				}
				
				bs = new byte[lengthByte + 3];
				bs[0] = b;
				
			} else {
				
				sendByte(NAK);
				
				entryLog(new SecsLog("Receive Secs1MessageBlock T2-Timeout (Length-byte)"));
				
				return;
			}
		}

		{
			/*** Reading ***/
			
			int sum = 0;
	
			for ( int i = 1, m = lengthByte + 1 ; i < m ; ++i ) {
				
				Optional<Byte> op = pollByteT1();
				
				if ( op.isPresent() ) {
					
					byte b = op.get();
					bs[i] = b;
					sum += ((int)b) & 0xFF;
					
				} else {
					
					sendByte(NAK);
					
					entryLog(new SecsLog("Receive Secs1MessageBlock T1-Timeout (pos=" + i + ")"));
					
					return;
				}
			}
			
			{
				int pos = lengthByte + 1;
				Optional<Byte> op = pollByteT1();
				
				if ( op.isPresent() ) {
					
					byte b = op.get();
					bs[pos] = b;
					sum -= (((int)b) << 8) & 0xFF00;
					
				} else {
					
					sendByte(NAK);
					
					entryLog(new SecsLog("Receive Secs1MessageBlock T1-Timeout (pos=" + pos + ")"));
					
					return;
				}
			}
			
			{
				int pos = lengthByte + 2;
				Optional<Byte> op = pollByteT1();
				
				if ( op.isPresent() ) {
					
					byte b = op.get();
					bs[pos] = b;
					sum -= ((int)b) & 0xFF;
					
				} else {
					
					sendByte(NAK);
					
					entryLog(new SecsLog("Receive Secs1MessageBlock T1-Timeout (pos=" + pos + ")"));
					
					return;
				}
			}
			
			/*** SUM-check ***/
			if ( sum == 0 /* O.K. */) {
				
				sendByte(ACK);
				
				Secs1MessageBlock block = new Secs1MessageBlock(bs);
				
				this.replyManager.resetTimer(block);
				
				blockManager.putBlock(block);
				
				if ( ! block.ebit() ) {
					
					Optional<Byte> op = pollByteT4();
					
					if ( op.isPresent() ) {
						
						byte b = op.get();
						
						if ( b == ENQ ) {
							
							receiveBlock();
							
						} else {
							
							entryLog(new Secs1MessageBlockLog("Wait next block, receive not ENQ (" + String.format("%02X",  b) + ")", block));
						}
						
					} else {
						
						entryLog(new Secs1MessageBlockLog("Wait next block, T4-timeout", block));
					}
				}
				
			} else {
				
				receiveBlockGarbage("Receieve Secs1Message sum-check failed (sum=" + sum + ")");
			}

		}
		
	}
	
//	private void receiveBlockGarbage(CharSequence reason) throws IOException, InterruptedException {
//		
//		for ( ;; ) {
//			if ( ! pollByteT1().isPresent() ) {
//				break;
//			}
//		}
//		
//		sendByte(NAK);
//		
//		entryLog(new SecsLog(reason));
//	}
//	
//	private boolean trySendMessageBlock(Secs1MessageBlock block) throws IOException, InterruptedException {
//		
//		recvByteQueue.clear();
//		
//		this.sendByte(block.bytes());
//		
//		Optional<Byte> op = pollByteT2();
//		
//		if ( op.isPresent() ) {
//			
//			byte b = op.get();
//			
//			if ( b == ACK ) {
//				
//				entryLog(new Secs1MessageBlockLog("Send Secs1MessageBlock completed", block));
//				
//				return true;
//				
//			} else {
//				
//				entryLog(new Secs1MessageBlockLog("Send Secs1MessageBlock Not-ACK (" + String.format("%02X", b) + ")", block));
//				
//				return false;
//			}
//			
//		} else {
//			
//			entryLog(new Secs1MessageBlockLog("Send Secs1MessageBlock T2-Timeout(ACK)", block));
//			
//			return false;
//		}
//	}
	
	
	
}
