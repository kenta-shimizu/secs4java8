package com.shimizukenta.secs.secs1;

import java.util.LinkedList;
import java.util.Optional;

import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.secs2.Secs2Exception;

public abstract class AbstractSecs1Circuit implements Runnable {
	
	protected static final byte ENQ = (byte)0x05;
	protected static final byte EOT = (byte)0x04;
	protected static final byte ACK = (byte)0x06;
	protected static final byte NAK = (byte)0x15;
	
	private final ByteAndSecs1MessageQueue queue = new ByteAndSecs1MessageQueue();
	private final Secs1SendMessageManager sendMgr = new Secs1SendMessageManager();
	private final Secs1TransactionManager<AbstractSecs1Message, AbstractSecs1MessageBlock> transMgr = new Secs1TransactionManager<>();
	
	private final AbstractSecs1Communicator comm;
	
	public AbstractSecs1Circuit(AbstractSecs1Communicator communicator) {
		this.comm = communicator;
	}

	@Override
	public void run() {
		try {
			for ( ;; ) {
				this.enter();
			}
		}
		catch ( InterruptedException ignore ) {
		}
	}
	
	public void putByte(byte b) throws InterruptedException {
		this.queue.putByte(b);
	}
	
	public void putBytes(byte[] bs) throws InterruptedException {
		this.queue.putBytes(bs);
	}
	
	private void sendBytes(byte[] bs) throws Secs1SendByteException, InterruptedException {
		this.comm.sendBytes(bs);
	}
	
	private void sendByte(byte b) throws Secs1SendByteException, InterruptedException {
		this.sendBytes(new byte[] {b});
	}
	
	public Optional<Secs1Message> send(AbstractSecs1Message msg)
			throws Secs1SendMessageException, Secs1WaitReplyMessageException, Secs1Exception
			, InterruptedException {
		
		try {
			
			this.sendMgr.enter(msg);
			
			this.comm.notifyTrySendMessagePassThrough(msg);
			this.comm.notifyTrySendSecs1MessagePassThrough(msg);
			this.comm.notifyLog(new Secs1TrySendMessageLog(msg));
			
			if ( msg.wbit() ) {
				
				try {
					this.transMgr.enter(msg);
					
					this.queue.putSecs1Message(msg);
					
					this.sendMgr.waitUntilSended(msg);
					
					Secs1Message r = this.transMgr.waitReply(
							msg,
							this.comm.config().timeout().t3());
					
					if ( r == null ) {
						
						throw new Secs1TimeoutT3Exception(msg);
						
					} else {
						
						return Optional.of(r);
					}
				}
				finally {
					
					this.transMgr.exit(msg);
				}
				
			} else {
				
				this.queue.putSecs1Message(msg);
				
				this.sendMgr.waitUntilSended(msg);
				
				return Optional.empty();
			}
		}
		catch ( Secs1SendMessageException e ) {
			this.comm.notifyLog(e);
			throw e;
		}
		catch ( Secs1WaitReplyMessageException e ) {
			this.comm.notifyLog(e);
			throw e;
		}
		finally {
			this.sendMgr.exit(msg);
		}
	}
	
	private void enter() throws InterruptedException {
		
		ByteOrSecs1Message v = this.queue.takeByteOrSecs1Message();
		
		final Secs1MessageBlockPack pack = v.message();
		
		if ( pack == null ) {
			
			if ( v.isENQ() ) {
				
				try {
					this.receiving();
				}
				catch ( Secs1Exception e ) {
					this.comm.notifyLog(e);
				}
			}
			
		} else {
			
			try {
				
				for ( int retry = 0; retry <= this.comm.config().retry().intValue(); ) {
					
					this.sendByte(ENQ);
					
					for ( ;; ) {
						
						Byte b = this.queue.pollByte(this.comm.config().timeout().t2());
						
						if ( b == null ) {
							
							this.comm.notifyLog(Secs1RetryCircuitControlLog.newInstance(retry));
							retry += 1;
							break;
							
						} else if ( b.byteValue() == ENQ && ! this.comm.config().isMaster().booleanValue() ) {
								
							try {
								this.receiving();
							}
							catch ( SecsException e ) {
								this.comm.notifyLog(e);
							}
							
							retry = 0;
							pack.reset();
							break;
							
						} else if ( b.byteValue() == EOT ) {
							
							if ( this.sending(pack.present()) ) {
								
								if ( pack.ebit() ) {
									
									this.sendMgr.putSended(pack.message());
									this.comm.notifySendedMessagePassThrough(pack.message());
									this.comm.notifySendedSecs1MessagePassThrough(pack.message());
									this.comm.notifyLog(new Secs1SendedMessageLog(pack.message()));
									
									return;
									
								} else {
									
									pack.next();
									retry = 0;
									break;
								}
								
							} else {
								
								this.comm.notifyLog(Secs1RetryCircuitControlLog.newInstance(retry));
								retry += 1;
								break;
							}
						}
					}
				}
				
				this.sendMgr.putException(
						pack.message(),
						new Secs1RetryOverException());
			}
			catch ( Secs1Exception e ) {
				this.sendMgr.putException(pack.message(), e);
				this.comm.notifyLog(e);
			}
		}
	}
	
