package com.shimizukenta.secs.secs1;

import com.shimizukenta.secs.SecsWaitReplyMessageException;

public class Secs1WaitReplyMessageException extends SecsWaitReplyMessageException {
	
	private static final long serialVersionUID = -6010907057511916221L;
	
	public Secs1WaitReplyMessageException(Secs1Message primaryMessage) {
		super(primaryMessage);
	}

	public Secs1WaitReplyMessageException(Secs1Message primaryMessage, Throwable cause) {
		super(primaryMessage, cause);
	}

}
