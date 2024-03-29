package com.shimizukenta.secs.hsms.impl;

import com.shimizukenta.secs.hsms.HsmsCommunicateState;
import com.shimizukenta.secs.hsms.HsmsCommunicateStateChangeBiListener;
import com.shimizukenta.secs.hsms.HsmsCommunicateStateChangeListener;
import com.shimizukenta.secs.hsms.HsmsGemAccessor;
import com.shimizukenta.secs.impl.AbstractPropertyBiObserver;
import com.shimizukenta.secs.local.property.Observable;

public class HsmsCommunicateStatePropertyBiObserver extends AbstractPropertyBiObserver<HsmsGemAccessor, HsmsCommunicateState, HsmsCommunicateStateChangeListener, HsmsCommunicateStateChangeBiListener> {

	public HsmsCommunicateStatePropertyBiObserver(
			HsmsGemAccessor communicator,
			Observable<HsmsCommunicateState> observer) {
		
		super(communicator, observer);
	}

	@Override
	protected void notifyValueToListener(HsmsCommunicateStateChangeListener listener, HsmsCommunicateState value) {
		listener.changed(value);
	}

	@Override
	protected void notifyValueToBiListener(
			HsmsCommunicateStateChangeBiListener biListener,
			HsmsCommunicateState value,
			HsmsGemAccessor communicator) {
		
		biListener.changed(value, communicator);
	}
	
}
