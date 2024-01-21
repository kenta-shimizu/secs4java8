package com.shimizukenta.secs.sml;

/**
 * Parse SML-Message out-of-range Stream-Number Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class SmlParseStreamOutOfRangeException extends SmlParseException {

	private static final long serialVersionUID = 7097817723809409602L;
	
	/**
	 * Constructor.
	 * 
	 * @param strm the Stream-Number
	 */
	public SmlParseStreamOutOfRangeException(int strm) {
		super(String.valueOf(strm));
	}
}
