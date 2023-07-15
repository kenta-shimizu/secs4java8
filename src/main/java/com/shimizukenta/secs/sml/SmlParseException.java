package com.shimizukenta.secs.sml;

/**
 * SMML Parse Exception.
 * 
 * @author shimizukenta
 *
 */
public class SmlParseException extends Exception {

	private static final long serialVersionUID = 6933932663095380472L;
	
	/**
	 * Constructor.
	 * 
	 */
	public SmlParseException() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 */
	public SmlParseException(String message) {
		super(message);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param cause the cause
	 */
	public SmlParseException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 * @param cause the cause
	 */
	public SmlParseException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the detail message.
	 * @param cause the cause. (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 * @param enableSuppression whether or not suppression is enabled or disabled
	 * @param writableStackTrace whether or not the stack trace should be writable
	 */
	public SmlParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
