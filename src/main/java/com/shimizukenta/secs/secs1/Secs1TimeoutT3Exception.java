package com.shimizukenta.secs.secs1;

import com.shimizukenta.secs.SecsWaitReplyMessageException;

public class Secs1TimeoutT3Exception extends SecsWaitReplyMessageException {
	
	private static final long serialVersionUID = 7313332817972338765L;

	public Secs1TimeoutT3Exception() {
		super();
	}

	public Secs1TimeoutT3Exception(String message) {
		super(message);
	}

	public Secs1TimeoutT3Exception(Throwable cause) {
		super(cause);
	}

	public Secs1TimeoutT3Exception(String message, Throwable cause) {
		super(message, cause);
	}

	public Secs1TimeoutT3Exception(Secs1Message primaryMessage) {
		super(primaryMessage);
	}

	public Secs1TimeoutT3Exception(Secs1Message primaryMessage, Throwable cause) {
		super(primaryMessage, cause);
	}

}
