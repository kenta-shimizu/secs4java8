package com.shimizukenta.secs.impl;

import java.time.LocalDateTime;

public abstract class AbstractSecsCommunicateStateChangeLog extends AbstractSecsLog {
	
	private static final long serialVersionUID = 9068377101784351432L;
	
	public AbstractSecsCommunicateStateChangeLog(CharSequence subject, LocalDateTime timestamp, Object value) {
		super(subject, timestamp, value);
	}
	
	public AbstractSecsCommunicateStateChangeLog(CharSequence subject, Object value) {
		super(subject, value);
	}
}
