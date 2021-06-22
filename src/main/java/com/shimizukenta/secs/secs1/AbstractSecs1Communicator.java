package com.shimizukenta.secs.secs1;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import com.shimizukenta.secs.AbstractSecsCommunicator;
import com.shimizukenta.secs.AbstractSecsWaitReplyMessageExceptionLog;
import com.shimizukenta.secs.ByteArrayProperty;
import com.shimizukenta.secs.InterruptableRunnable;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

/**
 * This abstract class is implementation of SECS-I (SEMI-E4).
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractSecs1Communicator extends AbstractSecsCommunicator implements Secs1Communicator {
	
	protected static final byte ENQ = (byte)0x05;
	protected static final byte EOT = (byte)0x04;
	protected static final byte ACK = (byte)0x06;
	protected static final byte NAK = (byte)0x15;
	
	protected static final boolean isENQ(Byte b) {
		return b != null && b.byteValue() == ENQ;
	}
	
	protected static final boolean isEOT(Byte b) {
		return b != null && b.byteValue() == EOT;
	}
	
	protected static final boolean isACK(Byte b) {
		return b != null && b.byteValue() == ACK;
	}
	
	private final Secs1CommunicatorConfig secs1Config;
	private final ByteArrayProperty deviceIdBytes = ByteArrayProperty.newInstance(new byte[] {0, 0});
	
	private final ByteAndSecs1MessageQueue circuitQueue = new ByteAndSecs1MessageQueue();
	private final Secs1SendMessageManager sendMgr = new Secs1SendMessageManager();
	private final Secs1ReplyMessageManager replyMgr = new Secs1ReplyMessageManager();
	
	public AbstractSecs1Communicator(Secs1CommunicatorConfig config) {
		super(config);
		
		this.secs1Config = config;
		
		this.secs1Config.deviceId().addChangeListener(n -> {
			int v = n.intValue();
			byte[] bs = new byte[] {
					(byte)(v >> 8),
					(byte)v
			};
			deviceIdBytes.set(bs);
		});
	}
	
	protected Secs1CommunicatorConfig secs1Config() {
		return secs1Config;
	}
	
	@Override
	public void open() throws IOException {
		super.open();
		
		executeLoopTask(() -> {
			entryCircuit();
		});
	}
	
	@Override
	public void close() throws IOException {
		
		synchronized ( this ) {
			if ( isClosed() ) {
				return;
			}
			
			super.close();
		}
	}
	
	protected void putByte(byte b) throws InterruptedException {
		this.circuitQueue.putByte(b);
	}
	
	protected void putBytes(byte[] bs) throws InterruptedException {
		this.circuitQueue.putBytes(bs);
	}
	
	
	abstract protected void sendByte(byte[] bs) throws SecsSendMessageException, SecsException, InterruptedException;
	
	private void sendByte(byte b) throws SecsSendMessageException, SecsException, InterruptedException {
		sendByte(new byte[] {b});
	}
	
	@Override
	public Optional<Secs1Message> send(Secs1Message msg)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		try {
			
			this.sendMgr.entry(msg);
			
			if ( msg.wbit() ) {
				
				try {
					this.replyMgr.entry(msg);
					
					this.circuitQueue.putSecs1Message(msg);
					
					this.sendMgr.waitUntilSended(msg);
					
					Optional<Secs1Message> r = this.replyMgr.reply(msg, this.secs1Config().timeout().t3());
					
					if ( ! r.isPresent() ) {
						throw new Secs1TimeoutT3Exception(msg);
					}
					
					return r;
				}
				finally {
					
					this.replyMgr.exit(msg);
				}
				
			} else {
				
				this.circuitQueue.putSecs1Message(msg);
				
				this.sendMgr.waitUntilSended(msg);
				
				return Optional.empty();
			}
		}
		catch ( SecsWaitReplyMessageException e ) {
			
			notifyLog(new AbstractSecsWaitReplyMessageExceptionLog(e) {
				
				private static final long serialVersionUID = 6987300659468550084L;
			});
			
			throw e;
		}
		catch ( SecsException e ) {
			notifyLog(e);
			throw e;
		}
		finally {
			this.sendMgr.exit(msg);
		}
	}
	
	@Override
	public Secs1Message createSecs1Message(byte[] header) {
		return createSecs1Message(header, Secs2.empty());
	}
	
	@Override
	public Secs1Message createSecs1Message(byte[] header, Secs2 body) {
		return new Secs1Message(header, body);
	}
	
	private final AtomicInteger autoNumber = new AtomicInteger();
	
	@Override
	public Optional<SecsMessage> send(int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		byte[] devids = deviceIdBytes.get();
		boolean rbit = secs1Config().isEquip().booleanValue();
		int num = autoNumber.incrementAndGet();
		
		byte[] head = new byte[] {
				devids[0],
				devids[1],
				(byte)strm,
				(byte)func,
				(byte)0,
				(byte)0,
				(byte)0,
				(byte)0,
				(byte)(num >> 8),
				(byte)num
		};
		
		if ( rbit ) {
			head[0] |= (byte)0x80;
			head[6] = devids[0];
			head[7] = devids[1];
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
		
		byte[] devids = deviceIdBytes.get();
		boolean rbit = this.secs1Config().isEquip().booleanValue();
		
		byte[] head = new byte[] {
				devids[0],
				devids[1],
				(byte)strm,
				(byte)func,
				(byte)0,
				(byte)0,
				priHead[6],
				priHead[7],
				priHead[8],
				priHead[9]
		};
		
		if ( rbit ) {
			head[0] |= (byte)0x80;
		}
		
		if ( wbit ) {
			head[2] |= (byte)0x80;
		}
		
		return send(createSecs1Message(head, secs2)).map(msg -> (SecsMessage)msg);
	}
	
	private void entryCircuit() throws InterruptedException {
		
		ByteOrSecs1Message v = this.circuitQueue.takeByteOrSecs1Message();
		
		final Secs1MessageBlockPack pack = v.message();
		
		if ( pack == null ) {
			
			if ( v.isENQ() ) {
				
				try {
					this.receiveCircuit();
				}
				catch ( SecsException e ) {
					this.notifyLog(e);
				}
			}
			
		} else {
			
			try {
				
				for (int retry = 0; retry <= this.secs1Config().retry().intValue(); ) {
					
					this.sendByte(ENQ);
					
					Byte b = this.circuitQueue.pollByte(this.secs1Config().timeout().t2());
					
					if ( isEOT(b) ) {
						
						//TODO
						//to-send
					
					} else if ( isENQ(b) ) {
						
						if ( this.secs1Config().isMaster().booleanValue() ) {
							
							Byte bb = this.circuitQueue.pollByte(this.secs1Config().timeout().t2());
							
							if ( isEOT(bb) ) {
								
								//TOOD
								//to-send
								
							} else {
								
								retry += 1;
								this.notifyLog(Secs1RetryCircuitControlLog.newInstance(retry));
							}
							
						} else {
							
							try {
								this.receiveCircuit();
							}
							catch ( SecsException e ) {
								this.notifyLog(e);
							}
							
							retry = 0;
							pack.reset();
						}
						
					} else {
						
						retry += 1;
						this.notifyLog(Secs1RetryCircuitControlLog.newInstance(retry));
					}
				}
				
				//TODO
				//retry-over
				//sendMgr.putException
				
			}
			catch ( SecsException e ) {
				this.notifyLog(e);
				this.sendMgr.putException(pack.message(), e);
			}
		}
	}
	
	private final LinkedList<Secs1MessageBlock> cacheBlocks = new LinkedList<>();
	
	private void receiveCircuit() throws SecsException, InterruptedException {
		
		this.sendByte(EOT);
		
		byte[] bs = new byte[257];
		
		{
			int r = this.circuitQueue.pollBytes(bs, 0, 1, this.secs1Config().timeout().t2());
			
			if ( r <= 0 ) {
				this.sendByte(NAK);
				this.notifyLog(Secs1TimeoutT2LengthByteCircuitColtrolLog.newInstance());
				return;
			}
		}
		
		{
			int len = (int)(bs[0]) & 0x000000FF;
			
			if ( len < 10 || len > 254 ) {
				this.circuitQueue.garbageBytes(this.secs1Config().timeout().t1());
				this.sendByte(NAK);
				this.notifyLog(Secs1IllegalLengthByteCircuitControlLog.newInstance(len));
				return;
			}
			
			for (int pos = 1, m = (len + 3); pos < m;) {
				
				int r = this.circuitQueue.pollBytes(bs, pos, m, this.secs1Config().timeout().t1());
				
				if ( r <= 0 ) {
					this.sendByte(NAK);
					this.notifyLog(Secs1TimeoutT1CircuitControlLog.newInstance(pos));
					return;
				}
				
				pos += r;
			}
		}
		
		Secs1MessageBlock block = new Secs1MessageBlock(bs);
		
		if ( block.sumCheck() ) {
			
			this.sendByte(ACK);
			
		} else {
			
			this.circuitQueue.garbageBytes(this.secs1Config().timeout().t1());
			this.sendByte(NAK);
			this.notifyLog(Secs1SumCheckMismatchCirsuitControlLog.newInstance());
			return;
		}
		
		this.notifyLog(new Secs1ReceiveMessageBlockLog(block));
		
		if (block.deviceId() != this.secs1Config().deviceId().intValue()) {
			return;
		}
		
		if ( this.cacheBlocks.isEmpty() ) {
			
			this.cacheBlocks.add(block);
			
		} else {
			
			Secs1MessageBlock prev = this.cacheBlocks.getLast();
			
			if ( prev.sameSystemBytes(block) ) {
				
				if ( prev.isNextBlock(block) ) {
					this.cacheBlocks.add(block);
				}
				
			} else {
				
				this.cacheBlocks.clear();
				this.cacheBlocks.add(block);
			}
		}
		
		if ( block.ebit() ) {
			
			try {
				Secs1Message s1msg = Secs1MessageBlockConverter.toSecs1Message(this.cacheBlocks);
				
				this.notifyReceiveMessagePassThrough(s1msg);
				this.notifyLog(new Secs1ReceiveMessageLog(s1msg));
				
				this.replyMgr.put(s1msg);
			}
			catch ( Secs2Exception e ) {
				this.notifyLog(e);
			}
			finally {
				this.cacheBlocks.clear();
			}
			
		} else {
			
			this.replyMgr.resetTimer(block);
			
			Byte b = this.circuitQueue.pollByte(this.secs1Config().timeout().t4());
			
			if ( isENQ(b) ) {
				
				this.receiveCircuit();
				
			} else if ( b == null ) {
				
				this.notifyLog(Secs1TimeoutT4CircuitControlLog.newInstance(block));
				
			} else {
				
				this.notifyLog(Secs1NotReceiveNextBlockEnqCircuitControlLog.newInstance(block, b));
			}
		}
	}
	
	
	
	
	private class CircuitLoop implements InterruptableRunnable {
		
		/**
		 * Watch-dog-timer
		 */
		private final long wdt = 3000L;
		
		private Secs1MessageBlock presentBlock;
		
		public CircuitLoop() {
			presentBlock = null;
		}
		
		@Override
		public void run() throws InterruptedException {
			
			NextCircuitControlBehavior next = NextCircuitControlBehavior.ToNextLoop;
			
			synchronized ( this ) {
				
				if ( presentBlock == null ) {
					presentBlock = sendReplyManager.pollBlock();
				}
				
				if ( presentBlock == null ) {
					
					Byte b = pollByte().orElse(null);
					
					if ( b == null ) {
						
						long t = secs1Config().timeout().t2().getMilliSeconds();
						
						if ( t > wdt ) {
							this.wait(wdt);
						} else {
							this.wait(t / 2L);
						}
						
					} else {
						
						if ( b.byteValue() == ENQ ) {
							next = NextCircuitControlBehavior.ToReceiveBlock;
						}
					}
					
				} else {
					
					next = NextCircuitControlBehavior.ToCircuitControl;
				}
			}
			
			try {
				switch ( next ) {
				case ToReceiveBlock: {
					receiveBlock();
					break;
				}
				case ToCircuitControl: {
					circuitControl();
					break;
				}
				case ToNextLoop:
				default: {
					/* Nothing */
				}
				}
			}
			catch ( SecsException e ) {
				notifyLog(e);
			}
		}
		
		private final byte[] request = new byte[] {ENQ, EOT};
		
		private PollCircuitControl pollCircuitControl() throws InterruptedException {
			
			Callable<PollCircuitControl> task = () -> {
				
				try {
					for ( ;; ) {
						Optional<Byte> op = pollByte(request);
						
						if ( op.isPresent() ) {
							
							byte b = op.get().byteValue();
							
							if ( ! secs1Config().isMaster().booleanValue() && b == ENQ ) {
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
			};
			
			try {
				return executeInvokeAny(
						Arrays.asList(task),
						secs1Config().timeout().t2()
						);
			}
			catch ( TimeoutException giveup ) {
			}
			catch ( ExecutionException e ) {
				
				Throwable t = e.getCause();
				
				if ( t instanceof RuntimeException ) {
					throw (RuntimeException)t;
				}
				
				notifyLog(t);
			}
			
			return PollCircuitControl.RETRY;
		}
		
		private void circuitControl() throws InterruptedException {
			
			try {
				
				for ( int counter = 0, m = secs1Config.retry().intValue(); counter <= m; ) {
					
					sendByte(ENQ);
					
					PollCircuitControl p = pollCircuitControl();
					
					switch ( p ) {
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
							notifyLog(Secs1RetryCircuitControlLog.newInstance(counter));
						}
						
						break;
					}
					case RETRY: {
						
						counter += 1;
						notifyLog(Secs1RetryCircuitControlLog.newInstance(counter));
						
						break;
					}
					}
				}
				
				{
					sendReplyManager.sendFailed(presentBlock, new Secs1RetryOverException());
					
					this.presentBlock = null;
				}
			}
			catch ( SecsException e ) {
				
				sendReplyManager.sendFailed(presentBlock, e);
				
				this.presentBlock = null;
			}

		}
		
		
		private boolean sendBlock() throws SecsException, InterruptedException {
			
			notifyLog(new Secs1TrySendMessageBlockLog(presentBlock));
			
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
					
					notifyLog(Secs1NotReceiveAckCircuitControlLog.newInstance(presentBlock, b));
				}
				
			} else {
				
				notifyLog(Secs1TimeoutT2AckCircuitControlLog.newInstance(presentBlock));
			}
			
			return false;
		}
	}
	
}
