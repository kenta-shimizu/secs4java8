package com.shimizukenta.secs;

import java.util.Optional;

/**
 * This Exception is super class of SECS-Communicating Exception.
 * 
 * <p>
 * To get Reference-Message if exist, {@link #secsMessage()}<br />
 * </p>
 * 
 * @author kenta-shimzu
 *
 */
public class SecsException extends Exception {
	
	private static final long serialVersionUID = -3938886949828082098L;
	
	/**
	 * Reference SECS-Message.
	 */
	private final SecsMessage secsMsg;
	
	/**
	 * Constructor.
	 * 
	 */
	public SecsException() {
		super();
		secsMsg = null;
	}
	
	/**
	 * COnstructor.
	 * 
	 * @param message the message
	 */
	public SecsException(String message) {
		super(message);
		secsMsg = null;
	}
	
	/**
	 * Constructor.
	 * 
	 * @param cause the cause
	 */
	public SecsException(Throwable cause) {
		super(cause);
		secsMsg = null;
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 * @param cause the cause
	 */
	public SecsException(String message, Throwable cause) {
		super(message, cause);
		secsMsg = null;
	}
	
	/**
	 * Constructor.
	 * 
	 * @param secsMessage the reference-secs-message
	 */
	public SecsException(SecsMessage secsMessage) {
		super(createMessage(secsMessage));
		this.secsMsg = secsMessage;
	}
	
	/**
	 * Constructor.
	 * 
	 * @param secsMessage the reference-secs-message
	 * @param cause the cause
	 */
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
	
	/**
	 * Returns reference SECS-Message.
	 * 
	 * @return reference SECS-Message
	 */
	public Optional<SecsMessage> secsMessage() {
		return secsMsg == null ? Optional.empty() : Optional.of(secsMsg);
	}
}
