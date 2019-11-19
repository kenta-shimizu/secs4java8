package secs.hsmsSs;

import secs.SecsMessage;
import secs.SecsWaitReplyMessageException;

public class HsmsSsTimeoutT6Exception extends SecsWaitReplyMessageException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1103030516068115495L;

	public HsmsSsTimeoutT6Exception() {
		super();
	}

	public HsmsSsTimeoutT6Exception(String message) {
		super(message);
	}

	public HsmsSsTimeoutT6Exception(Throwable cause) {
		super(cause);
	}

	public HsmsSsTimeoutT6Exception(String message, Throwable cause) {
		super(message, cause);
	}

	public HsmsSsTimeoutT6Exception(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public HsmsSsTimeoutT6Exception(SecsMessage primaryMessage) {
		super(primaryMessage);
	}

	public HsmsSsTimeoutT6Exception(SecsMessage primaryMessage, Throwable cause) {
		super(primaryMessage, cause);
	}

}
