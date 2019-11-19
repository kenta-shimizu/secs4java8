package secs.hsmsSs;

import secs.SecsMessage;
import secs.SecsWaitReplyMessageException;

public class HsmsSsTimeoutT3Exception extends SecsWaitReplyMessageException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1022723959420986350L;

	public HsmsSsTimeoutT3Exception() {
		super();
	}

	public HsmsSsTimeoutT3Exception(String message) {
		super(message);
	}

	public HsmsSsTimeoutT3Exception(Throwable cause) {
		super(cause);
	}

	public HsmsSsTimeoutT3Exception(String message, Throwable cause) {
		super(message, cause);
	}

	public HsmsSsTimeoutT3Exception(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public HsmsSsTimeoutT3Exception(SecsMessage primaryMessage) {
		super(primaryMessage);
	}

	public HsmsSsTimeoutT3Exception(SecsMessage primaryMessage, Throwable cause) {
		super(primaryMessage, cause);
	}

}
