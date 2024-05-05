package com.shimizukenta.secs.impl;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.shimizukenta.secs.SecsCommunicatableStateChangeBiListener;
import com.shimizukenta.secs.SecsCommunicatableStateChangeListener;
import com.shimizukenta.secs.SecsCommunicateStateDetectable;

public interface SecsCommunicateStateDetectableImpl extends SecsCommunicateStateDetectable {
	
	@Override
	default public boolean isCommunicatable() {
		return this.secsCommunicateStateObserver().isCommunicatable();
	}
	
	@Override
	default public void waitUntilCommunicatable() throws InterruptedException {
		this.secsCommunicateStateObserver().waitUntilCommunicatable();
	}
	
	@Override
	default public void waitUntilCommunicatable(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.secsCommunicateStateObserver().waitUntilCommunicatable(timeout, unit);
	}
	
	@Override
	default public void waitUntilNotCommunicatable() throws InterruptedException {
		this.secsCommunicateStateObserver().waitUntilNotCommunicatable();
	}
	
	@Override
	default public void waitUntilNotCommunicatable(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.secsCommunicateStateObserver().waitUntilNotCommunicatable(timeout, unit);
	}
	
	@Override
	default public boolean addSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeListener listener) {
		return this.secsCommunicateStateObserver().addSecsCommunicatableStateChangeListener(listener);
	}
	
	@Override
	default public boolean removeSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeListener listener) {
		return this.secsCommunicateStateObserver().removeSecsCommunicatableStateChangeListener(listener);
	}
	
	@Override
	default public boolean addSecsCommunicatableStateChangeBiListener(SecsCommunicatableStateChangeBiListener biListener) {
		return this.secsCommunicateStateObserver().addSecsCommunicatableStateChangeBiListener(biListener);
	}
	
	@Override
	default public boolean removeSecsCommunicatableStateChangeBiListener(SecsCommunicatableStateChangeBiListener biListener) {
		return this.secsCommunicateStateObserver().removeSecsCommunicatableStateChangeBiListener(biListener);
	}
	
	public AbstractSecsCommunicateStateObserverFacade secsCommunicateStateObserver();
	
}
