package com.shimizukenta.secs.hsms;

/**
 * HSMS Control Message length-byte {@code >10} Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class HsmsControlMessageLengthBytesUpperThanTenException extends HsmsMessageLengthBytesException {
	
	private static final long serialVersionUID = -5662768171171603635L;
	
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
	public HsmsControlMessageLengthBytesUpperThanTenException(HsmsMessageType type, long length) {
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
