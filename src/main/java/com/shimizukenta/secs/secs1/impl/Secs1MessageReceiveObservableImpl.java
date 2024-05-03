package com.shimizukenta.secs.secs1.impl;

import com.shimizukenta.secs.impl.AbstractSecsMessageReceiveObserverFacade;
import com.shimizukenta.secs.impl.SecsMessageReceiveObservableImpl;
import com.shimizukenta.secs.secs1.Secs1MessageReceiveBiListener;
import com.shimizukenta.secs.secs1.Secs1MessageReceiveListener;
import com.shimizukenta.secs.secs1.Secs1MessageReceiveObservable;

public interface Secs1MessageReceiveObservableImpl extends Secs1MessageReceiveObservable, SecsMessageReceiveObservableImpl {
	
	@Override
	default public boolean addSecs1MessageReceiveListener(Secs1MessageReceiveListener listener) {
		return this.secs1MessageReceiveObserver().addSecs1MessageReceiveListener(listener);
	}
	
	@Override
	default public boolean removeSecs1MessageReceiveListener(Secs1MessageReceiveListener listener) {
		return this.secs1MessageReceiveObserver().removeSecs1MessageReceiveListener(listener);
	}
	
	@Override
	default public boolean addSecs1MessageReceiveBiListener(Secs1MessageReceiveBiListener biListener) {
		return this.secs1MessageReceiveObserver().addSecs1MessageReceiveBiListener(biListener);
	}
	
	@Override
	default public boolean removeSecs1MessageReceiveBiListener(Secs1MessageReceiveBiListener biListener) {
		return this.secs1MessageReceiveObserver().removeSecs1MessageReceiveBiListener(biListener);
	}
	
	@Override
	default public  AbstractSecsMessageReceiveObserverFacade secsMessageReceiveObserver() {
		return this.secs1MessageReceiveObserver();
	}
	
	public  AbstractSecs1MessageReceiveObserverFacade secs1MessageReceiveObserver();
	
}
