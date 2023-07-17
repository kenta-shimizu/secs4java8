package com.shimizukenta.secs.hsms.impl;

import java.io.IOException;

import com.shimizukenta.secs.AbstractSecsCommunicatorConfig;
import com.shimizukenta.secs.hsms.HsmsCommunicateState;
import com.shimizukenta.secs.hsms.HsmsCommunicateStateChangeListener;
import com.shimizukenta.secs.hsms.HsmsCommunicator;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessagePassThroughListener;
import com.shimizukenta.secs.impl.AbstractSecsCommunicator;
import com.shimizukenta.secs.local.property.ObjectProperty;

public abstract class AbstractHsmsCommunicator extends AbstractSecsCommunicator implements HsmsCommunicator {

	private final ObjectProperty<HsmsCommunicateState> hsmsCommState = ObjectProperty.newInstance(HsmsCommunicateState.NOT_CONNECTED);
	
	private final HsmsMessagePassThroughQueueObserver trySendHsmsMsgPassThroughQueueObserver;
	private final HsmsMessagePassThroughQueueObserver sendedHsmsMsgPassThroughQueueObserver;
	private final HsmsMessagePassThroughQueueObserver recvHsmsMsgPassThroughQueueObserver;
	
	public AbstractHsmsCommunicator(AbstractSecsCommunicatorConfig config) {
		super(config);
		
		this.hsmsCommState.computeIsEqualTo(HsmsCommunicateState.SELECTED).addChangeListener(this::notifyCommunicatableStateChange);
		
		this.trySendHsmsMsgPassThroughQueueObserver = new HsmsMessagePassThroughQueueObserver(this);
		this.sendedHsmsMsgPassThroughQueueObserver = new HsmsMessagePassThroughQueueObserver(this);
		this.recvHsmsMsgPassThroughQueueObserver = new HsmsMessagePassThroughQueueObserver(this);
	}
	
	@Override
	public void open() throws IOException {
		super.open();
	}
	
	@Override
	public void close() throws IOException {
		super.close();
	}
	
	@Override
	public boolean addHsmsCommunicateStateChangeListener(HsmsCommunicateStateChangeListener listener) {
		return this.hsmsCommState.addChangeListener(listener::changed);
	}
	
	@Override
	public boolean removeHsmsCommunicateStateChangeListener(HsmsCommunicateStateChangeListener listener) {
		return this.hsmsCommState.removeChangeListener(listener::changed);
	}
	
	public void notifyHsmsCommunicateState(HsmsCommunicateState state) {
		this.hsmsCommState.set(state);
	}
	
	
	@Override
	public boolean addTrySendHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr) {
		return this.trySendHsmsMsgPassThroughQueueObserver.addListener(lstnr);
	}
	
	@Override
	public boolean removeTrySendHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr) {
		return this.trySendHsmsMsgPassThroughQueueObserver.removeListener(lstnr);
	}
	
	public void notifyTrySendHsmsMessagePassThrough(HsmsMessage msg) throws InterruptedException {
		this.trySendHsmsMsgPassThroughQueueObserver.put(msg);
	}
	
	
	@Override
	public boolean addSendedHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr) {
		return this.sendedHsmsMsgPassThroughQueueObserver.addListener(lstnr);
	}
	
	@Override
	public boolean removeSendedHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr) {
		return this.sendedHsmsMsgPassThroughQueueObserver.removeListener(lstnr);
	}
	
	public void notifySendedHsmsMessagePassThrough(HsmsMessage msg) throws InterruptedException {
		this.sendedHsmsMsgPassThroughQueueObserver.put(msg);
	}
	
	
	@Override
	public boolean addReceiveHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr) {
		return this.recvHsmsMsgPassThroughQueueObserver.addListener(lstnr);
	}
	
	@Override
	public boolean removeReceiveHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr) {
		return this.recvHsmsMsgPassThroughQueueObserver.removeListener(lstnr);
	}
	
	public void notifyReceiveHsmsMessagePassThrough(HsmsMessage msg) throws InterruptedException {
		this.recvHsmsMsgPassThroughQueueObserver.put(msg);
	}
	
}
