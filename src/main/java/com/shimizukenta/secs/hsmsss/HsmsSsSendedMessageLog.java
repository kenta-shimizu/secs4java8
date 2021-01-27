package com.shimizukenta.secs.hsmsss;

import java.time.LocalDateTime;

import com.shimizukenta.secs.SecsSendedMessageLog;

public class HsmsSsSendedMessageLog extends SecsSendedMessageLog {
	
	private static final long serialVersionUID = 7293612158659733189L;
	
	private static final String commonSubject = "Sended HSMS-SS-Message";
	
	public HsmsSsSendedMessageLog(HsmsSsMessage message, LocalDateTime timestamp) {
		super(message, timestamp);
	}
	
	public HsmsSsSendedMessageLog(HsmsSsMessage message) {
		super(message);
	}
	
	@Override
	public String subject() {
		return commonSubject;
	}
	
}
