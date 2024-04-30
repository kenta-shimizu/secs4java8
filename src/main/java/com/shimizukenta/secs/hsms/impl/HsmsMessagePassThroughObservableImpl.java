package com.shimizukenta.secs.hsms.impl;

import com.shimizukenta.secs.SecsMessagePassThroughListener;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessagePassThroughObservable;
import com.shimizukenta.secs.impl.AbstractSecsMessagePassThroughObserverFacade;
import com.shimizukenta.secs.impl.SecsMessagePassThroughObservableImpl;

public interface HsmsMessagePassThroughObservableImpl extends HsmsMessagePassThroughObservable, SecsMessagePassThroughObservableImpl {
	
	@Override
	default public boolean addTrySendHsmsMessagePassThroughListener(SecsMessagePassThroughListener<? super HsmsMessage> listener) {
		return this.hsmsPassThroughObserver().addTrySendHsmsMessagePassThroughListener(listener);
	}
	
	@Override
	default public boolean removeTrySendHsmsMessagePassThroughListener(SecsMessagePassThroughListener<? super HsmsMessage> listener) {
		return this.hsmsPassThroughObserver().removeTrySendHsmsMessagePassThroughListener(listener);
	}
	
	@Override
	default public boolean addSendedHsmsMessagePassThroughListener(SecsMessagePassThroughListener<? super HsmsMessage> listener) {
		return this.hsmsPassThroughObserver().addSendedHsmsMessagePassThroughListener(listener);
	}
	
	@Override
	default public boolean removeSendedHsmsMessagePassThroughListener(SecsMessagePassThroughListener<? super HsmsMessage> listener) {
		return this.hsmsPassThroughObserver().removeSendedHsmsMessagePassThroughListener(listener);
	}
	
	@Override
	default public boolean addReceiveHsmsMessagePassThroughListener(SecsMessagePassThroughListener<? super HsmsMessage> listener) {
		return this.hsmsPassThroughObserver().addReceiveHsmsMessagePassThroughListener(listener);
	}
	
	@Override
	default public boolean removeReceiveHsmsMessagePassThroughListener(SecsMessagePassThroughListener<? super HsmsMessage> listener) {
		return this.hsmsPassThroughObserver().removeReceiveHsmsMessagePassThroughListener(listener);
	}
	
	@Override
	default AbstractSecsMessagePassThroughObserverFacade secsPassThroughObserver() {
		return this.hsmsPassThroughObserver();
	}
	
	public AbstractHsmsMessagePassThroughObserverFacade hsmsPassThroughObserver();
	
}
