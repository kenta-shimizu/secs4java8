package com.shimizukenta.secs.hsms;

/**
 * HSMS Message Length byte {@code <10} Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class HsmsMessageLengthBytesLowerThanTenException extends HsmsMessageLengthBytesException {
	
	private static final long serialVersionUID = -5199965558586918070L;
	
	/**
	 * Constructor.
	 * 
	 * @param length the length size
	 */
	public HsmsMessageLengthBytesLowerThanTenException(long length) {
		super(("length: " + length), length);
	}

}
