package com.shimizukenta.secs.hsms.impl;

import com.shimizukenta.secs.SecsLog;
import com.shimizukenta.secs.SecsLogListener;
import com.shimizukenta.secs.SecsMessagePassThroughLog;
import com.shimizukenta.secs.SecsThrowableLog;
import com.shimizukenta.secs.hsms.HsmsChannelConnectionLog;
import com.shimizukenta.secs.hsms.HsmsLogObservable;
import com.shimizukenta.secs.hsms.HsmsMessagePassThroughLog;
import com.shimizukenta.secs.hsms.HsmsSessionCommunicateStateLog;

public interface HsmsLogObservableImpl extends HsmsLogObservable {
	
	public AbstractHsmsLogObserverFacade logObserver();
	
	@Override
	default public boolean addSecsLogListener(SecsLogListener<? super SecsLog> listener) {
		return this.logObserver().addSecsLogListener(listener);
	}

	@Override
	default public boolean removeSecsLogListener(SecsLogListener<? super SecsLog> listener) {
		return this.logObserver().removeSecsLogListener(listener);
	}

	@Override
	default public boolean addSecsThrowableLogListener(SecsLogListener<? super SecsThrowableLog> listener) {
		return this.logObserver().addSecsThrowableLogListener(listener);
	}

	@Override
	default public boolean removeSecsThrowableLogListener(SecsLogListener<? super SecsThrowableLog> listener) {
		return this.logObserver().removeSecsThrowableLogListener(listener);
	}

	@Override
	default public boolean addTrySendSecsMessagePassThroughLogListener(SecsLogListener<? super SecsMessagePassThroughLog> listener) {
		return this.logObserver().addTrySendSecsMessagePassThroughLogListener(listener);
	}

	@Override
	default public boolean removeTrySendSecsMessagePassThroughLogListener(SecsLogListener<? super SecsMessagePassThroughLog> listener) {
		return this.logObserver().removeTrySendSecsMessagePassThroughLogListener(listener);
	}

	@Override
	default public boolean addSendedSecsMessagePassThroughLogListener(SecsLogListener<? super SecsMessagePassThroughLog> listener) {
		return this.logObserver().addSendedSecsMessagePassThroughLogListener(listener);
	}

	@Override
	default public boolean removeSendedSecsMessagePassThroughLogListener(SecsLogListener<? super SecsMessagePassThroughLog> listener) {
		return this.logObserver().removeSendedSecsMessagePassThroughLogListener(listener);
	}

	@Override
	default public boolean addReceiveSecsMessagePassThroughLogListener(SecsLogListener<? super SecsMessagePassThroughLog> listener) {
		return this.logObserver().addReceiveSecsMessagePassThroughLogListener(listener);
	}

	@Override
	default public boolean removeReceiveSecsMessagePassThroughLogListener(SecsLogListener<? super SecsMessagePassThroughLog> listener) {
		return this.logObserver().removeReceiveSecsMessagePassThroughLogListener(listener);
	}
	
	@Override
	default public boolean addTrySendHsmsMessagePassThroughLogListener(SecsLogListener<? super HsmsMessagePassThroughLog> listener) {
		return this.logObserver().addTrySendHsmsMessagePassThroughLogListener(listener);
	}

	@Override
	default public boolean removeTrySendHsmsMessagePassThroughLogListener(SecsLogListener<? super HsmsMessagePassThroughLog> listener) {
		return this.logObserver().removeTrySendHsmsMessagePassThroughLogListener(listener);
	}

	@Override
	default public boolean addSendedHsmsMessagePassThroughLogListener(SecsLogListener<? super HsmsMessagePassThroughLog> listener) {
		return this.logObserver().addSendedHsmsMessagePassThroughLogListener(listener);
	}

	@Override
	default public boolean removeSendedHsmsMessagePassThroughLogListener(SecsLogListener<? super HsmsMessagePassThroughLog> listener) {
		return this.logObserver().removeSendedHsmsMessagePassThroughLogListener(listener);
	}

	@Override
	default public boolean addReceiveHsmsMessagePassThroughLogListener(SecsLogListener<? super HsmsMessagePassThroughLog> listener) {
		return this.logObserver().addReceiveHsmsMessagePassThroughLogListener(listener);
	}

	@Override
	default public boolean removeReceiveHsmsMessagePassThroughLogListener(SecsLogListener<? super HsmsMessagePassThroughLog> listener) {
		return this.logObserver().removeReceiveHsmsMessagePassThroughLogListener(listener);
	}
	
	@Override
	default public boolean addHsmsChannelConnectionLogListener(SecsLogListener<? super HsmsChannelConnectionLog> listener) {
		return this.logObserver().addHsmsChannelConnectionLogListener(listener);
	}
	
	@Override
	default public boolean removeHsmsChannelConnectionLogListener(SecsLogListener<? super HsmsChannelConnectionLog> listener) {
		return this.logObserver().removeHsmsChannelConnectionLogListener(listener);
	}
	
	@Override
	default public boolean addHsmsSessionCommunicateStateLogListener(SecsLogListener<? super HsmsSessionCommunicateStateLog> listener) {
		return this.logObserver().addHsmsSessionCommunicateStateLogListener(listener);
	}
	
	@Override
	default public boolean removeHsmsSessionCommunicateStateLogListener(SecsLogListener<? super HsmsSessionCommunicateStateLog> listener) {
		return this.logObserver().removeHsmsSessionCommunicateStateLogListener(listener);
	}

}
