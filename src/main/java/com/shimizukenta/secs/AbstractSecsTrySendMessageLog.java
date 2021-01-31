package com.shimizukenta.secs;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class AbstractSecsTrySendMessageLog extends AbstractSecsLog {
	
	private static final long serialVersionUID = -5404537548566127711L;
	
	private static final String commonSubject = "Try-Send SECS-Message";
	
	public AbstractSecsTrySendMessageLog(SecsMessage message, LocalDateTime timestamp) {
		super(commonSubject, timestamp, Objects.requireNonNull(message));
	}
	
	public AbstractSecsTrySendMessageLog(SecsMessage message) {
		super(commonSubject, Objects.requireNonNull(message));
	}
	
	@Override
	public String subject() {
		return commonSubject;
	}
	
}
