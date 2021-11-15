package com.shimizukenta.secs.hsms;

public class HsmsControlMessageLengthBytesUpperThanTenException extends HsmsMessageLengthBytesException {
	
	private static final long serialVersionUID = -5662768171171603635L;
	
	private final HsmsMessageType type;
	
	public HsmsControlMessageLengthBytesUpperThanTenException(HsmsMessageType type, long length) {
		super(("type: " + type.toString() + ", length: " + length), length);
		this.type = type;
	}
	
	public HsmsMessageType type() {
		return this.type;
	}
	
}
