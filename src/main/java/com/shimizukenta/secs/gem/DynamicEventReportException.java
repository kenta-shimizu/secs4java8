package com.shimizukenta.secs.gem;

/**
 * This Exception is super class of DynamicEventReportConfig method failed.
 * 
 * @author kenta-shimizu
 *
 */
public class DynamicEventReportException extends Exception {
	
	private static final long serialVersionUID = -8074400313735676602L;
	
	public DynamicEventReportException() {
	}

	public DynamicEventReportException(String message) {
		super(message);
	}

	public DynamicEventReportException(Throwable cause) {
		super(cause);
	}

	public DynamicEventReportException(String message, Throwable cause) {
		super(message, cause);
	}

	public DynamicEventReportException(
			String message,
			Throwable cause,
			boolean enableSuppression,
			boolean writableStackTrace) {
		
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
