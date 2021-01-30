package com.shimizukenta.secs;

import java.time.LocalDateTime;
import java.util.Objects;

public class SecsTrySendMessageLog extends AbstractSecsLog {
	
	private static final long serialVersionUID = -5404537548566127711L;
	
	private static final String commonSubject = "Try-Send SECS-Message";
	
	public SecsTrySendMessageLog(SecsMessage message, LocalDateTime timestamp) {
		super(commonSubject, timestamp, Objects.requireNonNull(message));
	}
	
	public SecsTrySendMessageLog(SecsMessage message) {
		super(commonSubject, Objects.requireNonNull(message));
	}
	
	@Override
	public String subject() {
		return commonSubject;
	}
	
}
