package com.shimizukenta.secs.secs1.impl;

import com.shimizukenta.secs.impl.AbstractQueueBiObserver;
import com.shimizukenta.secs.secs1.Secs1Message;
import com.shimizukenta.secs.secs1.Secs1MessagePassThroughBiListener;
import com.shimizukenta.secs.secs1.Secs1MessagePassThroughListener;

public class Secs1MessagePassThroughQueueBiObserver extends AbstractQueueBiObserver<AbstractSecs1Communicator, Secs1MessagePassThroughListener, Secs1MessagePassThroughBiListener, Secs1Message> {

	public Secs1MessagePassThroughQueueBiObserver(AbstractSecs1Communicator comm) {
		super(comm);
	}
	
	@Override
	protected void notifyValueToListener(Secs1MessagePassThroughListener listener, Secs1Message value) {
		listener.passThrough(value);
	}
	
	@Override
	protected void notifyValueToBiListener(
			Secs1MessagePassThroughBiListener biListener,
			Secs1Message value,
			AbstractSecs1Communicator communicator) {
		
		biListener.passThrough(value, communicator);
	}

}
