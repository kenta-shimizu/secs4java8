package com.shimizukenta.secs;

import java.time.LocalDateTime;
import java.util.Objects;

public class SecsReceiveMessageLog extends AbstractSecsLog {
	
	private static final long serialVersionUID = -1198469179914563072L;
	
	private static final String commonSubject = "Receive SECS-Message";
	
	public SecsReceiveMessageLog(SecsMessage message, LocalDateTime timestamp) {
		super(commonSubject, timestamp, Objects.requireNonNull(message));
	}
	
	public SecsReceiveMessageLog(SecsMessage message) {
		super(commonSubject, Objects.requireNonNull(message));
	}
	
	@Override
	public String subject() {
		return commonSubject;
	}
	
}
