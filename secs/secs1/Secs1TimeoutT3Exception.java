package secs.secs1;

import secs.SecsMessage;
import secs.SecsWaitReplyMessageException;

public class Secs1TimeoutT3Exception extends SecsWaitReplyMessageException {

	private static final long serialVersionUID = -819647606179236547L;

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

	public Secs1TimeoutT3Exception(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public Secs1TimeoutT3Exception(SecsMessage primaryMessage) {
		super(primaryMessage);
	}

	public Secs1TimeoutT3Exception(SecsMessage primaryMessage, Throwable cause) {
		super(primaryMessage, cause);
	}

}
