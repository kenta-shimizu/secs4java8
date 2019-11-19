package secs.secs1;

import secs.SecsMessage;
import secs.SecsSendMessageException;

public class Secs1SendMessageException extends SecsSendMessageException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 694651447953258946L;

	public Secs1SendMessageException() {
		super();
	}

	public Secs1SendMessageException(String message) {
		super(message);
	}

	public Secs1SendMessageException(Throwable cause) {
		super(cause);
	}

	public Secs1SendMessageException(String message, Throwable cause) {
		super(message, cause);
	}

	public Secs1SendMessageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public Secs1SendMessageException(SecsMessage msg) {
		super(msg);
	}

	public Secs1SendMessageException(SecsMessage msg, Throwable cause) {
		super(msg, cause);
	}

}
