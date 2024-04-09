package com.shimizukenta.secs.hsms.impl;

import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsMessagePassThroughListener;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessagePassThroughObservable;

public interface HsmsMessagePassThroughObservableImpl extends HsmsMessagePassThroughObservable {
	
	@Override
	default public boolean addTrySendSecsMessagePassThroughListener(SecsMessagePassThroughListener<? super SecsMessage> listener) {
		return this.passThroughObserver().addTrySendSecsMessagePassThroughListener(listener);
	}
	
	@Override
	default public boolean removeTrySendSecsMessagePassThroughListener(SecsMessagePassThroughListener<? super SecsMessage> listener) {
		return this.passThroughObserver().removeTrySendSecsMessagePassThroughListener(listener);
	}
	
	@Override
	default public boolean addSendedSecsMessagePassThroughListener(SecsMessagePassThroughListener<? super SecsMessage> listener) {
		return this.passThroughObserver().addSendedSecsMessagePassThroughListener(listener);
	}
	
	@Override
	default public boolean removeSendedSecsMessagePassThroughListener(SecsMessagePassThroughListener<? super SecsMessage> listener) {
		return this.passThroughObserver().removeSendedSecsMessagePassThroughListener(listener);
	}
	
	@Override
	default public boolean addReceiveSecsMessagePassThroughListener(SecsMessagePassThroughListener<? super SecsMessage> listener) {
		return this.passThroughObserver().addReceiveSecsMessagePassThroughListener(listener);
	}
	
	@Override
	default public boolean removeReceiveSecsMessagePassThroughListener(SecsMessagePassThroughListener<? super SecsMessage> listener) {
		return this.passThroughObserver().removeReceiveSecsMessagePassThroughListener(listener);
	}
	
	@Override
	default public boolean addTrySendHsmsMessagePassThroughListener(SecsMessagePassThroughListener<? super HsmsMessage> listener) {
		return this.passThroughObserver().addTrySendHsmsMessagePassThroughListener(listener);
	}
	
	@Override
	default public boolean removeTrySendHsmsMessagePassThroughListener(SecsMessagePassThroughListener<? super HsmsMessage> listener) {
		return this.passThroughObserver().removeTrySendHsmsMessagePassThroughListener(listener);
	}
	
	@Override
	default public boolean addSendedHsmsMessagePassThroughListener(SecsMessagePassThroughListener<? super HsmsMessage> listener) {
		return this.passThroughObserver().addSendedHsmsMessagePassThroughListener(listener);
	}
	
	@Override
	default public boolean removeSendedHsmsMessagePassThroughListener(SecsMessagePassThroughListener<? super HsmsMessage> listener) {
		return this.passThroughObserver().removeSendedHsmsMessagePassThroughListener(listener);
	}
	
	@Override
	default public boolean addReceiveHsmsMessagePassThroughListener(SecsMessagePassThroughListener<? super HsmsMessage> listener) {
		return this.passThroughObserver().addReceiveHsmsMessagePassThroughListener(listener);
	}
	
	@Override
	default public boolean removeReceiveHsmsMessagePassThroughListener(SecsMessagePassThroughListener<? super HsmsMessage> listener) {
		return this.passThroughObserver().removeReceiveHsmsMessagePassThroughListener(listener);
	}
	
	public AbstractHsmsMessagePassThroughObserverFacade passThroughObserver();
	
}
