package com.shimizukenta.secs.hsms;

import com.shimizukenta.secs.SecsMessage;

public class HsmsNotExpectControlTypeReplyMessageException extends HsmsWaitReplyMessageException {
	
	private static final long serialVersionUID = -3694450588050886894L;
	
	public HsmsNotExpectControlTypeReplyMessageException(SecsMessage primaryMessage) {
		super(primaryMessage);
	}
	
	public HsmsNotExpectControlTypeReplyMessageException(SecsMessage primaryMessage, Throwable cause) {
		super(primaryMessage, cause);
	}
	
}
