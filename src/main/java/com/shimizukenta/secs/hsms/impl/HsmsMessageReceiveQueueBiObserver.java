package com.shimizukenta.secs.hsms.impl;

import java.util.concurrent.Executor;

import com.shimizukenta.secs.hsms.HsmsGemAccessor;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessageReceiveBiListener;
import com.shimizukenta.secs.hsms.HsmsMessageReceiveListener;
import com.shimizukenta.secs.impl.AbstractQueueBiObserver;

@Deprecated
public class HsmsMessageReceiveQueueBiObserver extends AbstractQueueBiObserver<HsmsGemAccessor, HsmsMessageReceiveListener, HsmsMessageReceiveBiListener, HsmsMessage> {

	public HsmsMessageReceiveQueueBiObserver(Executor executor, HsmsGemAccessor comm) {
		super(executor, comm);
	}

	@Override
	protected void notifyValueToListener(HsmsMessageReceiveListener listener, HsmsMessage value) {
		listener.received(value);
	}

	@Override
	protected void notifyValueToBiListener(
			HsmsMessageReceiveBiListener biListener,
			HsmsMessage value,
			HsmsGemAccessor communicator) {
		
		biListener.received(value, communicator);
	}
	
}
