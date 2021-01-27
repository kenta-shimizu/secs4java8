package com.shimizukenta.secs.hsmsss;

import java.time.LocalDateTime;

import com.shimizukenta.secs.SecsReceiveMessageLog;

public class HsmsSsReceiveMessageLog extends SecsReceiveMessageLog {
	
	private static final long serialVersionUID = 67992153842819842L;
	
	private static final String commonSubject = "Receive HSMS-SS-Message";
	
	public HsmsSsReceiveMessageLog(HsmsSsMessage message, LocalDateTime timestamp) {
		super(message, timestamp);
	}

	public HsmsSsReceiveMessageLog(HsmsSsMessage message) {
		super(message);
	}
	
	@Override
	public String subject() {
		return commonSubject;
	}
	
}
