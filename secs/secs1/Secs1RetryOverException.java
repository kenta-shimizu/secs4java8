package secs.secs1;

import secs.SecsSendMessageException;

public class Secs1RetryOverException extends SecsSendMessageException {

	private static final long serialVersionUID = -3699602290397266734L;

	public Secs1RetryOverException() {
		super();
	}

	public Secs1RetryOverException(String message) {
		super(message);
	}

	public Secs1RetryOverException(Throwable cause) {
		super(cause);
	}

	public Secs1RetryOverException(String message, Throwable cause) {
		super(message, cause);
	}

	public Secs1RetryOverException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	public Secs1RetryOverException(Secs1Message msg) {
		super(msg);
	}
	
	public Secs1RetryOverException(Secs1Message msg, Throwable cause) {
		super(msg, cause);
	}
	
}
