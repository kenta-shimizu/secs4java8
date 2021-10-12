package com.shimizukenta.secs.hsmsss;

import com.shimizukenta.secs.SecsWaitReplyMessageException;

@Deprecated
public class HsmsSsTimeoutT3Exception extends SecsWaitReplyMessageException {
	
	private static final long serialVersionUID = -4951833148190707156L;
	
	public HsmsSsTimeoutT3Exception() {
		super();
	}

	public HsmsSsTimeoutT3Exception(String message) {
		super(message);
	}

	public HsmsSsTimeoutT3Exception(Throwable cause) {
		super(cause);
	}

	public HsmsSsTimeoutT3Exception(String message, Throwable cause) {
		super(message, cause);
	}

	public HsmsSsTimeoutT3Exception(HsmsSsMessage primaryMessage) {
		super(primaryMessage);
	}
	
	public HsmsSsTimeoutT3Exception(HsmsSsMessage primaryMessage, Throwable cause) {
		super(primaryMessage, cause);
	}

}
