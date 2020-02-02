package com.shimizukenta.secs.hsmsss;

public class HsmsSsSendMessageSizeException extends HsmsSsSendMessageException {
	
	private static final long serialVersionUID = 2220846766540314503L;

	public HsmsSsSendMessageSizeException() {
		super();
	}

	public HsmsSsSendMessageSizeException(String message) {
		super(message);
	}

	public HsmsSsSendMessageSizeException(Throwable cause) {
		super(cause);
	}

	public HsmsSsSendMessageSizeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public HsmsSsSendMessageSizeException(HsmsSsMessage msg) {
		super(msg);
	}

	public HsmsSsSendMessageSizeException(HsmsSsMessage msg, Throwable cause) {
		super(msg, cause);
	}

}
