package com.shimizukenta.secs.secs1;

import com.shimizukenta.secs.SecsSendMessageException;

public class Secs1SendMessageException extends SecsSendMessageException {
	
	private static final long serialVersionUID = -8586897718794845589L;

	public Secs1SendMessageException() {
		super();
	}

	public Secs1SendMessageException(String message) {
		super(message);
	}

	public Secs1SendMessageException(Throwable cause) {
		super(cause);
	}

	public Secs1SendMessageException(String message, Throwable cause) {
		super(message, cause);
	}

	public Secs1SendMessageException(Secs1Message msg) {
		super(msg);
	}

	public Secs1SendMessageException(Secs1Message msg, Throwable cause) {
		super(msg, cause);
	}

}
