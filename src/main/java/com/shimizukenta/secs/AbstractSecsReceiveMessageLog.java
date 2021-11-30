package com.shimizukenta.secs;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public abstract class AbstractSecsReceiveMessageLog extends AbstractSecsLog implements SecsReceiveMessageLog {
	
	private static final long serialVersionUID = -1198469179914563072L;
	
	private static final String commonSubject = "Receive SECS-Message";
	
	private final AbstractSecsMessage msg;
	
	public AbstractSecsReceiveMessageLog(AbstractSecsMessage message, LocalDateTime timestamp) {
		super(commonSubject, timestamp, Objects.requireNonNull(message));
		this.msg = message;
	}
	
	public AbstractSecsReceiveMessageLog(AbstractSecsMessage message) {
		super(commonSubject, Objects.requireNonNull(message));
		this.msg = message;
	}
	
	@Override
	public String subject() {
		return commonSubject;
	}
	
	@Override
	public Optional<AbstractSecsMessage> optionalAbstractSecsMessage() {
		return Optional.of(this.msg);
	}

}
