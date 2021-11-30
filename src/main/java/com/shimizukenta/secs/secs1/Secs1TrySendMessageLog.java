package com.shimizukenta.secs.secs1;

import java.time.LocalDateTime;

import com.shimizukenta.secs.AbstractSecsTrySendMessageLog;

public class Secs1TrySendMessageLog extends AbstractSecsTrySendMessageLog {
	
	private static final long serialVersionUID = -5052290289868782404L;
	
	private static final String commonSubject = "Try-Send SECS1-Message";
	
	public Secs1TrySendMessageLog(AbstractSecs1Message message, LocalDateTime timestamp) {
		super(message, timestamp);
	}

	public Secs1TrySendMessageLog(AbstractSecs1Message message) {
		super(message);
	}
	
	@Override
	public String subject() {
		return commonSubject;
	}
	
}
