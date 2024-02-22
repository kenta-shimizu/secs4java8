package com.shimizukenta.secs.secs1.impl;

import java.util.concurrent.Executor;

import com.shimizukenta.secs.impl.AbstractQueueObserver;
import com.shimizukenta.secs.secs1.Secs1Message;
import com.shimizukenta.secs.secs1.Secs1MessagePassThroughListener;

public class Secs1MessagePassThroughQueueObserver extends AbstractQueueObserver<Secs1MessagePassThroughListener, Secs1Message> {

	public Secs1MessagePassThroughQueueObserver(Executor executor) {
		super(executor);
	}
	
	@Override
	protected void notifyValueToListener(Secs1MessagePassThroughListener listener, Secs1Message value) {
		listener.passThrough(value);
	}
	
}
