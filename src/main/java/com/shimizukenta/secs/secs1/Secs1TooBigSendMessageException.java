package com.shimizukenta.secs.secs1;

public class Secs1TooBigSendMessageException extends Secs1SendMessageException {

	private static final long serialVersionUID = -2524467469346164679L;

	public Secs1TooBigSendMessageException() {
		super();
	}

	public Secs1TooBigSendMessageException(String message) {
		super(message);
	}

	public Secs1TooBigSendMessageException(Throwable cause) {
		super(cause);
	}

	public Secs1TooBigSendMessageException(String message, Throwable cause) {
		super(message, cause);
	}

	public Secs1TooBigSendMessageException(Secs1Message msg) {
		super(msg);
	}

	public Secs1TooBigSendMessageException(Secs1Message msg, Throwable cause) {
		super(msg, cause);
	}

}
