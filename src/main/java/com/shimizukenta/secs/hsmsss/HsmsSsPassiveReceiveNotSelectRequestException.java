package com.shimizukenta.secs.hsmsss;

import com.shimizukenta.secs.hsms.HsmsException;

public class HsmsSsPassiveReceiveNotSelectRequestException extends HsmsException {
	
	private static final long serialVersionUID = -5410805951789181837L;
	
	public HsmsSsPassiveReceiveNotSelectRequestException() {
		super();
	}
	
	public HsmsSsPassiveReceiveNotSelectRequestException(String message) {
		super(message);
	}
	
	public HsmsSsPassiveReceiveNotSelectRequestException(Throwable cause) {
		super(cause);
	}
	
	public HsmsSsPassiveReceiveNotSelectRequestException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
