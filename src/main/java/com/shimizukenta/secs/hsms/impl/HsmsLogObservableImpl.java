package com.shimizukenta.secs.hsms.impl;

import com.shimizukenta.secs.SecsLogListener;
import com.shimizukenta.secs.hsms.HsmsChannelConnectionLog;
import com.shimizukenta.secs.hsms.HsmsLogObservable;
import com.shimizukenta.secs.hsms.HsmsMessagePassThroughLog;
import com.shimizukenta.secs.hsms.HsmsSessionCommunicateStateLog;
import com.shimizukenta.secs.impl.AbstractSecsLogObserverFacade;
import com.shimizukenta.secs.impl.SecsLogObservableImpl;

public interface HsmsLogObservableImpl extends HsmsLogObservable, SecsLogObservableImpl{
	
	@Override
	default public boolean addTrySendHsmsMessagePassThroughLogListener(SecsLogListener<? super HsmsMessagePassThroughLog> listener) {
		return this.hsmsLogObserver().addTrySendHsmsMessagePassThroughLogListener(listener);
	}

	@Override
	default public boolean removeTrySendHsmsMessagePassThroughLogListener(SecsLogListener<? super HsmsMessagePassThroughLog> listener) {
		return this.hsmsLogObserver().removeTrySendHsmsMessagePassThroughLogListener(listener);
	}

	@Override
	default public boolean addSendedHsmsMessagePassThroughLogListener(SecsLogListener<? super HsmsMessagePassThroughLog> listener) {
		return this.hsmsLogObserver().addSendedHsmsMessagePassThroughLogListener(listener);
	}

	@Override
	default public boolean removeSendedHsmsMessagePassThroughLogListener(SecsLogListener<? super HsmsMessagePassThroughLog> listener) {
		return this.hsmsLogObserver().removeSendedHsmsMessagePassThroughLogListener(listener);
	}

	@Override
	default public boolean addReceiveHsmsMessagePassThroughLogListener(SecsLogListener<? super HsmsMessagePassThroughLog> listener) {
		return this.hsmsLogObserver().addReceiveHsmsMessagePassThroughLogListener(listener);
	}

	@Override
	default public boolean removeReceiveHsmsMessagePassThroughLogListener(SecsLogListener<? super HsmsMessagePassThroughLog> listener) {
		return this.hsmsLogObserver().removeReceiveHsmsMessagePassThroughLogListener(listener);
	}
	
	@Override
	default public boolean addHsmsChannelConnectionLogListener(SecsLogListener<? super HsmsChannelConnectionLog> listener) {
		return this.hsmsLogObserver().addHsmsChannelConnectionLogListener(listener);
	}
	
	@Override
	default public boolean removeHsmsChannelConnectionLogListener(SecsLogListener<? super HsmsChannelConnectionLog> listener) {
		return this.hsmsLogObserver().removeHsmsChannelConnectionLogListener(listener);
	}
	
	@Override
	default public boolean addHsmsSessionCommunicateStateLogListener(SecsLogListener<? super HsmsSessionCommunicateStateLog> listener) {
		return this.hsmsLogObserver().addHsmsSessionCommunicateStateLogListener(listener);
	}
	
	@Override
	default public boolean removeHsmsSessionCommunicateStateLogListener(SecsLogListener<? super HsmsSessionCommunicateStateLog> listener) {
		return this.hsmsLogObserver().removeHsmsSessionCommunicateStateLogListener(listener);
	}
	
	@Override
	default public AbstractSecsLogObserverFacade secsLogObserver() {
		return this.hsmsLogObserver();
	}
	
	public AbstractHsmsLogObserverFacade hsmsLogObserver();
	
}
