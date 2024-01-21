package com.shimizukenta.secs.sml;

/**
 * Parse SML-Message out-of-range Function-Number Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class SmlParseFunctionOutOfRangeException extends SmlParseException {
	
	private static final long serialVersionUID = -327020018176459746L;
	
	/**
	 * Constructor.
	 * 
	 * @param func the Function-Number
	 */
	public SmlParseFunctionOutOfRangeException(int func) {
		super(String.valueOf(func));
	}
	
}
