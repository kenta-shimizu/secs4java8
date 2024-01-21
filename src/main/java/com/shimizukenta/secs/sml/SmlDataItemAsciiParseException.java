package com.shimizukenta.secs.sml;

/**
 * SML Data-Item Ascii parse Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class SmlDataItemAsciiParseException extends SmlDataItemParseException {
	
	private static final long serialVersionUID = -4086048074724662894L;
	
	/**
	 * Constructor.
	 * 
	 */
	public SmlDataItemAsciiParseException() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 */
	public SmlDataItemAsciiParseException(String message) {
		super(message);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param cause the cause
	 */
	public SmlDataItemAsciiParseException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 * @param cause the cause
	 */
	public SmlDataItemAsciiParseException(String message, Throwable cause) {
		super(message, cause);
	}

}
