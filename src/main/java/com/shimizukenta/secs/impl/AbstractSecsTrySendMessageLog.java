package com.shimizukenta.secs.impl;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsTrySendMessageLog;

@Deprecated
public abstract class AbstractSecsTrySendMessageLog extends AbstractSecsLog implements SecsTrySendMessageLog {
	
	private static final long serialVersionUID = -5404537548566127711L;
	
	private static final String commonSubject = "Try-Send SECS-Message";
	
	private final SecsMessage msg;
	
	public AbstractSecsTrySendMessageLog(SecsMessage message, LocalDateTime timestamp) {
		super(commonSubject, timestamp, Objects.requireNonNull(message));
		this.msg = message;
	}
	
	public AbstractSecsTrySendMessageLog(SecsMessage message) {
		super(commonSubject, Objects.requireNonNull(message));
		this.msg = message;
	}
	
	@Override
	public String subject() {
		return commonSubject;
	}
	
}
