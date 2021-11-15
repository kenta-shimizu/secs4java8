package com.shimizukenta.secs.hsms;

import com.shimizukenta.secs.SecsMessage;

public class HsmsIllegalTypeReplyMessageException extends HsmsWaitReplyMessageException {
	
	private static final long serialVersionUID = -3694450588050886894L;
	
	public HsmsIllegalTypeReplyMessageException(SecsMessage primaryMessage) {
		super(primaryMessage);
	}
	
	public HsmsIllegalTypeReplyMessageException(SecsMessage primaryMessage, Throwable cause) {
		super(primaryMessage, cause);
	}
	
}
