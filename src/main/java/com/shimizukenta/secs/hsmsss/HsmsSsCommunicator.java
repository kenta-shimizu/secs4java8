package com.shimizukenta.secs.hsmsss;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import com.shimizukenta.secs.AbstractProperty;
import com.shimizukenta.secs.AbstractSecsCommunicator;
import com.shimizukenta.secs.ByteArrayProperty;
import com.shimizukenta.secs.Property;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.secs2.Secs2;

/**
 * This class is implementation of HSMS-SS (SEMI-E37.1)<br />
 * To create new instance, {@link #newInstance(HsmsSsCommunicatorConfig)}<br />
 * To craete new instance and open, {@link #open(HsmsSsCommunicatorConfig)}
 * 
 * @author kenta-shimizu
 *
 */
public abstract class HsmsSsCommunicator extends AbstractSecsCommunicator {
	
	private final HsmsSsCommunicatorConfig hsmsSsConfig;
	protected final HsmsSsSendReplyManager sendReplyManager;
	
	private final ByteArrayProperty sessionIdBytes = new ByteArrayProperty(new byte[] {0, 0});
	
	private final Property<HsmsSsCommunicateState> hsmsSsCommStateProperty = new AbstractProperty<HsmsSsCommunicateState>(HsmsSsCommunicateState.NOT_CONNECTED) {
		private static final long serialVersionUID = 2735232737920848138L;
	};
	
	protected HsmsSsCommunicator(HsmsSsCommunicatorConfig config) {
		super(config);
		
		this.hsmsSsConfig = config;
		this.sendReplyManager = new HsmsSsSendReplyManager(this);
		
		this.hsmsSsConfig.sessionId().addChangeListener(i -> {
			int v = i.intValue();
			byte[] bs = new byte[] {
					(byte)(v >> 8),
					(byte)v
			};
			sessionIdBytes.set(bs);
		});
	}
	
