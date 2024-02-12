package com.shimizukenta.secs.impl;

import com.shimizukenta.secs.GemAccessor;
import com.shimizukenta.secs.SecsCommunicatableStateChangeBiListener;
import com.shimizukenta.secs.SecsCommunicatableStateChangeListener;
import com.shimizukenta.secs.local.property.Observable;

public class SecsCommunicatableStatePropertyBiObserver extends AbstractPropertyBiObserver<GemAccessor, Boolean, SecsCommunicatableStateChangeListener, SecsCommunicatableStateChangeBiListener> {

	public SecsCommunicatableStatePropertyBiObserver(GemAccessor communicator, Observable<Boolean> observer) {
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
			GemAccessor communicator) {
		
		biListener.changed(value.booleanValue(), communicator);
	}

}
