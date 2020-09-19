package com.shimizukenta.secs.secs1;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import com.shimizukenta.secs.AbstractByteArrayProperty;
import com.shimizukenta.secs.AbstractSecsCommunicator;
import com.shimizukenta.secs.AbstractTimeProperty;
import com.shimizukenta.secs.InterruptableRunnable;
import com.shimizukenta.secs.ReadOnlyTimeProperty;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.SimpleByteArrayProperty;
import com.shimizukenta.secs.SimpleTimeProperty;
import com.shimizukenta.secs.secs2.Secs2;

/**
 * This class is implementation of SECS-I (SEMI-E4)<br />
 * To get instance, Secs1OnTcpIpCommunicator#newInstance
 * 
 * @author kenta-shimizu
 *
 */
public abstract class Secs1Communicator extends AbstractSecsCommunicator {
	
	protected static final byte ENQ = (byte)0x05;
	protected static final byte EOT = (byte)0x04;
	protected static final byte ACK = (byte)0x06;
	protected static final byte NAK = (byte)0x15;
	
	
	private final Secs1CommunicatorConfig secs1Config;
	private final Secs1SendReplyManager sendReplyManager;
	private final AbstractByteArrayProperty deviceIdBytes = new SimpleByteArrayProperty(new byte[] {0, 0});
	private final CircuitLoop circuit = new CircuitLoop();
	
	protected void circuitNotifyAll() {
		synchronized ( circuit ) {
			circuit.notifyAll();
		}
	}
	
	public Secs1Communicator(Secs1CommunicatorConfig config) {
		super(config);
		
		this.secs1Config = config;
		this.sendReplyManager = new Secs1SendReplyManager(this);
		
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
		
		executeLoopTask(circuit);
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
	
	
	abstract protected Optional<Byte> pollByte();
	abstract protected Optional<Byte> pollByte(byte[] request) throws InterruptedException;
	abstract protected Optional<Byte> pollByte(ReadOnlyTimeProperty timeout) throws InterruptedException;
	abstract protected Optional<Byte> pollByte(byte[] request, ReadOnlyTimeProperty timeout) throws InterruptedException;
	
	private Optional<Byte> pollByteT1() throws InterruptedException {
		return pollByte(secs1Config.timeout().t1());
	}
	
	private Optional<Byte> pollByteT2() throws InterruptedException {
		return pollByte(secs1Config.timeout().t2());
	}
	
	private Optional<Byte> pollByteT4() throws InterruptedException {
		return pollByte(secs1Config.timeout().t4());
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
			notifyLog(e);
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
	
	
	private enum PollCircuitControl {
		
		RX,
		TX,
		RETRY,
		
		;
	}
	
	
	private class CircuitLoop implements InterruptableRunnable {
		
		private final AbstractTimeProperty wdt = new SimpleTimeProperty(0.250F);
		private Secs1MessageBlock presentBlock;
		
		public CircuitLoop() {
			presentBlock = null;
		}
		
		@Override
		public void run() throws InterruptedException {
			
			try {
				
				if ( presentBlock == null ) {
					presentBlock = sendReplyManager.pollBlock();
				}
				
				if ( presentBlock == null ) {
					
					Byte b = pollByte().orElse(null);
					
					if ( b == null ) {
						
						wdt.wait(this);
						
					} else {
						
						if ( b.byteValue() == ENQ ) {
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
				notifyLog(e);
			}
			catch ( Throwable t ) {
				throw t;
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
				return executeInvokeAny(task, secs1Config().timeout().t2());
			}
			catch ( TimeoutException giveup ) {
			}
			catch ( ExecutionException e ) {
				
				Throwable t = e.getCause();
				
				if ( t instanceof RuntimeException ) {
					throw (RuntimeException)t;
				}
				
				notifyLog(e);
			}
			
			return PollCircuitControl.RETRY;
		}
		
		private void circuitControl() throws SecsException, InterruptedException {
			
			for ( int counter = 0, m = secs1Config.retry().intValue(); counter <= m; ) {
				
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
					
					notifyLog("Secs1Communicator#circuitControl pollByte RETRY");
					
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
					
					notifyLog("Receive Secs1MessageBlock T2-Timeout (Length-byte)");
					
					return;
				}
			}
			
			for ( int i = 1, m = bs.length ; i < m ; ++i ) {
				
				Optional<Byte> op = pollByteT1();
				
				if ( op.isPresent() ) {
					
					bs[i] = op.get().byteValue();
					
				} else {
					
					sendByte(NAK);
					
					notifyLog("Receive Secs1MessageBlock T1-Timeout (pos=" + i + ")");
					
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
							
							notifyLog("Wait next Block, receive not ENQ (" + String.format("%02X",  b) + ")", block);
						}
						
					} else {
						
						notifyLog("Wait next Block, T4-timeout", block);
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
			
			notifyLog(reason);
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
					
					notifyLog("Secs1Communicator#sendBlock revieve-not-ACK (" + String.format("%02X", b) + ")", presentBlock);
				}
				
			} else {
				
				notifyLog("Secs1Communicator#sendBlock Timeout-T2", presentBlock);
			}
			
			return false;
		}
	}
	
}
