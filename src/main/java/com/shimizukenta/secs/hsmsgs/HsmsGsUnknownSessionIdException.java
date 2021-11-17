package com.shimizukenta.secs.hsmsgs;

import com.shimizukenta.secs.hsms.HsmsException;

public class HsmsGsUnknownSessionIdException extends HsmsException {
	
	private static final long serialVersionUID = -3532958513890338098L;
	
	public HsmsGsUnknownSessionIdException(int id) {
		super("Unknown Session-ID: " + id);
	}
	
}
