package com.shimizukenta.secs.hsms;

import com.shimizukenta.secs.SecsMessage;

public class HsmsT6TimeoutException extends HsmsWaitReplyMessageException {
	
	private static final long serialVersionUID = -4799649738977591873L;

	public HsmsT6TimeoutException(SecsMessage primaryMessage) {
		super(primaryMessage);
	}

	public HsmsT6TimeoutException(SecsMessage primaryMessage, Throwable cause) {
		super(primaryMessage, cause);
	}

}
