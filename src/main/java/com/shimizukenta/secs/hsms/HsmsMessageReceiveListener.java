package com.shimizukenta.secs.hsms;

import java.util.EventListener;

public interface HsmsMessageReceiveListener extends EventListener {
	
	public void received(AbstractHsmsMessage msg);
}
