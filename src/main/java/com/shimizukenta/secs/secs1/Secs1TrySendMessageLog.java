package com.shimizukenta.secs.secs1;

import java.time.LocalDateTime;

import com.shimizukenta.secs.AbstractSecsTrySendMessageLog;

public class Secs1TrySendMessageLog extends AbstractSecsTrySendMessageLog {
	
	private static final long serialVersionUID = 1353372467201283677L;
	
	private static final String commonSubject = "Try-Send SECS1-Message";
	
	public Secs1TrySendMessageLog(Secs1Message message, LocalDateTime timestamp) {
		super(message, timestamp);
	}

	public Secs1TrySendMessageLog(Secs1Message message) {
		super(message);
	}
	
	@Override
	public String subject() {
		return commonSubject;
	}
	
}
