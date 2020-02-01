package com.shimizukenta.secs.hsmsss;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
import com.shimizukenta.secs.secs2.Secs2Exception;

public abstract class HsmsSsCommunicator extends AbstractSecsCommunicator {
	
	private final ExecutorService execServ = Executors.newCachedThreadPool(r -> {
		Thread th = new Thread(r);
		th.setDaemon(true);
		return th;
	});
	
	private final HsmsSsCommunicatorConfig hsmsSsConfig;
	private final HsmsSsMessageSendReplyManager sendReplyManager;
	
	private HsmsSsCommunicateState hsmsSsCommunicateState;
	
	public HsmsSsCommunicator(HsmsSsCommunicatorConfig config) {
		super(config);
		
		this.hsmsSsConfig = config;
		this.sendReplyManager = new HsmsSsMessageSendReplyManager(execServ, config.timeout(), msg -> {
			
			synchronized ( channels ) {
				
				if ( channels.isEmpty() ) {
					throw new HsmsSsNotConnectedException(msg);
				}
				
				for ( AsynchronousSocketChannel ch : channels ) {
					
					send(ch, msg);
					
					sendReplyManager().notifySendCompleted(msg);
				}
			}
		});
		
		this.notifyHsmsSsCommunicateStateChange(HsmsSsCommunicateState.NOT_CONNECTED);
		
	}

	protected void send(AsynchronousSocketChannel ch, HsmsSsMessage msg)
			throws InterruptedException, HsmsSsSendMessageException {
		
		entryLog(new SecsLog("Try Send HsmsSs-Message", msg));
		
		notifyTrySendMessagePassThrough(msg);
		
		try {
			byte[] head = msg.header10Bytes();
			byte[] body = msg.secs2().secs2Bytes();
			
			int len = head.length + body.length;
			
			int bflen = len + 4;
			
			if ( bflen > 0x7FFFFFFF || bflen < 14 ) {
				throw new HsmsSsSendMessageSizeException(msg);
			}
			
			ByteBuffer bf = ByteBuffer.allocate(bflen);
			bf.putInt(len);
			bf.put(head);
			bf.put(body);
			((Buffer)bf).flip();
			
			while ( bf.hasRemaining() ) {
				
				Future<Integer> f = ch.write(bf);
				
				try {
					int w = f.get().intValue();
					
					if ( w <= 0 ) {
						break;
					}
				}
				catch ( InterruptedException e ) {
					f.cancel(true);
					throw e;
				}
			}
			
			notifySendedMessagePassThrough(msg);
		}
		catch ( ExecutionException e ) {
			throw new HsmsSsSendMessageException(msg, e.getCause());
		}
		catch ( Secs2Exception e ) {
			throw new HsmsSsSendMessageException(msg, e);
		}
	}
	
	protected ExecutorService executorService() {
		return execServ;
	}
	
	protected HsmsSsCommunicatorConfig hsmsSsConfig() {
		return hsmsSsConfig;
	}
	
	protected HsmsSsMessageSendReplyManager sendReplyManager() {
		return sendReplyManager;
	}
	
	private final BlockingQueue<HsmsSsMessage> recvDataMsgQueue = new LinkedBlockingQueue<>();
	
