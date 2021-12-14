package com.shimizukenta.secs.hsms;

import com.shimizukenta.secs.SecsMessage;

public class HsmsTimeoutT3Exception extends HsmsWaitReplyMessageException {
	
	private static final long serialVersionUID = -8380997736755238819L;

	public HsmsTimeoutT3Exception(SecsMessage primaryMessage) {
		super(primaryMessage);
	}

	public HsmsTimeoutT3Exception(SecsMessage primaryMessage, Throwable cause) {
		super(primaryMessage, cause);
	}

}
