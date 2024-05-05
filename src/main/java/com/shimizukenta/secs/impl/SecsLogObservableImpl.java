package com.shimizukenta.secs.impl;

import com.shimizukenta.secs.SecsLog;
import com.shimizukenta.secs.SecsLogListener;
import com.shimizukenta.secs.SecsLogObservable;
import com.shimizukenta.secs.SecsMessagePassThroughLog;
import com.shimizukenta.secs.SecsThrowableLog;

public interface SecsLogObservableImpl extends SecsLogObservable {
	
	@Override
	default public boolean addSecsLogListener(SecsLogListener<? super SecsLog> listener) {
		return this.secsLogObserver().addSecsLogListener(listener);
	}

	@Override
	default public boolean removeSecsLogListener(SecsLogListener<? super SecsLog> listener) {
		return this.secsLogObserver().removeSecsLogListener(listener);
	}

	@Override
	default public boolean addSecsThrowableLogListener(SecsLogListener<? super SecsThrowableLog> listener) {
		return this.secsLogObserver().addSecsThrowableLogListener(listener);
	}

	@Override
	default public boolean removeSecsThrowableLogListener(SecsLogListener<? super SecsThrowableLog> listener) {
		return this.secsLogObserver().removeSecsThrowableLogListener(listener);
	}

	@Override
	default public boolean addTrySendSecsMessagePassThroughLogListener(SecsLogListener<? super SecsMessagePassThroughLog> listener) {
		return this.secsLogObserver().addTrySendSecsMessagePassThroughLogListener(listener);
	}

	@Override
	default public boolean removeTrySendSecsMessagePassThroughLogListener(SecsLogListener<? super SecsMessagePassThroughLog> listener) {
		return this.secsLogObserver().removeTrySendSecsMessagePassThroughLogListener(listener);
	}

	@Override
	default public boolean addSendedSecsMessagePassThroughLogListener(SecsLogListener<? super SecsMessagePassThroughLog> listener) {
		return this.secsLogObserver().addSendedSecsMessagePassThroughLogListener(listener);
	}

	@Override
	default public boolean removeSendedSecsMessagePassThroughLogListener(SecsLogListener<? super SecsMessagePassThroughLog> listener) {
		return this.secsLogObserver().removeSendedSecsMessagePassThroughLogListener(listener);
	}

	@Override
	default public boolean addReceiveSecsMessagePassThroughLogListener(SecsLogListener<? super SecsMessagePassThroughLog> listener) {
		return this.secsLogObserver().addReceiveSecsMessagePassThroughLogListener(listener);
	}

	@Override
	default public boolean removeReceiveSecsMessagePassThroughLogListener(SecsLogListener<? super SecsMessagePassThroughLog> listener) {
		return this.secsLogObserver().removeReceiveSecsMessagePassThroughLogListener(listener);
	}
	
	public AbstractSecsLogObserverFacade secsLogObserver();

}
