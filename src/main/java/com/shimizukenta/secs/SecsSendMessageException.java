package com.shimizukenta.secs;

/**
 * This Exception is super class of SECS-Communicator send-message failed Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class SecsSendMessageException extends SecsException {
	
	private static final long serialVersionUID = 7063333511988028224L;
	
	public SecsSendMessageException() {
		super();
	}

	public SecsSendMessageException(String message) {
		super(message);
	}

	public SecsSendMessageException(Throwable cause) {
		super(cause);
	}

	public SecsSendMessageException(String message, Throwable cause) {
		super(message, cause);
	}

	public SecsSendMessageException(SecsMessage msg) {
		super(msg);
	}
	
	public SecsSendMessageException(SecsMessage msg, Throwable cause) {
		super(msg, cause);
	}
	
}
