package com.shimizukenta.secs.hsmsss.impl;

import com.shimizukenta.secs.hsms.impl.AbstractHsmsSession;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicatorConfig;

public abstract class AbstractHsmsSsSession extends AbstractHsmsSession {
	
	private final int sessionId;
	
	public AbstractHsmsSsSession(HsmsSsCommunicatorConfig config, int sessionId) {
		super(config);
		this.sessionId = sessionId;
	}
	
	@Override
	public int deviceId() {
		return this.sessionId;
	}
	
	@Override
	public int sessionId() {
		return this.sessionId;
	}
	
}
