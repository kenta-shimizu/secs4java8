package com.shimizukenta.secs.hsms.impl;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.shimizukenta.secs.hsms.HsmsCommunicateState;
import com.shimizukenta.secs.hsms.HsmsCommunicateStateChangeBiListener;
import com.shimizukenta.secs.hsms.HsmsCommunicateStateChangeListener;
import com.shimizukenta.secs.hsms.HsmsCommunicateStateDetectable;
import com.shimizukenta.secs.impl.SecsCommunicateStateDetectableImpl;

public interface HsmsCommunicateStateDetectableImpl
		extends HsmsCommunicateStateDetectable, SecsCommunicateStateDetectableImpl {
	
	@Override
	default public HsmsCommunicateState getHsmsCommunicateState() {
		return this.hsmsCommunicateStateObserver().getHsmsCommunicateState();
	}
	
	@Override
	default public void waitUntilHsmsCommunicateState(HsmsCommunicateState state) throws InterruptedException {
		this.hsmsCommunicateStateObserver().waitUntilHsmsCommunicateState(state);
	}
	
	@Override
	default public void waitUntilHsmsCommunicateState(HsmsCommunicateState state, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.hsmsCommunicateStateObserver().waitUntilHsmsCommunicateState(state, timeout, unit);
	}
	
	@Override
	default public void waitUntilNotHsmsCommunicateState(HsmsCommunicateState state) throws InterruptedException {
		this.hsmsCommunicateStateObserver().waitUntilNotHsmsCommunicateState(state);
	}
	
	@Override
	default public void waitUntilNotHsmsCommunicateState(HsmsCommunicateState state, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.hsmsCommunicateStateObserver().waitUntilNotHsmsCommunicateState(state, timeout, unit);
	}
	
	@Override
	default public boolean addHsmsCommunicateStateChangeListener(HsmsCommunicateStateChangeListener listener) {
		return this.hsmsCommunicateStateObserver().addHsmsCommunicateStateChangeListener(listener);
	}
	
	@Override
	default public boolean removeHsmsCommunicateStateChangeListener(HsmsCommunicateStateChangeListener listener) {
		return this.hsmsCommunicateStateObserver().removeHsmsCommunicateStateChangeListener(listener);
	}
	
	@Override
	default public boolean addHsmsCommunicateStateChangeBiListener(HsmsCommunicateStateChangeBiListener biListener) {
		return this.hsmsCommunicateStateObserver().addHsmsCommunicateStateChangeBiListener(biListener);
	}
	
	@Override
	default public boolean removeHsmsCommunicateStateChangeBiListener(HsmsCommunicateStateChangeBiListener biListener) {
		return this.hsmsCommunicateStateObserver().removeHsmsCommunicateStateChangeBiListener(biListener);
	}
	
	@Override
	default public AbstractHsmsCommunicateStateObserverFacade secsCommunicateStateObserver() {
		return this.hsmsCommunicateStateObserver();
	}
	
	public AbstractHsmsCommunicateStateObserverFacade hsmsCommunicateStateObserver();

}
