package com.shimizukenta.secs.secs1.impl;

import com.shimizukenta.secs.impl.AbstractSecsMessage;
import com.shimizukenta.secs.secs1.Secs1Message;

public abstract class AbstractSecs1Message extends AbstractSecsMessage implements Secs1Message {
	
	private static final long serialVersionUID = -7944936333752743698L;
	
	public AbstractSecs1Message() {
		super();
	}
	
	@Override
	public int sessionId() {
		return this.deviceId();
	}
	
}
