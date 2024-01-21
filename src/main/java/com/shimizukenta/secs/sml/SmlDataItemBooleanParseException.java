package com.shimizukenta.secs.sml;

/**
 * SML Data-Item Boolean parse Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class SmlDataItemBooleanParseException extends SmlDataItemParseException {
	
	private static final long serialVersionUID = -5362385458324948658L;
	
	/**
	 * Costructor.
	 * 
	 */
	public SmlDataItemBooleanParseException() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param cause the cause
	 */
	public SmlDataItemBooleanParseException(Throwable cause) {
		super(cause);
	}
	
}
