package com.shimizukenta.secs.hsms;

/**
 * HSMS Control Message length-byte {@code >10} Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class HsmsControlMessageLengthBytesGreaterThanTenException extends HsmsMessageLengthBytesException {
	
	private static final long serialVersionUID = 2142542736758882422L;
	
	/**
	 * Hsms Message Type.
	 * 
	 */
	private final HsmsMessageType type;
	
	/**
	 * Constructor.
	 * 
	 * @param type the message type
	 * @param length the byte length
	 */
	public HsmsControlMessageLengthBytesGreaterThanTenException(HsmsMessageType type, long length) {
		super(("type: " + type.toString() + ", length: " + length), length);
		this.type = type;
	}
	
	/**
	 * Returns HsmsMessageType.
	 * 
	 * @return HsmsMessageType
	 */
	public HsmsMessageType type() {
		return this.type;
	}
	
}
