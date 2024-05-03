package com.shimizukenta.secs.impl;

import com.shimizukenta.secs.SecsMessageReceiveBiListener;
import com.shimizukenta.secs.SecsMessageReceiveListener;
import com.shimizukenta.secs.SecsMessageReceiveObservable;

public interface SecsMessageReceiveObservableImpl extends SecsMessageReceiveObservable {
	
	@Override
	default public boolean addSecsMessageReceiveListener(SecsMessageReceiveListener listener) {
		return this.secsMessageReceiveObserver().addSecsMessageReceiveListener(listener);
	}
	
	@Override
	default public boolean removeSecsMessageReceiveListener(SecsMessageReceiveListener listener) {
		return this.secsMessageReceiveObserver().removeSecsMessageReceiveListener(listener);
	}
	
	@Override
	default public boolean addSecsMessageReceiveBiListener(SecsMessageReceiveBiListener biListener) {
		return this.secsMessageReceiveObserver().addSecsMessageReceiveBiListener(biListener);
	}
	
	@Override
	default public boolean removeSecsMessageReceiveBiListener(SecsMessageReceiveBiListener biListener) {
		return this.secsMessageReceiveObserver().removeSecsMessageReceiveBiListener(biListener);
	}
	
	
	public AbstractSecsMessageReceiveObserverFacade secsMessageReceiveObserver();
	
}
