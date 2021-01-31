package com.shimizukenta.secs.hsmsss;

import java.time.LocalDateTime;

import com.shimizukenta.secs.AbstractSecsTrySendMessageLog;

public class HsmsSsTrySendMessageLog extends AbstractSecsTrySendMessageLog {
	
	private static final long serialVersionUID = -2104861980874139884L;
	
	private static final String commonSubject = "Try-Send HSMS-SS-Message";
	
	public HsmsSsTrySendMessageLog(HsmsSsMessage message, LocalDateTime timestamp) {
		super(message, timestamp);
	}

	public HsmsSsTrySendMessageLog(HsmsSsMessage message) {
		super(message);
	}
	
	@Override
	public String subject() {
		return commonSubject;
	}
	
}
