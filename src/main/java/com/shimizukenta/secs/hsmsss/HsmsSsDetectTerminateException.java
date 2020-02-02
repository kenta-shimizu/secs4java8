package com.shimizukenta.secs.hsmsss;

import com.shimizukenta.secs.SecsException;

public class HsmsSsDetectTerminateException extends SecsException {
	
	private static final long serialVersionUID = -8554637149818638292L;
	
	public HsmsSsDetectTerminateException() {
		super();
	}

	public HsmsSsDetectTerminateException(String message) {
		super(message);
	}

	public HsmsSsDetectTerminateException(Throwable cause) {
		super(cause);
	}

	public HsmsSsDetectTerminateException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
