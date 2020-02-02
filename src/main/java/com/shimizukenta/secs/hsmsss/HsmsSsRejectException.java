package com.shimizukenta.secs.hsmsss;

import com.shimizukenta.secs.SecsWaitReplyMessageException;

public class HsmsSsRejectException extends SecsWaitReplyMessageException {
	
	private static final long serialVersionUID = 6470225458405714078L;

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
	
	public HsmsSsRejectException(HsmsSsMessage primaryMessage) {
		super(primaryMessage);
	}

	public HsmsSsRejectException(HsmsSsMessage primaryMessage, Throwable cause) {
		super(primaryMessage, cause);
	}
	
}
