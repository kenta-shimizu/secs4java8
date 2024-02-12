package com.shimizukenta.secs.impl;

import java.util.concurrent.Executor;

import com.shimizukenta.secs.GemAccessor;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsMessagePassThroughBiListener;
import com.shimizukenta.secs.SecsMessagePassThroughListener;

public class SecsMessagePassThroughQueueBiObserver extends AbstractQueueBiObserver<GemAccessor, SecsMessagePassThroughListener, SecsMessagePassThroughBiListener, SecsMessage> {

	public SecsMessagePassThroughQueueBiObserver(Executor executor, GemAccessor communicator) {
		super(executor, communicator);
	}

	@Override
	protected void notifyValueToListener(SecsMessagePassThroughListener listener, SecsMessage value) {
		listener.passThrough(value);
	}

	@Override
	protected void notifyValueToBiListener(
			SecsMessagePassThroughBiListener biListener,
			SecsMessage value,
			GemAccessor communicator) {
		
		biListener.passThrough(value, communicator);
	}
	
}
