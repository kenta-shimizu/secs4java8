package com.shimizukenta.secs.secs2;

/**
 * Secs2 illegal data format Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class Secs2IllegalDataFormatException extends Secs2Exception {
	
	private static final long serialVersionUID = -2801232481812100207L;
	
	/**
	 * Constructor.
	 * 
	 */
	public Secs2IllegalDataFormatException() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 */
	public Secs2IllegalDataFormatException(String message) {
		super(message);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param cause the cause
	 */
	public Secs2IllegalDataFormatException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 * @param cause the cause
	 */
	public Secs2IllegalDataFormatException(String message, Throwable cause) {
		super(message, cause);
	}

}
