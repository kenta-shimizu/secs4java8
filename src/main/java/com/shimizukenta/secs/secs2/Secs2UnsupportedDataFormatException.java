package com.shimizukenta.secs.secs2;

/**
 * Secs2 unsupported data format Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class Secs2UnsupportedDataFormatException extends Secs2BytesParseException {
	
	private static final long serialVersionUID = -305868137866435475L;
	
	/**
	 * Constructor.
	 * 
	 */
	public Secs2UnsupportedDataFormatException() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 */
	public Secs2UnsupportedDataFormatException(String message) {
		super(message);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param cause the cause
	 */
	public Secs2UnsupportedDataFormatException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 * @param cause the cause
	 */
	public Secs2UnsupportedDataFormatException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
