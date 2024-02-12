package com.shimizukenta.secs.hsms.impl;

import java.util.concurrent.Executor;

import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessageReceiveListener;
import com.shimizukenta.secs.hsms.HsmsSessionMessageReceiveBiListener;
import com.shimizukenta.secs.impl.AbstractQueueBiObserver;

public class HsmsSessionMessageReceiveQueueBiObserver extends AbstractQueueBiObserver<AbstractHsmsSession, HsmsMessageReceiveListener, HsmsSessionMessageReceiveBiListener, HsmsMessage> {

	public HsmsSessionMessageReceiveQueueBiObserver(Executor executor, AbstractHsmsSession comm) {
		super(executor, comm);
	}

	@Override
	protected void notifyValueToListener(HsmsMessageReceiveListener listener, HsmsMessage value) {
		listener.received(value);
	}

	@Override
	protected void notifyValueToBiListener(
			HsmsSessionMessageReceiveBiListener biListener,
			HsmsMessage value,
			AbstractHsmsSession communicator) {
		
		biListener.received(value, communicator);
	}
	
}
