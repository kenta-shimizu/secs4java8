package com.shimizukenta.secs.secs2;

/**
 * Secs2 length byte out of range Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class Secs2LengthByteOutOfRangeException extends IllegalArgumentException {
	
	private static final long serialVersionUID = -4790954656700659624L;
	
	/**
	 * Constructor.
	 * 
	 */
	public Secs2LengthByteOutOfRangeException() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 */
	public Secs2LengthByteOutOfRangeException(String message) {
		super(message);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param cause the cause
	 */
	public Secs2LengthByteOutOfRangeException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 * @param cause the cause
	 */
	public Secs2LengthByteOutOfRangeException(String message, Throwable cause) {
		super(message, cause);
	}

}
