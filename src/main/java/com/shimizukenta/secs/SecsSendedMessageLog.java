package com.shimizukenta.secs;

import java.time.LocalDateTime;
import java.util.Objects;

public class SecsSendedMessageLog extends AbstractSecsLog {
	
	private static final long serialVersionUID = 7389391644974019197L;
	
	private static final String commonSubject = "Sended SECS-Message";
	
	public SecsSendedMessageLog(SecsMessage message, LocalDateTime timestamp) {
		super(commonSubject, timestamp, Objects.requireNonNull(message));
	}
	
	public SecsSendedMessageLog(SecsMessage message) {
		super(commonSubject, Objects.requireNonNull(message));
	}
	
	@Override
	public String subject() {
		return commonSubject;
	}
	
}
