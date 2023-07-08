package com.shimizukenta.secs.secs2;

/**
 * Secs2 bytes parse Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class Secs2BytesParseException extends Secs2Exception {
	
	private static final long serialVersionUID = -4597428673299213854L;
	
	/**
	 * Constructor.
	 * 
	 */
	public Secs2BytesParseException() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 */
	public Secs2BytesParseException(String message) {
		super(message);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param cause the cause
	 */
	public Secs2BytesParseException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 * @param cause the cause
	 */
	public Secs2BytesParseException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
