package com.shimizukenta.secs.impl;

import com.shimizukenta.secs.SecsCommunicatableStateChangeBiListener;
import com.shimizukenta.secs.SecsCommunicatableStateChangeListener;
import com.shimizukenta.secs.local.property.Observable;

public class SecsCommunicatableStatePropertyBiObserver extends AbstractPropertyBiObserver<AbstractSecsCommunicator, Boolean, SecsCommunicatableStateChangeListener, SecsCommunicatableStateChangeBiListener> {

	public SecsCommunicatableStatePropertyBiObserver(AbstractSecsCommunicator communicator, Observable<Boolean> observer) {
		super(communicator, observer);
	}

	@Override
	protected void notifyValueToListener(SecsCommunicatableStateChangeListener listener, Boolean value) {
		listener.changed(value.booleanValue());
	}

	@Override
	protected void notifyValueToBiListener(
			SecsCommunicatableStateChangeBiListener biListener,
			Boolean value,
			AbstractSecsCommunicator communicator) {
		
		biListener.changed(value.booleanValue(), communicator);
	}

}
