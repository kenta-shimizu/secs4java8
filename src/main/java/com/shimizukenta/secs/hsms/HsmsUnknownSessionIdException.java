package com.shimizukenta.secs.hsms;

public class HsmsUnknownSessionIdException extends HsmsException {
	
	private static final long serialVersionUID = -3532958513890338098L;
	
	public HsmsUnknownSessionIdException(int id) {
		super("Unknown Session-ID: " + id);
	}
	
}
