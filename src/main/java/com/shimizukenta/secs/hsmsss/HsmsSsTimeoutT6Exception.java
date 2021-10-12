package com.shimizukenta.secs.hsmsss;

import com.shimizukenta.secs.SecsWaitReplyMessageException;

@Deprecated
public class HsmsSsTimeoutT6Exception extends SecsWaitReplyMessageException {
	
	private static final long serialVersionUID = -6880723997450961190L;

	public HsmsSsTimeoutT6Exception() {
		super();
	}

	public HsmsSsTimeoutT6Exception(String message) {
		super(message);
	}

	public HsmsSsTimeoutT6Exception(Throwable cause) {
		super(cause);
	}

	public HsmsSsTimeoutT6Exception(String message, Throwable cause) {
		super(message, cause);
	}

	public HsmsSsTimeoutT6Exception(HsmsSsMessage primaryMessage) {
		super(primaryMessage);
	}

	public HsmsSsTimeoutT6Exception(HsmsSsMessage primaryMessage, Throwable cause) {
		super(primaryMessage, cause);
	}

}
