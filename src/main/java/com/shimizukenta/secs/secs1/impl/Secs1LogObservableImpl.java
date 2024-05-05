package com.shimizukenta.secs.secs1.impl;

import com.shimizukenta.secs.SecsLogListener;
import com.shimizukenta.secs.impl.AbstractSecsLogObserverFacade;
import com.shimizukenta.secs.impl.SecsLogObservableImpl;
import com.shimizukenta.secs.secs1.Secs1LogObservable;
import com.shimizukenta.secs.secs1.Secs1MessageBlockPassThroughLog;
import com.shimizukenta.secs.secs1.Secs1MessagePassThroughLog;

public interface Secs1LogObservableImpl extends Secs1LogObservable, SecsLogObservableImpl {
	
	@Override
	default public boolean addTrySendSecs1MessagePassThroughLogListener(SecsLogListener<? super Secs1MessagePassThroughLog> listener) {
		return this.secs1LogObserver().addTrySendSecs1MessagePassThroughLogListener(listener);
	}
	
	@Override
	default public boolean removeTrySendSecs1MessagePassThroughLogListener(SecsLogListener<? super Secs1MessagePassThroughLog> listener) {
		return this.secs1LogObserver().removeTrySendSecs1MessagePassThroughLogListener(listener);
	}
	
	@Override
	default public boolean addSendedSecs1MessagePassThroughLogListener(SecsLogListener<? super Secs1MessagePassThroughLog> listener) {
		return this.secs1LogObserver().addSendedSecs1MessagePassThroughLogListener(listener);
	}
	
	@Override
	default public boolean removeSendedSecs1MessagePassThroughLogListener(SecsLogListener<? super Secs1MessagePassThroughLog> listener) {
		return this.secs1LogObserver().removeSendedSecs1MessagePassThroughLogListener(listener);
	}
	
	@Override
	default public boolean addReceiveSecs1MessagePassThroughLogListener(SecsLogListener<? super Secs1MessagePassThroughLog> listener) {
		return this.secs1LogObserver().addReceiveSecs1MessagePassThroughLogListener(listener);
	}
	
	@Override
	default public boolean removeReceiveSecs1MessagePassThroughLogListener(SecsLogListener<? super Secs1MessagePassThroughLog> listener) {
		return this.secs1LogObserver().removeReceiveSecs1MessagePassThroughLogListener(listener);
	}
	
	@Override
	default public boolean addTrySendSecs1MessageBlockPassThroughLogListener(SecsLogListener<? super Secs1MessageBlockPassThroughLog> listener) {
		return this.secs1LogObserver().addTrySendSecs1MessageBlockPassThroughLogListener(listener);
	}
	
	@Override
	default public boolean removeTrySendSecs1MessageBlockPassThroughLogListener(SecsLogListener<? super Secs1MessageBlockPassThroughLog> listener) {
		return this.secs1LogObserver().removeTrySendSecs1MessageBlockPassThroughLogListener(listener);
	}
	
	@Override
	default public boolean addSendedSecs1MessageBlockPassThroughLogListener(SecsLogListener<? super Secs1MessageBlockPassThroughLog> listener) {
		return this.secs1LogObserver().addSendedSecs1MessageBlockPassThroughLogListener(listener);
	}
	
	@Override
	default public boolean removeSendedSecs1MessageBlockPassThroughLogListener(SecsLogListener<? super Secs1MessageBlockPassThroughLog> listener) {
		return this.secs1LogObserver().removeSendedSecs1MessageBlockPassThroughLogListener(listener);
	}
	
	@Override
	default public boolean addReceiveSecs1MessageBlockPassThroughLogListener(SecsLogListener<? super Secs1MessageBlockPassThroughLog> listener) {
		return this.secs1LogObserver().addReceiveSecs1MessageBlockPassThroughLogListener(listener);
	}
	
	@Override
	default public boolean removeReceiveSecs1MessageBlockPassThroughLogListener(SecsLogListener<? super Secs1MessageBlockPassThroughLog> listener)  {
		return this.secs1LogObserver().removeReceiveSecs1MessageBlockPassThroughLogListener(listener);
	}
	
	@Override
	default public AbstractSecsLogObserverFacade secsLogObserver() {
		return this.secs1LogObserver();
	}
	
	public AbstractSecs1LogObserverFacade secs1LogObserver();
	
}
