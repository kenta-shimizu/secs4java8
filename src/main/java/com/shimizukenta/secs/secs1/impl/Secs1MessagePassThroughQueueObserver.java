package com.shimizukenta.secs.secs1.impl;

import com.shimizukenta.secs.impl.AbstractBaseCommunicator;
import com.shimizukenta.secs.impl.AbstractQueueObserver;
import com.shimizukenta.secs.secs1.Secs1Message;
import com.shimizukenta.secs.secs1.Secs1MessagePassThroughListener;

public class Secs1MessagePassThroughQueueObserver extends AbstractQueueObserver<Secs1MessagePassThroughListener, Secs1Message> {

	public Secs1MessagePassThroughQueueObserver(AbstractBaseCommunicator comm) {
		super(comm);
	}
	
	@Override
	protected void notifyValueToListener(Secs1MessagePassThroughListener listener, Secs1Message value) {
		listener.passThrough(value);
	}

}
