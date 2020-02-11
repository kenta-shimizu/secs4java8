package com.shimizukenta.secs.hsmsss;

public class HsmsSsTooBigSendMessageException extends HsmsSsSendMessageException {
	
	private static final long serialVersionUID = -5088584094004132950L;

	public HsmsSsTooBigSendMessageException() {
		super();
	}

	public HsmsSsTooBigSendMessageException(String message) {
		super(message);
	}

	public HsmsSsTooBigSendMessageException(Throwable cause) {
		super(cause);
	}

	public HsmsSsTooBigSendMessageException(String message, Throwable cause) {
		super(message, cause);
	}

	public HsmsSsTooBigSendMessageException(HsmsSsMessage msg) {
		super(msg);
	}

	public HsmsSsTooBigSendMessageException(HsmsSsMessage msg, Throwable cause) {
		super(msg, cause);
	}

}
