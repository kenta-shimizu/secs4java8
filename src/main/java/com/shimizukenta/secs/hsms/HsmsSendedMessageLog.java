package com.shimizukenta.secs.hsms;

import java.time.LocalDateTime;

import com.shimizukenta.secs.AbstractSecsSendedMessageLog;

public class HsmsSendedMessageLog extends AbstractSecsSendedMessageLog {
	
	private static final long serialVersionUID = 7358456946949913863L;
	
	private static final String commonSubject = "Sended HSMS-Message";
	
	public HsmsSendedMessageLog(HsmsMessage message, LocalDateTime timestamp) {
		super(message, timestamp);
	}
	
	public HsmsSendedMessageLog(HsmsMessage message) {
		super(message);
	}
	
	@Override
	public String subject() {
		return commonSubject;
	}
	
}
