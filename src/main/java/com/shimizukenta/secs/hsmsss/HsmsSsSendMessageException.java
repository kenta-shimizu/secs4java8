package com.shimizukenta.secs.hsmsss;

import com.shimizukenta.secs.SecsSendMessageException;

@Deprecated
public class HsmsSsSendMessageException extends SecsSendMessageException {
	
	private static final long serialVersionUID = -4647225535173892949L;
	
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

	public HsmsSsSendMessageException(HsmsSsMessage msg) {
		super(msg);
	}
	
	public HsmsSsSendMessageException(HsmsSsMessage msg, Throwable cause) {
		super(msg, cause);
	}
	
}