	@Override
	public void open() throws IOException {
		
		super.open();
		
		execServ.execute(() -> {
			try {
				for ( ;; ) {
					notifyReceiveMessage(recvDataMsgQueue.take());
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
	
	protected void putReceiveDataMessage(HsmsSsMessage msg) {
		recvDataMsgQueue.offer(msg);
	}
	
	public static HsmsSsCommunicator newInstance(HsmsSsCommunicatorConfig config) {
		
		switch ( config.protocol() ) {
		case PASSIVE: {
			
			if ( config.rebindIfPassive().isPresent() ) {
				
				return new HsmsSsRebindPassiveCommunicator(config);
				
			} else {
				
				return new HsmsSsPassiveCommunicator(config);
			}
			/* break; */
		}
		case ACTIVE: {
			
			return new HsmsSsActiveCommunicator(config);
			/* break; */
		}
		default: {
			
			throw new IllegalStateException("undefined protocol: " + config.protocol());
		}
		}
	}
	
	public static HsmsSsCommunicator open(HsmsSsCommunicatorConfig config) throws IOException {
		
		final HsmsSsCommunicator inst = newInstance(config);
		
		try {
			inst.open();
		}
		catch ( IOException e ) {
			
			try {
				inst.close();
			}
			catch ( IOException giveup ) {
			}
			
			throw e;
		}
		
		return inst;
	}
	
	private final Object syncHsmsSsCommunicateState = new Object();
	
	protected HsmsSsCommunicateState hsmsSsCommunicateState() {
		synchronized ( syncHsmsSsCommunicateState ) {
			return hsmsSsCommunicateState;
		}
	}
	
	protected void notifyHsmsSsCommunicateStateChange(HsmsSsCommunicateState state) {
		synchronized ( syncHsmsSsCommunicateState ) {
			
			if ( this.hsmsSsCommunicateState != state ) {
				
				this.hsmsSsCommunicateState = state;
				
				entryLog(new SecsLog("HsmsSs-Connect-state-chenged: " + state.toString()));
				notifyCommunicatableStateChange(state.communicatable());
			}
		}
	}
	
	/* Secs-Log-Queue */
	private final BlockingQueue<SecsLog> logQueue = new LinkedBlockingQueue<>();
	
	protected void entryLog(SecsLog log) {
		logQueue.offer(log);
	}
	
	
	/* channels */
	private final Collection<AsynchronousSocketChannel> channels = new ArrayList<>();
	
	protected boolean addChannel(AsynchronousSocketChannel ch) {
		synchronized ( channels ) {
			if ( channels.isEmpty() ) {
				return channels.add(ch);
			} else {
				return false;
			}
		}
	}
	
	protected boolean removeChannel(AsynchronousSocketChannel ch) {
		synchronized ( channels ) {
			return channels.remove(ch);
		}
	}
	
	private final AtomicInteger autoNumber = new AtomicInteger();
	
	protected int autoNumber() {
		return autoNumber.incrementAndGet();
	}
	
	protected byte[] systemBytes() {
		
		int n = autoNumber();
		
		byte[] bs = new byte[] {
				(byte)0
				, (byte)0
				, (byte)(n >> 8)
				, (byte)n
		};
		
		if ( hsmsSsConfig().isEquip() ) {
			
			int sessionid = hsmsSsConfig().sessionId();
			
			bs[0] = (byte)(sessionid >> 8);
			bs[1] = (byte)sessionid;
		}
		
		return bs;
	}
	
	public Optional<HsmsSsMessage> send(HsmsSsMessage msg)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		try {
			return sendReplyManager.send(msg);
		}
		catch ( SecsException e ) {
			entryLog(new SecsLog(e));
			throw e;
		}
	}
	
	@Override
	public Optional<SecsMessage> send(int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		HsmsSsMessageType mt = HsmsSsMessageType.DATA;
		int sessionid = hsmsSsConfig().sessionId();
		byte[] sysbytes = systemBytes();
		
		byte[] head = new byte[] {
				(byte)(sessionid >> 8)
				, (byte)(sessionid)
				, (byte)strm
				, (byte)func
				, mt.pType()
				, mt.sType()
				, sysbytes[0]
				, sysbytes[1]
				, sysbytes[2]
				, sysbytes[3]
		};
		
		if ( wbit ) {
			head[2] |= 0x80;
		}
		
		return send(new HsmsSsMessage(head, secs2)).map(msg -> (SecsMessage)msg);
	}

	@Override
	public Optional<SecsMessage> send(SecsMessage primary, int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		byte[] pri = primary.header10Bytes();
		
		HsmsSsMessageType mt = HsmsSsMessageType.DATA;
		int sessionid = hsmsSsConfig().sessionId();
		
		byte[] head = new byte[] {
				(byte)(sessionid >> 8)
				, (byte)(sessionid)
				, (byte)strm
				, (byte)func
				, mt.pType()
				, mt.sType()
				, pri[6]
				, pri[7]
				, pri[8]
				, pri[9]
		};
		
		if ( wbit ) {
			head[2] |= 0x80;
		}
		
		return send(new HsmsSsMessage(head, secs2)).map(msg -> (SecsMessage)msg);
	}
	
	protected HsmsSsMessage createSelectRequest() {
		return createHsmsSsControlPrimaryMessage(HsmsSsMessageType.SELECT_REQ);
	}
	
	protected HsmsSsMessage createSelectResponse(HsmsSsMessage primary, HsmsSsMessageSelectStatus status) {
		
		HsmsSsMessageType mt = HsmsSsMessageType.SELECT_RSP;
		byte[] pri = primary.header10Bytes();
		
		return new HsmsSsMessage(new byte[] {
				pri[0]
				, pri[1]
				, (byte)0
				, status.statusCode()
				, mt.pType()
				, mt.sType()
				, pri[6]
				, pri[7]
				, pri[8]
				, pri[9]
		});
	}
	
	/**
	 * Blocking-method<br />
	 * 
	 * @return true if success
	 * @throws InterruptedException
	 * @throws SecsException
	 */
	public boolean linktest() throws InterruptedException, SecsException {
		return send(createLinktestRequest()).isPresent();
	}
	
	protected HsmsSsMessage createLinktestRequest() {
		return createHsmsSsControlPrimaryMessage(HsmsSsMessageType.LINKTEST_REQ);
	}
	
	protected HsmsSsMessage createLinktestResponse(HsmsSsMessage primary) {
		
		HsmsSsMessageType mt = HsmsSsMessageType.LINKTEST_RSP;
		byte[] pri = primary.header10Bytes();
		
		return new HsmsSsMessage(new byte[] {
				pri[0]
				, pri[1]
				, (byte)0
				, (byte)0
				, mt.pType()
				, mt.sType()
				, pri[6]
				, pri[7]
				, pri[8]
				, pri[9]
		});
	}
	
	protected HsmsSsMessage createRejectRequest(HsmsSsMessage ref, HsmsSsMessageRejectReason reason) {
		
		HsmsSsMessageType mt = HsmsSsMessageType.REJECT_REQ;
		byte[] bs = ref.header10Bytes();
		byte b = reason == HsmsSsMessageRejectReason.NOT_SUPPORT_TYPE_P ? bs[4] : bs[5];
		
		return new HsmsSsMessage(new byte[] {
				bs[0]
				, bs[1]
				, b
				, reason.reasonCode()
				, mt.pType()
				, mt.sType()
				, bs[6]
				, bs[7]
				, bs[8]
				, bs[9]
		});
	}
	
	protected HsmsSsMessage createSeparateRequest() {
		return createHsmsSsControlPrimaryMessage(HsmsSsMessageType.SEPARATE_REQ);
	}
	
	private HsmsSsMessage createHsmsSsControlPrimaryMessage(HsmsSsMessageType mt) {
		
		byte[] sysbytes = systemBytes();
		
		return new HsmsSsMessage(new byte[] {
				(byte)0xFF
				, (byte)0xFF
				, (byte)0
				, (byte)0
				, mt.pType()
				, mt.sType()
				, sysbytes[0]
				, sysbytes[1]
				, sysbytes[2]
				, sysbytes[3]
		});
	}
	
}
