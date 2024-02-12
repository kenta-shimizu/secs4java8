package com.shimizukenta.secs.impl;

import java.util.concurrent.Executor;

import com.shimizukenta.secs.GemAccessor;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsMessageReceiveBiListener;
import com.shimizukenta.secs.SecsMessageReceiveListener;

public class SecsMessageReceiveQueueBiObserver extends AbstractQueueBiObserver<GemAccessor, SecsMessageReceiveListener, SecsMessageReceiveBiListener, SecsMessage> {

	public SecsMessageReceiveQueueBiObserver(Executor executor, GemAccessor comm) {
		super(executor, comm);
	}

	@Override
	protected void notifyValueToListener(SecsMessageReceiveListener listener, SecsMessage value) {
		listener.received(value);
	}

	@Override
	protected void notifyValueToBiListener(
			SecsMessageReceiveBiListener biListener,
			SecsMessage value,
			GemAccessor communicator) {
		
		biListener.received(value, communicator);
	}

}
