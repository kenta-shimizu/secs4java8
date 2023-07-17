package com.shimizukenta.secs.impl;

import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsMessageReceiveBiListener;
import com.shimizukenta.secs.SecsMessageReceiveListener;

public class SecsMessageReceiveQueueBiObserver extends AbstractQueueBiObserver<AbstractSecsCommunicator, SecsMessageReceiveListener, SecsMessageReceiveBiListener, SecsMessage> {

	public SecsMessageReceiveQueueBiObserver(AbstractSecsCommunicator comm) {
		super(comm);
	}

	@Override
	protected void notifyValueToListener(SecsMessageReceiveListener listener, SecsMessage value) {
		listener.received(value);
	}

	@Override
	protected void notifyValueToBiListener(
			SecsMessageReceiveBiListener biListener,
			SecsMessage value,
			AbstractSecsCommunicator communicator) {
		
		biListener.received(value, communicator);
	}

}
