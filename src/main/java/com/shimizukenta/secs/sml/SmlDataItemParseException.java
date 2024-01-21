package com.shimizukenta.secs.sml;

/**
 * SML Data-Item Parse Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class SmlDataItemParseException extends SmlParseException {
	
	private static final long serialVersionUID = -2654119012414524462L;
	
	/**
	 * Constructor.
	 * 
	 */
	public SmlDataItemParseException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param message the message
	 */
	public SmlDataItemParseException(String message) {
		super(message);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param cause the cause
	 */
	public SmlDataItemParseException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 * @param cause the cause
	 */
	public SmlDataItemParseException(String message, Throwable cause) {
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
	public SmlDataItemParseException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
