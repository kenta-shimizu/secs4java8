package com.shimizukenta.secs.hsmsss.impl;

import com.shimizukenta.secs.hsms.impl.AbstractHsmsSession;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicatorConfig;

public abstract class AbstractHsmsSsSession extends AbstractHsmsSession {
	
	private final HsmsSsCommunicatorConfig config;
	
	public AbstractHsmsSsSession(HsmsSsCommunicatorConfig config) {
		
		super(config);
		this.config = config;
	}
	
	@Override
	public int deviceId() {
		return this.sessionId();
	}
	
	@Override
	public int sessionId() {
		return this.config.sessionId().intValue();
	}
	
}
