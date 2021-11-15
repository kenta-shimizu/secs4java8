package com.shimizukenta.secs.hsms;

public class HsmsMessageLengthBytesException extends HsmsException {
	
	private static final long serialVersionUID = -3670973419196584240L;
	
	private final long length;
	
	public HsmsMessageLengthBytesException(long length) {
		super();
		this.length = length;
	}

	public HsmsMessageLengthBytesException(String message, long length) {
		super(message);
		this.length = length;
	}
	
	public long length() {
		return length;
	}
	
}
