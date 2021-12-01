package com.shimizukenta.secs.hsmsss;

import com.shimizukenta.secs.hsms.AbstractHsmsSession;

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
