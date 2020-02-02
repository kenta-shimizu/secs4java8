package com.shimizukenta.secs;

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
