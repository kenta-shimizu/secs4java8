package com.shimizukenta.secs;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public abstract class AbstractSecsTrySendMessageLog extends AbstractSecsLog implements SecsTrySendMessageLog {
	
	private static final long serialVersionUID = -5404537548566127711L;
	
	private static final String commonSubject = "Try-Send SECS-Message";
	
	private final AbstractSecsMessage msg;
	
	public AbstractSecsTrySendMessageLog(AbstractSecsMessage message, LocalDateTime timestamp) {
		super(commonSubject, timestamp, Objects.requireNonNull(message));
		this.msg = message;
	}
	
	public AbstractSecsTrySendMessageLog(AbstractSecsMessage message) {
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
