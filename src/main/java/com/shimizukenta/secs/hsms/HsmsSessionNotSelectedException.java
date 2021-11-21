package com.shimizukenta.secs.hsms;

public class HsmsSessionNotSelectedException extends HsmsException {
	
	private static final long serialVersionUID = 3311637413899884977L;
	
	public HsmsSessionNotSelectedException() {
		super();
	}
	
	public HsmsSessionNotSelectedException(String message) {
		super(message);
	}
	
	public HsmsSessionNotSelectedException(Throwable cause) {
		super(cause);
	}
	
	public HsmsSessionNotSelectedException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
