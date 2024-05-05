package com.shimizukenta.secs.hsms.impl;

import com.shimizukenta.secs.hsms.HsmsMessageReceiveBiListener;
import com.shimizukenta.secs.hsms.HsmsMessageReceiveListener;
import com.shimizukenta.secs.hsms.HsmsMessageReceiveObservable;
import com.shimizukenta.secs.impl.AbstractSecsMessageReceiveObserverFacade;
import com.shimizukenta.secs.impl.SecsMessageReceiveObservableImpl;

public interface HsmsMessageReceiveObservableImpl
		extends HsmsMessageReceiveObservable, SecsMessageReceiveObservableImpl {
	
	@Override
	default public boolean addHsmsMessageReceiveListener(HsmsMessageReceiveListener listener) {
		return this.hsmsMessageReceiveObserver().addHsmsMessageReceiveListener(listener);
	}
	
	@Override
	default public boolean removeHsmsMessageReceiveListener(HsmsMessageReceiveListener listener) {
		return this.hsmsMessageReceiveObserver().removeHsmsMessageReceiveListener(listener);
	}
	
	@Override
	default public boolean addHsmsMessageReceiveBiListener(HsmsMessageReceiveBiListener biListener) {
		return this.hsmsMessageReceiveObserver().addHsmsMessageReceiveBiListener(biListener);
	}
	
	@Override
	default public boolean removeHsmsMessageReceiveBiListener(HsmsMessageReceiveBiListener biListener) {
		return this.hsmsMessageReceiveObserver().removeHsmsMessageReceiveBiListener(biListener);
	}
	
	@Override 
	default public AbstractSecsMessageReceiveObserverFacade secsMessageReceiveObserver() {
		return this.hsmsMessageReceiveObserver();
	}
	
	public AbstractHsmsMessageReceiveObserverFacade hsmsMessageReceiveObserver();
	
}
