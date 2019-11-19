package secs.hsmsSs;

import secs.SecsMessage;
import secs.SecsWaitReplyMessageException;

public class HsmsSsRejectException extends SecsWaitReplyMessageException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3616101304258830704L;

	public HsmsSsRejectException() {
		super();
	}

	public HsmsSsRejectException(String message) {
		super(message);
	}

	public HsmsSsRejectException(Throwable cause) {
		super(cause);
	}

	public HsmsSsRejectException(String message, Throwable cause) {
		super(message, cause);
	}

	public HsmsSsRejectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public HsmsSsRejectException(SecsMessage primaryMessage) {
		super(primaryMessage);
	}

	public HsmsSsRejectException(SecsMessage primaryMessage, Throwable cause) {
		super(primaryMessage, cause);
	}
	
}
