package com.shimizukenta.secs;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class AbstractSecsReceiveMessageLog extends AbstractSecsLog {
	
	private static final long serialVersionUID = -1198469179914563072L;
	
	private static final String commonSubject = "Receive SECS-Message";
	
	public AbstractSecsReceiveMessageLog(SecsMessage message, LocalDateTime timestamp) {
		super(commonSubject, timestamp, Objects.requireNonNull(message));
	}
	
	public AbstractSecsReceiveMessageLog(SecsMessage message) {
		super(commonSubject, Objects.requireNonNull(message));
	}
	
	@Override
	public String subject() {
		return commonSubject;
	}
	
}
