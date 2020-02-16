package com.shimizukenta.secs.secs1;

import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;

public class Secs1DetectTerminateException extends SecsException {
	
	private static final long serialVersionUID = -2508376536085669257L;

	public Secs1DetectTerminateException() {
		super();
	}

	public Secs1DetectTerminateException(String message) {
		super(message);
	}

	public Secs1DetectTerminateException(Throwable cause) {
		super(cause);
	}

	public Secs1DetectTerminateException(String message, Throwable cause) {
		super(message, cause);
	}

	public Secs1DetectTerminateException(SecsMessage secsMessage) {
		super(secsMessage);
	}

	public Secs1DetectTerminateException(SecsMessage secsMessage, Throwable cause) {
		super(secsMessage, cause);
	}

}
