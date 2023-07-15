package com.shimizukenta.secs.gem;

/**
 * Alias not found by DynamicEventReport Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class AliasNotFoundDynamicEventReportException extends DynamicEventReportException {
	
	private static final long serialVersionUID = -4281074440072056436L;
	
	/**
	 * Constructor.
	 * 
	 */
	public AliasNotFoundDynamicEventReportException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param message the detail message. The detail message is saved for later retrieval by the getMessage() method.
	 */
	public AliasNotFoundDynamicEventReportException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause the cause (which is saved for later retrieval by the getCause() method). (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public AliasNotFoundDynamicEventReportException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the detail message. The detail message is saved for later retrieval by the getMessage() method.
	 * @param cause the cause (which is saved for later retrieval by the getCause() method). (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public AliasNotFoundDynamicEventReportException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
