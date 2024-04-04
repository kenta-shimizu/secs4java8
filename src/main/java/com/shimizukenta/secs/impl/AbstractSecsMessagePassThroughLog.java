package com.shimizukenta.secs.impl;

import java.util.Objects;

import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsMessagePassThroughLog;

public abstract class AbstractSecsMessagePassThroughLog extends AbstractSecsLog implements SecsMessagePassThroughLog {
	
	private static final long serialVersionUID = 8274713752251989454L;
	
	private final SecsMessage msg;
	
	public AbstractSecsMessagePassThroughLog(CharSequence subject, SecsMessage message) {
		super(subject, Objects.requireNonNull(message));
		this.msg = message;
	}
	
	@Override
	public SecsMessage getSecsMessage() {
		return this.msg;
	}

}
