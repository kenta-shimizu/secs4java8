package com.shimizukenta.secs.hsms;

public class HsmsMessageLengthBytesLowerThanTenException extends HsmsMessageLengthBytesException {
	
	private static final long serialVersionUID = -5199965558586918070L;
	
	public HsmsMessageLengthBytesLowerThanTenException(long length) {
		super(("length: " + length), length);
	}

}
