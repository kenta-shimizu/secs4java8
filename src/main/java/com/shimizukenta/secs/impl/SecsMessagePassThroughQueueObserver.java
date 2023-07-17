package com.shimizukenta.secs.impl;

import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsMessagePassThroughListener;

public class SecsMessagePassThroughQueueObserver extends AbstractQueueObserver<SecsMessagePassThroughListener, SecsMessage> {

	public SecsMessagePassThroughQueueObserver(AbstractBaseCommunicator comm) {
		super(comm);
	}

	@Override
	protected void notifyValueToListener(SecsMessagePassThroughListener listener, SecsMessage value) {
		listener.passThrough(value);
	}

}