	/**
	 * create new HSMS-SS-Communicator instance.
	 * 
	 * @param config
	 * @return new HSMS-SS-Communicator instance
	 */
	public static HsmsSsCommunicator newInstance(HsmsSsCommunicatorConfig config) {
		
		switch ( config.protocol().get() ) {
		case PASSIVE: {
			
			if ( config.rebindIfPassive().getMilliSeconds() >= 0L ) {
				
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
	
	/**
	 * create new HSMS-SS-Communicator instance and {@link #open()}
	 * 
	 * @param config
	 * @return new HSMS-SS-Communicator instance
	 * @throws IOException
	 */
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
	

	protected final HsmsSsCommunicatorConfig hsmsSsConfig() {
		return hsmsSsConfig;
	}
	
	@Override
	public void open() throws IOException {
		super.open();
		
		this.hsmsSsCommStateProperty.addChangeListener(state -> {
			notifyLog("HsmsSs-Connect-state-chenged: " + state.toString());
		});
		
		this.hsmsSsCommStateProperty.addChangeListener(state -> {
			notifyCommunicatableStateChange(state.communicatable());
		});
	}
	
	@Override
	public void close() throws IOException {
		
		synchronized ( this ) {
			if ( isClosed() ) {
				return;
			}
		}
		
		super.close();
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
	
	protected Optional<AsynchronousSocketChannel> optionalChannel() {
		synchronized ( channels ) {
			return channels.stream().findAny();
		}
	}
	
	
	/* HSMS Communicate State */
	protected HsmsSsCommunicateState hsmsSsCommunicateState() {
		return hsmsSsCommStateProperty.get();
	}
	
	protected void notifyHsmsSsCommunicateStateChange(HsmsSsCommunicateState state) {
		hsmsSsCommStateProperty.set(state);
	}
	
	
	/**
	 * Blocking-method<br />
	 * 
	 * @return true if success
	 * @throws InterruptedException
	 * @throws SecsException
	 */
	public boolean linktest() throws InterruptedException {
		try {
			return send(createLinktestRequest()).isPresent();
		}
		catch ( SecsException e ) {
			return false;
		}
	}
	
	
	private final AtomicInteger autoNumber = new AtomicInteger();
	
	private int autoNumber() {
		return autoNumber.incrementAndGet();
	}
	
	protected byte[] systemBytes() {
		
		byte[] bs = new byte[4];
		
		if ( hsmsSsConfig().isEquip().booleanValue() ) {
			
			byte[] xs = sessionIdBytes.get();
			bs[0] = xs[0];
			bs[1] = xs[1];
			
		} else {
			
			bs[0] = (byte)0;
			bs[1] = (byte)0;
		}
		
		int n = autoNumber();
		
		bs[2] = (byte)(n >> 8);
		bs[3] = (byte)n;
		
		return bs;
	}
	
	protected void send(AsynchronousSocketChannel channel, HsmsSsMessage msg)
			throws SecsSendMessageException, SecsException,
			InterruptedException {
		
		sendReplyManager.send(channel, msg);
	}
	
	public Optional<HsmsSsMessage> send(HsmsSsMessage msg)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException,
			InterruptedException {
		
		try {
			return sendReplyManager.send(msg);
		}
		catch ( SecsException e ) {
			notifyLog(e);
			throw e;
		}
	}
	
	@Override
	public Optional<SecsMessage> send(int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		HsmsSsMessageType mt = HsmsSsMessageType.DATA;
		byte[] xs = sessionIdBytes.get();
		byte[] sysbytes = systemBytes();
		
		byte[] head = new byte[] {
				xs[0],
				xs[1],
				(byte)strm,
				(byte)func,
				mt.pType(),
				mt.sType(),
				sysbytes[0],
				sysbytes[1],
				sysbytes[2],
				sysbytes[3]
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
		byte[] xs = sessionIdBytes.get();
		
		byte[] head = new byte[] {
				xs[0],
				xs[1],
				(byte)strm,
				(byte)func,
				mt.pType(),
				mt.sType(),
				pri[6],
				pri[7],
				pri[8],
				pri[9]
		};
		
		if ( wbit ) {
			head[2] |= 0x80;
		}
		
		return send(createHsmsSsMessage(head, secs2)).map(msg -> (SecsMessage)msg);
	}
	
	public HsmsSsMessage createHsmsSsMessage(byte[] head) {
		return createHsmsSsMessage(head, Secs2.empty());
	}
	
	public HsmsSsMessage createHsmsSsMessage(byte[] head, Secs2 body) {
		return new HsmsSsMessage(head, body);
	}
	
	public HsmsSsMessage createSelectRequest() {
		return createHsmsSsControlPrimaryMessage(HsmsSsMessageType.SELECT_REQ);
	}
	
	public HsmsSsMessage createSelectResponse(HsmsSsMessage primary, HsmsSsMessageSelectStatus status) {
		
		HsmsSsMessageType mt = HsmsSsMessageType.SELECT_RSP;
		byte[] pri = primary.header10Bytes();
		
		return createHsmsSsMessage(new byte[] {
				pri[0],
				pri[1],
				(byte)0,
				status.statusCode(),
				mt.pType(),
				mt.sType(),
				pri[6],
				pri[7],
				pri[8],
				pri[9]
		});
	}
	
	public HsmsSsMessage createDeselectRequest() {
		return createHsmsSsControlPrimaryMessage(HsmsSsMessageType.DESELECT_REQ);
	}
	
	public HsmsSsMessage createDeselectResponse(HsmsSsMessage primary) {
		
		HsmsSsMessageType mt = HsmsSsMessageType.DESELECT_RSP;
		byte[] pri = primary.header10Bytes();
		
		return createHsmsSsMessage(new byte[] {
				pri[0],
				pri[1],
				(byte)0,
				(byte)0,
				mt.pType(),
				mt.sType(),
				pri[6],
				pri[7],
				pri[8],
				pri[9]
		});
	}
	
	public HsmsSsMessage createLinktestRequest() {
		return createHsmsSsControlPrimaryMessage(HsmsSsMessageType.LINKTEST_REQ);
	}
	
	public HsmsSsMessage createLinktestResponse(HsmsSsMessage primary) {
		
		HsmsSsMessageType mt = HsmsSsMessageType.LINKTEST_RSP;
		byte[] pri = primary.header10Bytes();
		
		return createHsmsSsMessage(new byte[] {
				pri[0],
				pri[1],
				(byte)0,
				(byte)0,
				mt.pType(),
				mt.sType(),
				pri[6],
				pri[7],
				pri[8],
				pri[9]
		});
	}
	
	public HsmsSsMessage createRejectRequest(HsmsSsMessage ref, HsmsSsMessageRejectReason reason) {
		
		HsmsSsMessageType mt = HsmsSsMessageType.REJECT_REQ;
		byte[] bs = ref.header10Bytes();
		byte b = reason == HsmsSsMessageRejectReason.NOT_SUPPORT_TYPE_P ? bs[4] : bs[5];
		
		return createHsmsSsMessage(new byte[] {
				bs[0],
				bs[1],
				b,
				reason.reasonCode(),
				mt.pType(),
				mt.sType(),
				bs[6],
				bs[7],
				bs[8],
				bs[9]
		});
	}
	
	public HsmsSsMessage createSeparateRequest() {
		return createHsmsSsControlPrimaryMessage(HsmsSsMessageType.SEPARATE_REQ);
	}
	
	private HsmsSsMessage createHsmsSsControlPrimaryMessage(HsmsSsMessageType mt) {
		
		byte[] sysbytes = systemBytes();
		
		return createHsmsSsMessage(new byte[] {
				(byte)0xFF,
				(byte)0xFF,
				(byte)0,
				(byte)0,
				mt.pType(),
				mt.sType(),
				sysbytes[0],
				sysbytes[1],
				sysbytes[2],
				sysbytes[3]
		});
	}
	
}
