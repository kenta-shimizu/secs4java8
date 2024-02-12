package com.shimizukenta.secs.secs1.impl;

import java.util.concurrent.Executor;

import com.shimizukenta.secs.impl.AbstractQueueBiObserver;
import com.shimizukenta.secs.secs1.Secs1Message;
import com.shimizukenta.secs.secs1.Secs1MessageReceiveBiListener;
import com.shimizukenta.secs.secs1.Secs1MessageReceiveListener;

public class Secs1MessageReceiveQueueBiObserver extends AbstractQueueBiObserver<AbstractSecs1Communicator, Secs1MessageReceiveListener, Secs1MessageReceiveBiListener, Secs1Message> {

	public Secs1MessageReceiveQueueBiObserver(Executor executor, AbstractSecs1Communicator comm) {
		super(executor, comm);
	}
	
	@Override
	protected void notifyValueToListener(Secs1MessageReceiveListener listener, Secs1Message value) {
		listener.received(value);
	}

	@Override
	protected void notifyValueToBiListener(
			Secs1MessageReceiveBiListener biListener,
			Secs1Message value,
			AbstractSecs1Communicator communicator) {
		
		biListener.received(value, communicator);
	}
	
}
