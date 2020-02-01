package com.shimizukenta.secs.hsmsss;

import com.shimizukenta.secs.SecsSendMessageException;

public class HsmsSsSendMessageException extends SecsSendMessageException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1786959342763025990L;

	public HsmsSsSendMessageException() {
		super();
	}

	public HsmsSsSendMessageException(String message) {
		super(message);
	}

	public HsmsSsSendMessageException(Throwable cause) {
		super(cause);
	}

	public HsmsSsSendMessageException(String message, Throwable cause) {
		super(message, cause);
	}

	public HsmsSsSendMessageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	public HsmsSsSendMessageException(HsmsSsMessage msg) {
		super(msg);
	}
	
	public HsmsSsSendMessageException(HsmsSsMessage msg, Throwable cause) {
		super(msg, cause);
	}
	
}
