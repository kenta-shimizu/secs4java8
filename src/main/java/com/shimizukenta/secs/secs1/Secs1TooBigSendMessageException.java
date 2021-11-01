package com.shimizukenta.secs.secs1;

public class Secs1TooBigSendMessageException extends Secs1SendMessageException {
	
	private static final long serialVersionUID = 7790874216559023571L;

	public Secs1TooBigSendMessageException(Secs1Message msg) {
		super(msg);
	}

	public Secs1TooBigSendMessageException(Secs1Message msg, Throwable cause) {
		super(msg, cause);
	}

}
