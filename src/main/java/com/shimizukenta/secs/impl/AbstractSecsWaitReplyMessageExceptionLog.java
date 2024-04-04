package com.shimizukenta.secs.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageExceptionLog;

public abstract class AbstractSecsWaitReplyMessageExceptionLog extends AbstractSecsThrowableLog implements SecsWaitReplyMessageExceptionLog {
	
	private static final long serialVersionUID = -4007928836031665906L;
	
	private final SecsWaitReplyMessageException cause;
	
	public AbstractSecsWaitReplyMessageExceptionLog(SecsWaitReplyMessageException cause) {
		super(cause);
		this.cause = cause;
	}
	
	public Optional<SecsMessage> referenceSecsMessage() {
		return cause.secsMessage();
	}
	
	@Override
	public Optional<String> optionalValueString() {
		return this.referenceSecsMessage()
				.map(msg -> createHeader10BytesString(msg));
	}
	
	private static String createHeader10BytesString(SecsMessage msg) {
		
		byte[] bs = msg.header10Bytes();
		
		return "[" + s02X(bs[0]) + " " + s02X(bs[1]) 
		+ "|" + s02X(bs[2]) + " " + s02X(bs[3]) 
		+ "|" + s02X(bs[4]) + " " + s02X(bs[5]) 
		+ "|" + s02X(bs[6]) + " " + s02X(bs[7])
		+ " " + s02X(bs[8]) + " " + s02X(bs[9])
		+ "]";
	}
	
	private static String s02X(byte b) {
		return String.format("%02X", b);
	}
	
}
