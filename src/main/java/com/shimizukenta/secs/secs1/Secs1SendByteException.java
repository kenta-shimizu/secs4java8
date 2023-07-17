package com.shimizukenta.secs.secs1;

/**
 * SECS-I Send byte Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class Secs1SendByteException extends Secs1Exception {
	
	private static final long serialVersionUID = 1248896164196481979L;
	
	/**
	 * Constructor.
	 * 
	 */
	public Secs1SendByteException() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 */
	public Secs1SendByteException(String message) {
		super(message);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param cause the cause
	 */
	public Secs1SendByteException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 * @param cause the cause
	 */
	public Secs1SendByteException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
