package com.shimizukenta.secs.hsms.impl;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.shimizukenta.secs.hsms.HsmsCommunicateState;
import com.shimizukenta.secs.hsms.HsmsCommunicateStateChangeBiListener;
import com.shimizukenta.secs.hsms.HsmsCommunicateStateChangeListener;
import com.shimizukenta.secs.hsms.HsmsCommunicateStateDetectable;
import com.shimizukenta.secs.hsms.HsmsGemAccessor;
import com.shimizukenta.secs.impl.AbstractPropertyBiObserver;
import com.shimizukenta.secs.impl.AbstractSecsCommunicateStateObserverFacade;
import com.shimizukenta.secs.local.property.ObjectProperty;
import com.shimizukenta.secs.local.property.Observable;

public abstract class AbstractHsmsCommunicateStateObserverFacade extends AbstractSecsCommunicateStateObserverFacade
		implements HsmsCommunicateStateDetectable {
	
	private class HsmsCommStateBiObserver extends AbstractPropertyBiObserver<HsmsGemAccessor, HsmsCommunicateState, HsmsCommunicateStateChangeListener, HsmsCommunicateStateChangeBiListener> {

		public HsmsCommStateBiObserver(HsmsGemAccessor accessor, Observable<HsmsCommunicateState> observer) {
			super(accessor, observer);
		}

		@Override
		protected void notifyValueToListener(HsmsCommunicateStateChangeListener listener, HsmsCommunicateState value) {
			listener.changed(value);
		}

		@Override
		protected void notifyValueToBiListener(HsmsCommunicateStateChangeBiListener biListener, HsmsCommunicateState value, HsmsGemAccessor accessor) {
			biListener.changed(value, accessor);
		}
		
	}

	private final ObjectProperty<HsmsCommunicateState> hsmsCommStateProp = ObjectProperty.newInstance(HsmsCommunicateState.NOT_CONNECTED);
	private final HsmsCommStateBiObserver hsmsCommStateObserver;
	
	public AbstractHsmsCommunicateStateObserverFacade(HsmsGemAccessor accessor) {
		super(accessor);
		
		this.hsmsCommStateObserver = new HsmsCommStateBiObserver(accessor, this.hsmsCommStateProp);
		
		this.hsmsCommStateProp.addChangeListener(v -> {
			this.setSecsCommunicateState(v.communicatable());
		});
	}

	@Override
	public HsmsCommunicateState getHsmsCommunicateState() {
		return this.hsmsCommStateProp.get();
	}

	@Override
	public void waitUntilHsmsCommunicateState(HsmsCommunicateState state) throws InterruptedException {
		this.hsmsCommStateProp.waitUntilEqualTo(state);
	}

	@Override
	public void waitUntilHsmsCommunicateState(HsmsCommunicateState state, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.hsmsCommStateProp.waitUntilEqualTo(state, timeout, unit);
	}

	@Override
	public void waitUntilNotHsmsCommunicateState(HsmsCommunicateState state) throws InterruptedException {
		this.hsmsCommStateProp.waitUntilNotEqualTo(state);
	}

	@Override
	public void waitUntilNotHsmsCommunicateState(HsmsCommunicateState state, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.hsmsCommStateProp.waitUntilNotEqualTo(state, timeout, unit);
	}

	@Override
	public boolean addHsmsCommunicateStateChangeListener(HsmsCommunicateStateChangeListener listener) {
		return this.hsmsCommStateObserver.addListener(listener);
	}

	@Override
	public boolean removeHsmsCommunicateStateChangeListener(HsmsCommunicateStateChangeListener listener) {
		return this.hsmsCommStateObserver.removeListener(listener);
	}

	@Override
	public boolean addHsmsCommunicateStateChangeBiListener(HsmsCommunicateStateChangeBiListener biListener) {
		return this.hsmsCommStateObserver.addBiListener(biListener);
	}

	@Override
	public boolean removeHsmsCommunicateStateChangeBiListener(HsmsCommunicateStateChangeBiListener biListener) {
		return this.hsmsCommStateObserver.removeBiListener(biListener);
	}
	
	
	public void setHsmsCommunicateState(HsmsCommunicateState state) {
		this.hsmsCommStateProp.set(state);
	}

}
