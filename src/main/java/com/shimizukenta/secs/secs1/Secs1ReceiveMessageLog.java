package com.shimizukenta.secs.secs1;

import java.time.LocalDateTime;

import com.shimizukenta.secs.SecsReceiveMessageLog;

public class Secs1ReceiveMessageLog extends SecsReceiveMessageLog {
	
	private static final long serialVersionUID = -9039078309676624950L;
	
	private static final String commonSubject = "Receive SECS1-Message";
	
	public Secs1ReceiveMessageLog(Secs1Message message, LocalDateTime timestamp) {
		super(message, timestamp);
	}

	public Secs1ReceiveMessageLog(Secs1Message message) {
		super(message);
	}
	
	@Override
	public String subject() {
		return commonSubject;
	}
	
}
