package com.shimizukenta.secs.hsms.impl;

import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessagePassThroughListener;
import com.shimizukenta.secs.impl.AbstractBaseCommunicator;
import com.shimizukenta.secs.impl.AbstractQueueObserver;

public class HsmsMessagePassThroughQueueObserver extends AbstractQueueObserver<HsmsMessagePassThroughListener, HsmsMessage> {
	
	public HsmsMessagePassThroughQueueObserver(AbstractBaseCommunicator comm) {
		super(comm);
	}
	
	@Override
	protected void notifyValueToListener(HsmsMessagePassThroughListener listener, HsmsMessage value) {
		listener.passThrough(value);
	}
	
}
