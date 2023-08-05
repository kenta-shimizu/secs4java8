package com.shimizukenta.secs.hsmsss.impl;

import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessagePassThroughListener;
import com.shimizukenta.secs.hsmsss.HsmsSsMessagePassThroughBiListener;
import com.shimizukenta.secs.impl.AbstractQueueBiObserver;

public class HsmsSsMessagePassThroughQueueBiObserver extends AbstractQueueBiObserver<AbstractHsmsSsCommunicator, HsmsMessagePassThroughListener, HsmsSsMessagePassThroughBiListener, HsmsMessage> {

	public HsmsSsMessagePassThroughQueueBiObserver(AbstractHsmsSsCommunicator communicator) {
		super(communicator);
	}
	
	@Override
	protected void notifyValueToListener(HsmsMessagePassThroughListener listener, HsmsMessage value) {
		listener.passThrough(value);
	}
	
	@Override
	protected void notifyValueToBiListener(
			HsmsSsMessagePassThroughBiListener biListener,
			HsmsMessage value,
			AbstractHsmsSsCommunicator communicator) {
		
		biListener.passThrough(value, communicator);
	}
	
}
