package com.shimizukenta.secs.secs2;

/**
 * Secs2 index of of bounds Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class Secs2IndexOutOfBoundsException extends Secs2Exception {
	
	private static final long serialVersionUID = -9048679220913013292L;
	
	/**
	 * Constructor.
	 * 
	 */
	public Secs2IndexOutOfBoundsException() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 */
	public Secs2IndexOutOfBoundsException(String message) {
		super(message);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param cause the cause
	 */
	public Secs2IndexOutOfBoundsException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 * @param cause the cause
	 */
	public Secs2IndexOutOfBoundsException(String message, Throwable cause) {
		super(message, cause);
	}

}
