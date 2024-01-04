package com.shimizukenta.secs.impl;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsSendedMessageLog;

public abstract class AbstractSecsSendedMessageLog extends AbstractSecsLog implements SecsSendedMessageLog {
	
	private static final long serialVersionUID = 7389391644974019197L;
	
	private static final String commonSubject = "Sended SECS-Message";
	
	private final SecsMessage msg;
	
	public AbstractSecsSendedMessageLog(SecsMessage message, LocalDateTime timestamp) {
		super(commonSubject, timestamp, Objects.requireNonNull(message));
		this.msg = message;
	}
	
	public AbstractSecsSendedMessageLog(SecsMessage message) {
		super(commonSubject, Objects.requireNonNull(message));
		this.msg = message;
	}
	
	@Override
	public String subject() {
		return commonSubject;
	}
	
	@Override
	public Optional<SecsMessage> optionalSecsMessage() {
		return Optional.of(this.msg);
	}
	
}
