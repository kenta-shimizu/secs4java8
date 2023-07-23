package com.shimizukenta.secs.hsmsss.impl;

import com.shimizukenta.secs.hsms.HsmsCommunicateState;
import com.shimizukenta.secs.hsms.HsmsCommunicateStateChangeListener;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicateStateChangeBiListener;
import com.shimizukenta.secs.impl.AbstractPropertyBiObserver;
import com.shimizukenta.secs.local.property.Observable;

public class HsmsSsCommunicateStatePropertyBiObserver extends AbstractPropertyBiObserver<AbstractHsmsSsCommunicator, HsmsCommunicateState, HsmsCommunicateStateChangeListener, HsmsSsCommunicateStateChangeBiListener> {

	public HsmsSsCommunicateStatePropertyBiObserver(
			AbstractHsmsSsCommunicator communicator,
			Observable<HsmsCommunicateState> observer) {
		
		super(communicator, observer);
	}
	
	@Override
	protected void notifyValueToListener(HsmsCommunicateStateChangeListener listener, HsmsCommunicateState value) {
		listener.changed(value);
	}

	@Override
	protected void notifyValueToBiListener(
			HsmsSsCommunicateStateChangeBiListener biListener,
			HsmsCommunicateState value,
			AbstractHsmsSsCommunicator communicator) {
		
		biListener.changed(value, communicator);
	}
	
}
