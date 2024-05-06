package com.shimizukenta.secs.secs1.impl;

import com.shimizukenta.secs.SecsMessagePassThroughListener;
import com.shimizukenta.secs.impl.AbstractSecsMessagePassThroughObserverFacade;
import com.shimizukenta.secs.impl.SecsMessagePassThroughObservableImpl;
import com.shimizukenta.secs.secs1.Secs1Message;
import com.shimizukenta.secs.secs1.Secs1MessagePassThroughObservable;

public interface Secs1MessagePassThroughObservableImpl extends Secs1MessagePassThroughObservable, SecsMessagePassThroughObservableImpl {
	
	@Override
	default public boolean addTrySendSecs1MessagePassThroughListener(SecsMessagePassThroughListener<? super Secs1Message> listener) {
		return this.secs1PassThroughObserver().addTrySendSecs1MessagePassThroughListener(listener);
	};
	
	@Override
	default public boolean removeTrySendSecs1MessagePassThroughListener(SecsMessagePassThroughListener<? super Secs1Message> listener) {
		return this.secs1PassThroughObserver().removeTrySendSecs1MessagePassThroughListener(listener);
	}
	
	@Override
	default public boolean addSendedSecs1MessagePassThroughListener(SecsMessagePassThroughListener<? super Secs1Message> listener) {
		return this.secs1PassThroughObserver().addSendedSecs1MessagePassThroughListener(listener);
	}
	
	@Override
	default public boolean removeSendedSecs1MessagePassThroughListener(SecsMessagePassThroughListener<? super Secs1Message> listener) {
		return this.secs1PassThroughObserver().removeSendedSecs1MessagePassThroughListener(listener);
	}
	
	@Override
	default public boolean addReceiveSecs1MessagePassThroughListener(SecsMessagePassThroughListener<? super Secs1Message> listener) {
		return this.secs1PassThroughObserver().addReceiveSecs1MessagePassThroughListener(listener);
	}
	
	@Override
	default public boolean removeReceiveSecs1MessagePassThroughListener(SecsMessagePassThroughListener<? super Secs1Message> listener) {
		return this.secs1PassThroughObserver().removeReceiveSecs1MessagePassThroughListener(listener);
	}
	
	@Override
	default public AbstractSecsMessagePassThroughObserverFacade secsPassThroughObserver() {
		return this.secs1PassThroughObserver();
	}
	
	public AbstractSecs1MessagePassThroughObserverFacade secs1PassThroughObserver();
	
}
