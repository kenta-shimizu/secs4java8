package com.shimizukenta.secs.gem;

public class AliasNotFoundDynamicEventReportException extends DynamicEventReportException {
	
	private static final long serialVersionUID = -4281074440072056436L;
	
	public AliasNotFoundDynamicEventReportException() {
	}

	public AliasNotFoundDynamicEventReportException(String message) {
		super(message);
	}

	public AliasNotFoundDynamicEventReportException(Throwable cause) {
		super(cause);
	}

	public AliasNotFoundDynamicEventReportException(String message, Throwable cause) {
		super(message, cause);
	}

	public AliasNotFoundDynamicEventReportException(
			String message,
			Throwable cause,
			boolean enableSuppression,
			boolean writableStackTrace) {
		
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
