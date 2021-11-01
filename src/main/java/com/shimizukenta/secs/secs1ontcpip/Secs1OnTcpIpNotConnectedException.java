package com.shimizukenta.secs.secs1ontcpip;

import com.shimizukenta.secs.secs1.Secs1Message;
import com.shimizukenta.secs.secs1.Secs1SendMessageException;

public class Secs1OnTcpIpNotConnectedException extends Secs1SendMessageException {
	
	private static final long serialVersionUID = 7138823455190061871L;
	
	public Secs1OnTcpIpNotConnectedException(Secs1Message msg) {
		super(msg);
	}

	public Secs1OnTcpIpNotConnectedException(Secs1Message msg, Throwable cause) {
		super(msg, cause);
	}

}
