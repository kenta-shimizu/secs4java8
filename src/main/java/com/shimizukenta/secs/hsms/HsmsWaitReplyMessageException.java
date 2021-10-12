package com.shimizukenta.secs.hsms;

import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsWaitReplyMessageException;

public class HsmsWaitReplyMessageException extends SecsWaitReplyMessageException {
	
	private static final long serialVersionUID = 1959829976603850269L;

	public HsmsWaitReplyMessageException(SecsMessage primaryMessage) {
		super(primaryMessage);
	}

	public HsmsWaitReplyMessageException(SecsMessage primaryMessage, Throwable cause) {
		super(primaryMessage, cause);
	}

}
