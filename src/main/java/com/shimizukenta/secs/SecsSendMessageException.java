package com.shimizukenta.secs;

/**
 * This Exception is super class of SECS-Communicator send-message failed Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class SecsSendMessageException extends SecsException {
	
	private static final long serialVersionUID = 7063333511988028224L;
	
	/**
	 * Constructor.
	 * 
	 */
	public SecsSendMessageException() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 */
	public SecsSendMessageException(String message) {
		super(message);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param cause the cause
	 */
	public SecsSendMessageException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 * @param cause the cause
	 */
	public SecsSendMessageException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param msg the send-message
	 */
	public SecsSendMessageException(SecsMessage msg) {
		super(msg);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param msg the send-message
	 * @param cause the cause
	 */
	public SecsSendMessageException(SecsMessage msg, Throwable cause) {
		super(msg, cause);
	}
	
}
