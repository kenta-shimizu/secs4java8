package com.shimizukenta.secs.hsms.impl;

import com.shimizukenta.secs.hsms.HsmsCommunicateState;
import com.shimizukenta.secs.hsms.HsmsCommunicateStateChangeListener;
import com.shimizukenta.secs.hsms.HsmsSessionCommunicateStateChangeBiListener;
import com.shimizukenta.secs.impl.AbstractPropertyBiObserver;
import com.shimizukenta.secs.local.property.Observable;

public class HsmsSessionCommunicateStatePropertyBiObserver extends AbstractPropertyBiObserver<AbstractHsmsSession, HsmsCommunicateState, HsmsCommunicateStateChangeListener, HsmsSessionCommunicateStateChangeBiListener> {

	public HsmsSessionCommunicateStatePropertyBiObserver(
			AbstractHsmsSession communicator,
			Observable<HsmsCommunicateState> observer) {
		
		super(communicator, observer);
	}

	@Override
	protected void notifyValueToListener(HsmsCommunicateStateChangeListener listener, HsmsCommunicateState value) {
		listener.changed(value);
	}

	@Override
	protected void notifyValueToBiListener(
			HsmsSessionCommunicateStateChangeBiListener biListener,
			HsmsCommunicateState value,
			AbstractHsmsSession communicator) {
		
		biListener.changed(value, communicator);
	}
	
}
