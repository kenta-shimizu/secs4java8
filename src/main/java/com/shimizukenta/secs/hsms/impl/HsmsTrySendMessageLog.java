package com.shimizukenta.secs.hsms.impl;

import java.time.LocalDateTime;

import com.shimizukenta.secs.impl.AbstractSecsTrySendMessageLog;

public class HsmsTrySendMessageLog extends AbstractSecsTrySendMessageLog {
	
	private static final long serialVersionUID = 5307779082574384425L;
	
	private static final String commonSubject = "Try-Send HSMS-Message";
	
	public HsmsTrySendMessageLog(AbstractHsmsMessage message, LocalDateTime timestamp) {
		super(message, timestamp);
	}

	public HsmsTrySendMessageLog(AbstractHsmsMessage message) {
		super(message);
	}
	
	@Override
	public String subject() {
		return commonSubject;
	}
	
}
