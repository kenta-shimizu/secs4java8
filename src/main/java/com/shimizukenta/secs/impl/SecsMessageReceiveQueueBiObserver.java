package com.shimizukenta.secs.impl;

import java.util.concurrent.Executor;

import com.shimizukenta.secs.SecsGemAccessor;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsMessageReceiveBiListener;
import com.shimizukenta.secs.SecsMessageReceiveListener;

@Deprecated
public class SecsMessageReceiveQueueBiObserver extends AbstractQueueBiObserver<SecsGemAccessor, SecsMessageReceiveListener, SecsMessageReceiveBiListener, SecsMessage> {

	public SecsMessageReceiveQueueBiObserver(Executor executor, SecsGemAccessor comm) {
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
			SecsGemAccessor communicator) {
		
		biListener.received(value, communicator);
	}

}
