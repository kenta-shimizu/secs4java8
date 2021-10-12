package com.shimizukenta.secs.hsms;

import com.shimizukenta.secs.SecsMessage;

public class HsmsT3TimeoutException extends HsmsWaitReplyMessageException {
	
	private static final long serialVersionUID = -8380997736755238819L;

	public HsmsT3TimeoutException(SecsMessage primaryMessage) {
		super(primaryMessage);
	}

	public HsmsT3TimeoutException(SecsMessage primaryMessage, Throwable cause) {
		super(primaryMessage, cause);
	}

}
