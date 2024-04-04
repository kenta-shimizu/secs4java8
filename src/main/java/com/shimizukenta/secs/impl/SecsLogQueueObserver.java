package com.shimizukenta.secs.impl;

import java.util.concurrent.Executor;

import com.shimizukenta.secs.SecsLog;
import com.shimizukenta.secs.SecsLogListener;

@Deprecated
public class SecsLogQueueObserver extends AbstractQueueObserver<SecsLogListener, SecsLog> {
	
	public SecsLogQueueObserver(Executor executor) {
		super(executor);
	}
	
	@Override
	protected void notifyValueToListener(SecsLogListener listener, SecsLog value) {
		listener.received(value);
	}
}
