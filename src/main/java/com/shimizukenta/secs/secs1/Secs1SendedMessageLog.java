package com.shimizukenta.secs.secs1;

import java.time.LocalDateTime;

import com.shimizukenta.secs.AbstractSecsSendedMessageLog;

public class Secs1SendedMessageLog extends AbstractSecsSendedMessageLog {
	
	private static final long serialVersionUID = -7409606143132143490L;
	
	private static final String commonSubject = "Sended SECS1-Message";
	
	public Secs1SendedMessageLog(SimpleSecs1Message message, LocalDateTime timestamp) {
		super(message, timestamp);
	}

	public Secs1SendedMessageLog(SimpleSecs1Message message) {
		super(message);
	}
	
	@Override
	public String subject() {
		return commonSubject;
	}
	
}
