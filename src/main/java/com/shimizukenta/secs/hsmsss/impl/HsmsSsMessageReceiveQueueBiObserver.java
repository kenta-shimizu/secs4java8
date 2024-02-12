package com.shimizukenta.secs.hsmsss.impl;

import java.util.concurrent.Executor;

import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessageReceiveListener;
import com.shimizukenta.secs.hsmsss.HsmsSsMessageReceiveBiListener;
import com.shimizukenta.secs.impl.AbstractQueueBiObserver;

public class HsmsSsMessageReceiveQueueBiObserver extends AbstractQueueBiObserver<AbstractHsmsSsCommunicator, HsmsMessageReceiveListener, HsmsSsMessageReceiveBiListener, HsmsMessage> {

	public HsmsSsMessageReceiveQueueBiObserver(Executor executor, AbstractHsmsSsCommunicator comm) {
		super(executor, comm);
	}

	@Override
	protected void notifyValueToListener(HsmsMessageReceiveListener listener, HsmsMessage value) {
		listener.received(value);
	}

	@Override
	protected void notifyValueToBiListener(
			HsmsSsMessageReceiveBiListener biListener,
			HsmsMessage value,
			AbstractHsmsSsCommunicator communicator) {
		
		biListener.received(value, communicator);
	}
	
}
