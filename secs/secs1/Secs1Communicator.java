package secs.secs1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import secs.SecsCommunicator;
import secs.SecsException;
import secs.SecsLog;
import secs.SecsMessage;
import secs.SecsSendMessageException;
import secs.SecsWaitReplyMessageException;
import secs.secs2.Secs2;
import secs.secs2.Secs2Exception;

public abstract class Secs1Communicator extends SecsCommunicator {
	
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
		this.blockManager = new Secs1MessageBlockManager(config);
		this.replyManager = new Secs1MessageSendReplyManager(execServ, config.timeout(), msg -> {
			
			try {
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
	public Optional<? extends Secs1Message> send(int strm, int func, boolean wbit, Secs2 secs2)
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
		
		return send(new Secs1Message(head, secs2));
	}
	
	@Override
	public Optional<? extends Secs1Message> send(SecsMessage primary, int strm, int func, boolean wbit, Secs2 secs2)
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
		
		return send(new Secs1Message(head, secs2));
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
				loop();
			}
			catch ( InterruptedException ignore ) {
			}
		});
	}
	
	@Override
	public void close() throws IOException {
		
		List<IOException> ioExcepts = new ArrayList<>();
		
		try {
			super.close();
		}
		catch ( IOException e ) {
			ioExcepts.add(e);
		}
		
		try {
			execServ.shutdown();
			if ( ! execServ.awaitTermination(10L, TimeUnit.MILLISECONDS) ) {
				execServ.shutdownNow();
				if ( ! execServ.awaitTermination(5L, TimeUnit.SECONDS) ) {
					ioExcepts.add(new IOException("ExecutorService#shutdown failed"));
				}
			}
		}
		catch ( InterruptedException giveup ) {
		}
		
		if ( ! ioExcepts.isEmpty() ) {
			throw ioExcepts.get(0);
		}
	}
	
	private final BlockingQueue<Secs1MessageBlock> sendBlockQueue = new LinkedBlockingQueue<>();
	
	private void loop() throws InterruptedException {
		
		for ( ;; ) {
			
			try {
				
				if ( sendBlockQueue.isEmpty() ) {
					
					Optional<Byte> op = this.pollByte();
					
					if ( op.isPresent() ) {
						
						byte b = op.get();
						
						if ( b == ENQ ) {
							receiveBlock();
						}
						
					} else {
						
						synchronized ( this ) {
							this.wait();
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
	}
	
	private void circuitControl() throws IOException, InterruptedException {
		
		Secs1MessageBlock block = sendBlockQueue.peek();
		if ( block == null ) {
			return;
		}
		
		for ( int counter = 0 ; counter < secs1Config.retry() ; ) {
			
			this.sendByte(ENQ);
			
			Optional<Byte> op = pollByteT2();
			
			if ( op.isPresent() ) {
				
				byte b = op.get();
				
				if ( b == ENQ ) {
					
					if ( ! secs1Config.isMaster() ) {
						receiveBlock();
						return;
					}
					
				} else if ( b == EOT ) {
					
					if ( trySendMessageBlock(block) ) {
						
						sendBlockQueue.poll();
						
						if ( block.ebit() ) {
							replyManager.notifySendCompleted(block);
						}
						
						
						//TODO
						//putLog
						
						
						return;
						
					} else {
						
						counter += 1;
					}
					
				} else {
					
					counter += 1;
				}
				
			} else {
				
				counter += 1;
			}
		}
		
		/* Send-Retry-Over */
		sendBlockQueue.poll();
		
		
		
	}
	
	private void receiveBlock() throws IOException, InterruptedException {
		
		sendByte(EOT);
		
		
		//TODO
		
	}
	
	private boolean trySendMessageBlock(Secs1MessageBlock block) throws IOException, InterruptedException {
		
		//TODO
		
		return false;
	}
	
}

