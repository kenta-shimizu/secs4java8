package com.shimizukenta.secs.secs1;

import java.time.LocalDateTime;

import com.shimizukenta.secs.AbstractSecsReceiveMessageLog;

public class Secs1ReceiveMessageLog extends AbstractSecsReceiveMessageLog {
	
	private static final long serialVersionUID = 4047242282742361051L;
	
	private static final String commonSubject = "Receive SECS1-Message";
	
	public Secs1ReceiveMessageLog(AbstractSecs1Message message, LocalDateTime timestamp) {
		super(message, timestamp);
	}

	public Secs1ReceiveMessageLog(AbstractSecs1Message message) {
		super(message);
	}
	
	@Override
	public String subject() {
		return commonSubject;
	}
	
}
