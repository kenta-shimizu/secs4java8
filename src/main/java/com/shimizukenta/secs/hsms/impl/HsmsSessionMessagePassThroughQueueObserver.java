package com.shimizukenta.secs.hsms.impl;

import java.util.concurrent.Executor;

import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessagePassThroughListener;
import com.shimizukenta.secs.impl.AbstractQueueObserver;

public class HsmsSessionMessagePassThroughQueueObserver extends AbstractQueueObserver<HsmsMessagePassThroughListener, HsmsMessage> {

	public HsmsSessionMessagePassThroughQueueObserver(Executor executor) {
		super(executor);
	}

	@Override
	protected void notifyValueToListener(HsmsMessagePassThroughListener listener, HsmsMessage value) {
		listener.passThrough(value);
	}

}