	private boolean sending(AbstractSecs1MessageBlock block)
			throws Secs1Exception, InterruptedException {
		
		this.comm.notifyLog(new Secs1TrySendMessageBlockLog(block));
		
		this.sendBytes(block.getBytes());
		
		Byte b = this.queue.pollByte(this.comm.config().timeout().t2());
		
		if ( b == null ) {
			
			this.comm.notifyLog(Secs1TimeoutT2AckCircuitControlLog.newInstance(block));
			return false;
			
		} else if ( b.byteValue() == ACK ) {
			
			this.comm.notifyLog(new Secs1SendedMessageBlockLog(block));
			return true;
			
		} else {
			
			this.comm.notifyLog(Secs1NotReceiveAckCircuitControlLog.newInstance(block, b));
			return false;
		}
	}
	
	private final LinkedList<AbstractSecs1MessageBlock> cacheBlocks = new LinkedList<>();
	
	private void receiving() throws Secs1Exception, InterruptedException {
		
		this.sendByte(EOT);
		
		byte[] bs = new byte[257];
		
		{
			int r = this.queue.pollBytes(bs, 0, 1, this.comm.config().timeout().t2());
			
			if ( r <= 0 ) {
				this.sendByte(NAK);
				this.comm.notifyLog(Secs1TimeoutT2LengthByteCircuitColtrolLog.newInstance());
				return;
			}
		}
		
		{
			int len = (int)(bs[0]) & 0x000000FF;
			
			if ( len < 10 || len > 254 ) {
				this.queue.garbageBytes(this.comm.config().timeout().t1());
				this.sendByte(NAK);
				this.comm.notifyLog(Secs1IllegalLengthByteCircuitControlLog.newInstance(len));
				return;
			}
			
			for (int pos = 1, m = (len + 3); pos < m;) {
				
				int r = this.queue.pollBytes(bs, pos, m, this.comm.config().timeout().t1());
				
				if ( r <= 0 ) {
					this.sendByte(NAK);
					this.comm.notifyLog(Secs1TimeoutT1CircuitControlLog.newInstance(pos));
					return;
				}
				
				pos += r;
			}
		}
		
		AbstractSecs1MessageBlock block = new AbstractSecs1MessageBlock(bs) {

			private static final long serialVersionUID = -1187993676063154279L;
		};
		
		if ( block.checkSum() ) {
			
			this.sendByte(ACK);
			
		} else {
			
			this.queue.garbageBytes(this.comm.config().timeout().t1());
			this.sendByte(NAK);
			this.comm.notifyLog(Secs1SumCheckMismatchCirsuitControlLog.newInstance());
			return;
		}
		
		this.comm.notifyLog(new Secs1ReceiveMessageBlockLog(block));
		
		if (this.comm.config().isCheckMessageBlockDeviceId().booleanValue()) {
			if (block.deviceId() != this.comm.config().deviceId().intValue()) {
				return;
			}
		}
		
		if ( this.cacheBlocks.isEmpty() ) {
			
			this.cacheBlocks.add(block);
			
		} else {
			
			AbstractSecs1MessageBlock prev = this.cacheBlocks.getLast();
			
			if ( prev.equalsSystemBytes(block) ) {
				
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
				
				AbstractSecs1Message s1msg = Secs1MessageBuilder.fromBlocks(cacheBlocks);
				
				AbstractSecs1Message m = this.transMgr.put(s1msg);
				
				if ( m != null ) {
					this.comm.notifyReceiveMessage(m);
				}
				
				this.comm.notifyReceiveMessagePassThrough(s1msg);
				this.comm.notifyReceiveSecs1MessagePassThrough(s1msg);
				this.comm.notifyLog(new Secs1ReceiveMessageLog(s1msg));
			}
			catch ( Secs2Exception e ) {
				this.comm.notifyLog(e);
			}
			finally {
				this.cacheBlocks.clear();
			}
			
		} else {
			
			this.transMgr.resetTimer(block);
			
			Byte b = this.queue.pollByte(this.comm.config().timeout().t4());
			
			if ( b == null ) {
				
				this.comm.notifyLog(Secs1TimeoutT4CircuitControlLog.newInstance(block));
				
			} else if ( b.byteValue() == ENQ ) {
				
				this.receiving();
				
			} else {
				
				this.comm.notifyLog(Secs1NotReceiveNextBlockEnqCircuitControlLog.newInstance(block, b));
			}
		}
	}
	
}
