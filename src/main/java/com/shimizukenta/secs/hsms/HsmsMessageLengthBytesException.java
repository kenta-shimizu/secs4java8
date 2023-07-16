package com.shimizukenta.secs.hsms;

/**
 * HSMS Message Length byte Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class HsmsMessageLengthBytesException extends HsmsException {
	
	private static final long serialVersionUID = -3670973419196584240L;
	
	/**
	 * Length-byte size.
	 * 
	 */
	private final long length;
	
	/**
	 * Constructor.
	 * 
	 * @param length the length size
	 */
	public HsmsMessageLengthBytesException(long length) {
		super();
		this.length = length;
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 * @param length the length size
	 */
	public HsmsMessageLengthBytesException(String message, long length) {
		super(message);
		this.length = length;
	}
	
	/**
	 * Returns length size.
	 * 
	 * @return length size
	 */
	public long length() {
		return length;
	}
	
}
