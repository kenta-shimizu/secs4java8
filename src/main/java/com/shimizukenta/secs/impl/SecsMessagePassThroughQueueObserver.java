package com.shimizukenta.secs.impl;

import java.util.concurrent.Executor;

import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsMessagePassThroughListener;

@Deprecated
public class SecsMessagePassThroughQueueObserver extends AbstractQueueObserver<SecsMessagePassThroughListener＜SecsMessage>, SecsMessage> {
	
	public SecsMessagePassThroughQueueObserver(Executor executor) {
		super(executor);
	}
	
	@Override
	protected void notifyValueToListener(SecsMessagePassThroughListener listener, SecsMessage value) {
		listener.passThrough(value);
	}
	
}
