package com.shimizukenta.secs;

import java.util.Optional;

public class SecsException extends Exception {
	
	private static final long serialVersionUID = -3938886949828082098L;
	
	private final SecsMessage secsMsg;
	
	public SecsException() {
		super();
		secsMsg = null;
	}

	public SecsException(String message) {
		super(message);
		secsMsg = null;
	}

	public SecsException(Throwable cause) {
		super(cause);
		secsMsg = null;
	}

	public SecsException(String message, Throwable cause) {
		super(message, cause);
		secsMsg = null;
	}
	
	public SecsException(SecsMessage secsMessage) {
		super(createMessage(secsMessage));
		this.secsMsg = secsMessage;
	}
	
	public SecsException(SecsMessage secsMessage, Throwable cause) {
		super(createMessage(secsMessage), cause);
		this.secsMsg = secsMessage;
	}
	
	private static String createMessage(SecsMessage msg) {
		
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
	
	public Optional<SecsMessage> secsMessage() {
		return secsMsg == null ? Optional.empty() : Optional.of(secsMsg);
	}
}
