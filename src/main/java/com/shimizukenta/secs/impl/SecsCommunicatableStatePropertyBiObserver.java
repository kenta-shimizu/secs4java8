package com.shimizukenta.secs.impl;

import com.shimizukenta.secs.SecsGemAccessor;
import com.shimizukenta.secs.SecsCommunicatableStateChangeBiListener;
import com.shimizukenta.secs.SecsCommunicatableStateChangeListener;
import com.shimizukenta.secs.local.property.Observable;

@Deprecated
public class SecsCommunicatableStatePropertyBiObserver extends AbstractPropertyBiObserver<SecsGemAccessor, Boolean, SecsCommunicatableStateChangeListener, SecsCommunicatableStateChangeBiListener> {

	public SecsCommunicatableStatePropertyBiObserver(SecsGemAccessor communicator, Observable<Boolean> observer) {
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
			SecsGemAccessor communicator) {
		
		biListener.changed(value.booleanValue(), communicator);
	}

}
