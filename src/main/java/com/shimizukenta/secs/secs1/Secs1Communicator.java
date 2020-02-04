package com.shimizukenta.secs.secs1;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import com.shimizukenta.secs.AbstractSecsCommunicator;
import com.shimizukenta.secs.AbstractSecsMessage;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsLog;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.secs2.AbstractSecs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

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
	
	private final Secs1MessageBlockManager blockManager;
	private final Secs1MessageSendReplyManager replyManager;
	
	private final Secs1CommunicatorConfig secs1Config;
	
	
	public Secs1Communicator(Secs1CommunicatorConfig config) {
		super(config);
		
		this.secs1Config = config;
		this.blockManager = new Secs1MessageBlockManager(config, execServ);
		this.replyManager = new Secs1MessageSendReplyManager(execServ, config.timeout(), msg -> {
			
			try {
				entryLog(new SecsLog("Try Send Secs1Message", msg));
				
				notifyTrySendMessagePassThrough(msg);
				
				entrySendMessageMap(msg);
				
				List<Secs1MessageBlock> blocks = msg.blocks();
				sendBlockQueue.addAll(blocks);
			}
			catch ( Secs2Exception e ) {
				throw new Secs1SendMessageException(msg, e);
			}
		});
	}
	
	protected Secs1CommunicatorConfig secs1Config() {
		return secs1Config;
	}
	
	protected ExecutorService executorService() {
		return execServ;
	}
	
	
	abstract protected void sendByte(byte[] bs) throws IOException, InterruptedException;
	
	private final BlockingQueue<Byte> recvByteQueue = new LinkedBlockingQueue<>();
	
	protected boolean putRecvByte(Collection<Byte> bs) {
		
		synchronized ( this ) {
			
			try {
				return recvByteQueue.addAll(bs);
			}
			finally {
				this.notifyAll();
			}
		}
	}
	
	protected boolean putRecvByte(byte b) {
		
		synchronized ( this ) {
			
			try {
				return recvByteQueue.offer(b);
			}
			finally {
				this.notifyAll();
			}
		}
	}
	
	protected boolean putRecvByte(byte... bs) {
		
		synchronized ( this ) {
			
			try {
				
				for ( byte b : bs ) {
					
					if ( ! recvByteQueue.offer(b) ) {
						
						return false;
					}
				}
				
				return true;
			}
			finally {
				this.notifyAll();
			}
		}
	}
	
	protected Optional<Byte> pollByte() {
		Byte b = recvByteQueue.poll();
		return b == null ? Optional.empty() : Optional.of(b);
	}
	
	protected Optional<Byte> pollByte(long timeout, TimeUnit unit) throws InterruptedException {
		Byte b = recvByteQueue.poll(timeout, unit);
		return b == null ? Optional.empty() : Optional.of(b);
	}
	
	protected void sendByte(byte b) throws IOException, InterruptedException {
		sendByte(new byte[] {b});
	}
	
	private Optional<Byte> pollByteTx(float v) throws InterruptedException {
		return pollByte((long)(v * 1000.0F), TimeUnit.MILLISECONDS);
	}
	
	protected Optional<Byte> pollByteT1() throws InterruptedException {
		return pollByteTx(secs1Config.timeout().t1());
	}
	
	protected Optional<Byte> pollByteT2() throws InterruptedException {
		return pollByteTx(secs1Config.timeout().t2());
	}
	
	protected Optional<Byte> pollByteT4() throws InterruptedException {
		return pollByteTx(secs1Config.timeout().t4());
	}
	
	
	public Optional<Secs1Message> send(Secs1Message s1m)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		try {
			return replyManager.send(s1m);
		}
		catch (SecsException e) {
			entryLog(new SecsLog(e));
			throw e;
		}
	}
	
	private final AtomicInteger autoNumber = new AtomicInteger();
	
	@Override
	public Optional<SecsMessage> send(int strm, int func, boolean wbit, AbstractSecs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		int devid = this.secs1Config().deviceId();
		boolean rbit = this.secs1Config().isEquip();
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
		
		return send(new Secs1Message(head, secs2)).map(msg -> (AbstractSecsMessage)msg);
	}
	
	@Override
	public Optional<SecsMessage> send(SecsMessage primary, int strm, int func, boolean wbit, AbstractSecs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		if ( ! ( primary instanceof Secs1Message) ) {
			throw new ClassCastException("primary is not \"secs.secs1.Secs1Message\"");
		}
		
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
		
		return send(new Secs1Message(head, secs2)).map(msg -> (AbstractSecsMessage)msg);
	}
	
	
	/* Secs-Log-Queue */
	private final BlockingQueue<SecsLog> logQueue = new LinkedBlockingQueue<>();
	
	protected void entryLog(SecsLog log) {
		logQueue.offer(log);
	}
	
	private final BlockingQueue<Secs1Message> recvMsgQueue = new LinkedBlockingQueue<>();
	
	@Override
	public void open() throws IOException {
		
		super.open();
		
		blockManager.addSecs1MessageReceiveListener(recvMsgQueue::offer);
		blockManager.addSecs1MessageReceiveListener(this::notifyReceiveMessagePassThrough);
		
		blockManager.addSecsLogListener(this::entryLog);
		replyManager.addSecsLogListener(this::entryLog);
		
		execServ.execute(() -> {
			try {
				for ( ;; ) {
					replyManager.receive(recvMsgQueue.take()).ifPresent(this::notifyReceiveMessage);
				}
			}
			catch ( InterruptedException ignore ) {
			}
		});
		
		execServ.execute(() -> {
			try {
				for ( ;; ) {
					notifyLog(logQueue.take());
				}
			}
			catch ( InterruptedException ignore ) {
			}
		});
		
		execServ.execute(() -> {
			try {
				for ( ;; ) {
					loop();
				}
			}
			catch ( InterruptedException ignore ) {
			}
		});
	}
	
	@Override
	public void close() throws IOException {
		
		IOException ioExcept = null;
		
		try {
			synchronized ( this ) {
				if ( closed ) {
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
	
	private final BlockingQueue<Secs1MessageBlock> sendBlockQueue = new LinkedBlockingQueue<>();
	
	private void loop() throws InterruptedException {
		
		try {
			
			if ( sendBlockQueue.isEmpty() ) {
				
				Optional<Byte> op = this.pollByte(10L, TimeUnit.MILLISECONDS);
				
				if ( op.isPresent() ) {
					
					byte b = op.get();
					
					if ( b == ENQ ) {
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
		catch ( IOException e ) {
			
			sendBlockQueue.clear();
			entryLog(new SecsLog(e));
		}
		catch ( Throwable t ) {
			entryLog(new SecsLog(t));
			throw t;
		}
	}
	
	
	private enum CircuitControlPoll {
		
		RX,
		TX,
		RETRY,
		;
	}
	
	private CircuitControlPoll pollCircuitControl() throws InterruptedException {
		
		Collection<Callable<CircuitControlPoll>> tasks = Arrays.asList(() -> {
			
			try {
				for ( ;; ) {
					byte b = recvByteQueue.take();
					
					if ( ! secs1Config.isMaster() && b == ENQ ) {
						
						return CircuitControlPoll.RX;
						
					} else if ( b == EOT ){
						
						return CircuitControlPoll.TX;
					}
				}
			}
			catch ( InterruptedException ignore ) {
			}
			
			return null;
		});
		
		try {
			long t2 = (long)(secs1Config.timeout().t2() * 1000.0F);
			return executorService().invokeAny(tasks, t2, TimeUnit.MILLISECONDS);
		}
		catch ( ExecutionException none ) {
			
			return CircuitControlPoll.RETRY;
		}
		catch ( TimeoutException e ) {
			
			return CircuitControlPoll.RETRY;
		}
	}
	
	private void circuitControl() throws IOException, InterruptedException {
		
		Secs1MessageBlock block = sendBlockQueue.peek();
		if ( block == null ) {
			return;
		}
		
		for ( int counter = 0 ; counter < secs1Config.retry() ; ) {
			
			this.sendByte(ENQ);
			
			switch ( pollCircuitControl() ) {
			case RX: {
				
				receiveBlock();
				return;
				/* break; */
			}
			case TX: {
				
				if ( trySendMessageBlock(block) ) {
					
					sendBlockQueue.poll();
					
					if ( block.ebit() ) {
						
						replyManager.notifySendCompleted(block);
						
						Secs1Message sendedMsg = removeSendMessageMap(block);
						if ( sendedMsg != null ) {
							notifySendedMessagePassThrough(sendedMsg);
						}
					}
					
					return;
					
				} else {
					
					counter += 1;
				}
				
				break;
			}
			case RETRY: {
				
				entryLog(new Secs1MessageBlockLog("Secs1Communicator#circuitControl-poll failed", block));
				counter += 1;
				
				break;
			}
			}
		}
		
		/* Send-Retry-Over */
		{
			replyManager.notifySendFailed(block);
			
			sendBlockQueue.removeIf(blk -> blk.equalsSystemBytesKey(block));
			
			entryLog(new Secs1MessageBlockLog("Secs1Communicator#try-send retry-over", block));
			
			removeSendMessageMap(block);
		}
	}
	
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
	
	private void receiveBlockGarbage(CharSequence reason) throws IOException, InterruptedException {
		
		for ( ;; ) {
			if ( ! pollByteT1().isPresent() ) {
				break;
			}
		}
		
		sendByte(NAK);
		
		entryLog(new SecsLog(reason));
	}
	
	private boolean trySendMessageBlock(Secs1MessageBlock block) throws IOException, InterruptedException {
		
		recvByteQueue.clear();
		
		this.sendByte(block.bytes());
		
		Optional<Byte> op = pollByteT2();
		
		if ( op.isPresent() ) {
			
			byte b = op.get();
			
			if ( b == ACK ) {
				
				entryLog(new Secs1MessageBlockLog("Send Secs1MessageBlock completed", block));
				
				return true;
				
			} else {
				
				entryLog(new Secs1MessageBlockLog("Send Secs1MessageBlock Not-ACK (" + String.format("%02X", b) + ")", block));
				
				return false;
			}
			
		} else {
			
			entryLog(new Secs1MessageBlockLog("Send Secs1MessageBlock T2-Timeout(ACK)", block));
			
			return false;
		}
	}
	
	
	private final Collection<Secs1Message> sendMsgs = new HashSet<>();
	
	protected void entrySendMessageMap(Secs1Message msg) {
		
		synchronized ( sendMsgs ) {
			
			final Integer i = msg.systemBytesKey();
			
			sendMsgs.removeIf(m -> {
				return m.systemBytesKey().equals(i);
			});
			
			sendMsgs.add(msg);
		}
	}
	
	protected Secs1Message removeSendMessageMap(Integer key) {
		
		synchronized ( sendMsgs ) {
			
			Secs1Message rtn = sendMsgs.stream()
					.filter(m -> {
						return m.systemBytesKey().equals(key);
					})
					.findAny()
					.orElse(null);
			
			sendMsgs.removeIf(m -> {
				return m.systemBytesKey().equals(key);
			});
			
			return rtn;
		}
	}
	
	protected Secs1Message removeSendMessageMap(Secs1MessageBlock block) {
		return removeSendMessageMap(block.systemBytesKey());
	}
	
}
