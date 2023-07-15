package com.shimizukenta.secs;

/**
 * This Exception is super class of SECS-Communicator receive-message failed Exception.
 * 
 * <p>
 * e.g. Timeout-T3
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public class SecsWaitReplyMessageException extends SecsException {
	
	private static final long serialVersionUID = -443398822888562392L;
	
	/**
	 * Constructor.
	 * 
	 */
	public SecsWaitReplyMessageException() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 */
	public SecsWaitReplyMessageException(String message) {
		super(message);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param cause the cause
	 */
	public SecsWaitReplyMessageException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 * @param cause the cause
	 */
	public SecsWaitReplyMessageException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param primaryMessage the primary-message
	 */
	public SecsWaitReplyMessageException(SecsMessage primaryMessage) {
		super(primaryMessage);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param primaryMessage the primary-message
	 * @param cause the cause
	 */
	public SecsWaitReplyMessageException(SecsMessage primaryMessage, Throwable cause) {
		super(primaryMessage, cause);
	}
	
}
