package com.shimizukenta.secs.hsms;

import com.shimizukenta.secs.SecsMessage;

public class HsmsTimeoutT6Exception extends HsmsWaitReplyMessageException {
	
	private static final long serialVersionUID = -4799649738977591873L;

	public HsmsTimeoutT6Exception(SecsMessage primaryMessage) {
		super(primaryMessage);
	}

	public HsmsTimeoutT6Exception(SecsMessage primaryMessage, Throwable cause) {
		super(primaryMessage, cause);
	}

}
