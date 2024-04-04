package com.shimizukenta.secs.secs1.impl;

import java.time.LocalDateTime;

import com.shimizukenta.secs.impl.AbstractSecsTrySendMessageLog;
import com.shimizukenta.secs.secs1.Secs1Message;

@Deprecated
public class Secs1TrySendMessageLog extends AbstractSecsTrySendMessageLog {
	
	private static final long serialVersionUID = -5052290289868782404L;
	
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
