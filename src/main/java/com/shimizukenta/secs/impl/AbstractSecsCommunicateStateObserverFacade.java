package com.shimizukenta.secs.impl;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.shimizukenta.secs.SecsCommunicatableStateChangeBiListener;
import com.shimizukenta.secs.SecsCommunicatableStateChangeListener;
import com.shimizukenta.secs.SecsCommunicateStateDetectable;
import com.shimizukenta.secs.SecsGemAccessor;
import com.shimizukenta.secs.local.property.BooleanProperty;
import com.shimizukenta.secs.local.property.Observable;

public abstract class AbstractSecsCommunicateStateObserverFacade implements SecsCommunicateStateDetectable {
	
	private class SecsCommStateBiObserver extends AbstractPropertyBiObserver<SecsGemAccessor, Boolean, SecsCommunicatableStateChangeListener, SecsCommunicatableStateChangeBiListener> {

		public SecsCommStateBiObserver(SecsGemAccessor accessor, Observable<Boolean> observer) {
			super(accessor, observer);
		}

		@Override
		protected void notifyValueToListener(SecsCommunicatableStateChangeListener listener, Boolean value) {
			listener.changed(value.booleanValue());
		}

		@Override
		protected void notifyValueToBiListener(SecsCommunicatableStateChangeBiListener biListener, Boolean value, SecsGemAccessor accessor) {
			biListener.changed(value.booleanValue(), accessor);
		}

	}
	
	private final BooleanProperty secsCommStateProp = BooleanProperty.newInstance(false);
	private final SecsCommStateBiObserver secsCommStateObserver;
	
	public AbstractSecsCommunicateStateObserverFacade(SecsGemAccessor accessor) {
		this.secsCommStateObserver = new SecsCommStateBiObserver(accessor, this.secsCommStateProp);
	}

	@Override
	public boolean isCommunicatable() {
		return this.secsCommStateProp.booleanValue();
	}

	@Override
	public void waitUntilCommunicatable() throws InterruptedException {
		this.secsCommStateProp.waitUntilTrue();
	}

	@Override
	public void waitUntilCommunicatable(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.secsCommStateProp.waitUntilTrue(timeout, unit);
	}

	@Override
	public void waitUntilNotCommunicatable() throws InterruptedException {
		this.secsCommStateProp.waitUntilFalse();
	}

	@Override
	public void waitUntilNotCommunicatable(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.secsCommStateProp.waitUntilFalse(timeout, unit);
	}

	@Override
	public boolean addSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeListener listener) {
		return this.secsCommStateObserver.addListener(listener);
	}

	@Override
	public boolean removeSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeListener listener) {
		return this.secsCommStateObserver.removeListener(listener);
	}

	@Override
	public boolean addSecsCommunicatableStateChangeBiListener(SecsCommunicatableStateChangeBiListener biListener) {
		return this.secsCommStateObserver.addBiListener(biListener);
	}

	@Override
	public boolean removeSecsCommunicatableStateChangeBiListener(SecsCommunicatableStateChangeBiListener biListener) {
		return this.secsCommStateObserver.removeBiListener(biListener);
	}
	
	
	public void setSecsCommunicateState(boolean isCommunicatable) {
		this.secsCommStateProp.set(isCommunicatable);
	}

}
