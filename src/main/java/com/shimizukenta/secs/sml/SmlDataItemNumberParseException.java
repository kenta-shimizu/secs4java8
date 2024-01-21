package com.shimizukenta.secs.sml;

/**
 * SML Data-Item Number parse Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class SmlDataItemNumberParseException extends SmlDataItemParseException {
	
	private static final long serialVersionUID = -5586504959783559317L;
	
	/**
	 * Constructor.
	 * 
	 */
	public SmlDataItemNumberParseException() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 */
	public SmlDataItemNumberParseException(String message) {
		super(message);
	}
	
	/**
	 * Cunstructor.
	 * 
	 * @param cause the cause
	 */
	public SmlDataItemNumberParseException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 * @param cause the cause
	 */
	public SmlDataItemNumberParseException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
