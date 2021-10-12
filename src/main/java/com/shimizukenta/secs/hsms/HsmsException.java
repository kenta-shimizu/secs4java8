package com.shimizukenta.secs.hsms;

import com.shimizukenta.secs.SecsException;

public class HsmsException extends SecsException {
	
	private static final long serialVersionUID = 6427923930733052896L;
	
	public HsmsException() {
		super();
	}

	public HsmsException(String message) {
		super(message);
	}

	public HsmsException(Throwable cause) {
		super(cause);
	}

	public HsmsException(String message, Throwable cause) {
		super(message, cause);
	}

}
