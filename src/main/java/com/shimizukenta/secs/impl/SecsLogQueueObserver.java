package com.shimizukenta.secs.impl;

import com.shimizukenta.secs.SecsLog;
import com.shimizukenta.secs.SecsLogListener;

public class SecsLogQueueObserver extends AbstractQueueObserver<SecsLogListener, SecsLog> {
	
	public SecsLogQueueObserver(AbstractBaseCommunicator comm) {
		super(comm);
	}
	
	@Override
	protected void notifyValueToListener(SecsLogListener listener, SecsLog value) {
		listener.received(value);
	}
}
