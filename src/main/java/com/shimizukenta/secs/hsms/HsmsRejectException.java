package com.shimizukenta.secs.hsms;

import com.shimizukenta.secs.SecsMessage;

public class HsmsRejectException extends HsmsWaitReplyMessageException {
	
	private static final long serialVersionUID = -5627136181555783679L;
	
	public HsmsRejectException(SecsMessage primaryMessage) {
		super(primaryMessage);
	}

	public HsmsRejectException(SecsMessage primaryMessage, Throwable cause) {
		super(primaryMessage, cause);
	}

}
