package com.shimizukenta.secs.hsms;

import java.time.LocalDateTime;

import com.shimizukenta.secs.AbstractSecsReceiveMessageLog;

public class HsmsReceiveMessageLog extends AbstractSecsReceiveMessageLog {
	
	private static final long serialVersionUID = 2805162103637679173L;
	
	private static final String commonSubject = "Receive HSMS-Message";
	
	public HsmsReceiveMessageLog(AbstractHsmsMessage message, LocalDateTime timestamp) {
		super(message, timestamp);
	}

	public HsmsReceiveMessageLog(AbstractHsmsMessage message) {
		super(message);
	}
	
	@Override
	public String subject() {
		return commonSubject;
	}
	
}
