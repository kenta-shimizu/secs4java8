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
	
	public SecsWaitReplyMessageException() {
		super();
	}

	public SecsWaitReplyMessageException(String message) {
		super(message);
	}

	public SecsWaitReplyMessageException(Throwable cause) {
		super(cause);
	}

	public SecsWaitReplyMessageException(String message, Throwable cause) {
		super(message, cause);
	}

	public SecsWaitReplyMessageException(SecsMessage primaryMessage) {
		super(primaryMessage);
	}
	
	public SecsWaitReplyMessageException(SecsMessage primaryMessage, Throwable cause) {
		super(primaryMessage, cause);
	}
	
}
