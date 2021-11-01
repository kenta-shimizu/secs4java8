package com.shimizukenta.secs.secs1;

import com.shimizukenta.secs.SecsSendMessageException;

public class Secs1SendMessageException extends SecsSendMessageException {
	
	private static final long serialVersionUID = 8277809939091038496L;
	
	public Secs1SendMessageException(Secs1Message msg) {
		super(msg);
	}

	public Secs1SendMessageException(Secs1Message msg, Throwable cause) {
		super(msg, cause);
	}

}
