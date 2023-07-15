package com.shimizukenta.secs.gem;

/**
 * This Exception is super class of DynamicEventReportConfig method failed.
 * 
 * @author kenta-shimizu
 *
 */
public class DynamicEventReportException extends Exception {
	
	private static final long serialVersionUID = -8074400313735676602L;
	
	/**
	 * Constructor.
	 */
	public DynamicEventReportException() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the detail message. The detail message is saved for later retrieval by the getMessage() method.
	 */
	public DynamicEventReportException(String message) {
		super(message);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param cause the cause (which is saved for later retrieval by the getCause() method). (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public DynamicEventReportException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the detail message. The detail message is saved for later retrieval by the getMessage() method.
	 * @param cause the cause (which is saved for later retrieval by the getCause() method). (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public DynamicEventReportException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
