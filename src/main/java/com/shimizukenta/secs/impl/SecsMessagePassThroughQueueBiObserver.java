package com.shimizukenta.secs.impl;

import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsMessagePassThroughBiListener;
import com.shimizukenta.secs.SecsMessagePassThroughListener;

public class SecsMessagePassThroughQueueBiObserver extends AbstractQueueBiObserver<AbstractSecsCommunicator, SecsMessagePassThroughListener, SecsMessagePassThroughBiListener, SecsMessage> {

	public SecsMessagePassThroughQueueBiObserver(AbstractSecsCommunicator comm) {
		super(comm);
	}

	@Override
	protected void notifyValueToListener(SecsMessagePassThroughListener listener, SecsMessage value) {
		listener.passThrough(value);
	}

	@Override
	protected void notifyValueToBiListener(
			SecsMessagePassThroughBiListener biListener,
			SecsMessage value,
			AbstractSecsCommunicator communicator) {
		
		biListener.passThrough(value, communicator);
	}
	
}
