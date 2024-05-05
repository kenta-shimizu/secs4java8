package com.shimizukenta.secs.impl;

import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsMessagePassThroughListener;
import com.shimizukenta.secs.SecsMessagePassThroughObservable;

public interface SecsMessagePassThroughObservableImpl extends SecsMessagePassThroughObservable {

	@Override
	default public boolean addTrySendSecsMessagePassThroughListener(SecsMessagePassThroughListener<? super SecsMessage> listener) {
		return this.secsPassThroughObserver().addTrySendSecsMessagePassThroughListener(listener);
	}
	
	@Override
	default public boolean removeTrySendSecsMessagePassThroughListener(SecsMessagePassThroughListener<? super SecsMessage> listener) {
		return this.secsPassThroughObserver().removeTrySendSecsMessagePassThroughListener(listener);
	}
	
	@Override
	default public boolean addSendedSecsMessagePassThroughListener(SecsMessagePassThroughListener<? super SecsMessage> listener) {
		return this.secsPassThroughObserver().addSendedSecsMessagePassThroughListener(listener);
	}
	
	@Override
	default public boolean removeSendedSecsMessagePassThroughListener(SecsMessagePassThroughListener<? super SecsMessage> listener) {
		return this.secsPassThroughObserver().removeSendedSecsMessagePassThroughListener(listener);
	}
	
	@Override
	default public boolean addReceiveSecsMessagePassThroughListener(SecsMessagePassThroughListener<? super SecsMessage> listener) {
		return this.secsPassThroughObserver().addReceiveSecsMessagePassThroughListener(listener);
	}
	
	@Override
	default public boolean removeReceiveSecsMessagePassThroughListener(SecsMessagePassThroughListener<? super SecsMessage> listener) {
		return this.secsPassThroughObserver().removeReceiveSecsMessagePassThroughListener(listener);
	}
	
	public AbstractSecsMessagePassThroughObserverFacade secsPassThroughObserver();
	
}
