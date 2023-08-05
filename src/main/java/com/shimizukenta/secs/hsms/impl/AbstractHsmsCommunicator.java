package com.shimizukenta.secs.hsms.impl;

import java.io.IOException;

import com.shimizukenta.secs.AbstractSecsCommunicatorConfig;
import com.shimizukenta.secs.hsms.HsmsCommunicateState;
import com.shimizukenta.secs.hsms.HsmsCommunicator;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.impl.AbstractSecsCommunicator;
import com.shimizukenta.secs.local.property.ObjectProperty;
import com.shimizukenta.secs.local.property.Observable;

public abstract class AbstractHsmsCommunicator extends AbstractSecsCommunicator implements HsmsCommunicator {

	private final ObjectProperty<HsmsCommunicateState> hsmsCommState = ObjectProperty.newInstance(HsmsCommunicateState.NOT_CONNECTED);
	
	public AbstractHsmsCommunicator(AbstractSecsCommunicatorConfig config) {
		super(config);
		
		this.hsmsCommState.computeIsEqualTo(HsmsCommunicateState.SELECTED).addChangeListener(this::notifyCommunicatableStateChange);
	}
	
	@Override
	public void open() throws IOException {
		super.open();
	}
	
	@Override
	public void close() throws IOException {
		super.close();
	}
	
	
	protected Observable<HsmsCommunicateState> getHsmsCommunicateState() {
		return this.hsmsCommState;
	}
	
	public void notifyHsmsCommunicateState(HsmsCommunicateState state) {
		this.hsmsCommState.set(state);
	}
	
	
	public void notifyReceiveHsmsMessage(HsmsMessage message) throws InterruptedException {
		super.notifyReceiveSecsMessage(message);
		this.prototypeNotifyReceiveHsmsMessage(message);
	}
	
	abstract protected void prototypeNotifyReceiveHsmsMessage(HsmsMessage message) throws InterruptedException;
	
	
	public void notifyTrySendHsmsMessagePassThrough(HsmsMessage message) throws InterruptedException {
		super.notifyTrySendSecsMessagePassThrough(message);
		this.prototypeNotifyTrySendHsmsMessagePassThrough(message);
	}
	
	abstract protected void prototypeNotifyTrySendHsmsMessagePassThrough(HsmsMessage message) throws InterruptedException;
	
	
	public void notifySendedHsmsMessagePassThrough(HsmsMessage message) throws InterruptedException {
		super.notifySendedSecsMessagePassThrough(message);
		this.prototypeNotifySendedHsmsMessagePassThrough(message);
	}
	
	abstract protected void prototypeNotifySendedHsmsMessagePassThrough(HsmsMessage message) throws InterruptedException;
	
	
	public void notifyReceiveHsmsMessagePassThrough(HsmsMessage message) throws InterruptedException {
		super.notifyReceiveSecsMessagePassThrough(message);
		this.prototypeNotifyReceiveHsmsMessagePassThrough(message);
	}
	
	abstract protected void prototypeNotifyReceiveHsmsMessagePassThrough(HsmsMessage message) throws InterruptedException;

}
