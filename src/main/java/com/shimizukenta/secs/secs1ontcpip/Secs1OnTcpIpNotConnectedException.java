package com.shimizukenta.secs.secs1ontcpip;

import com.shimizukenta.secs.secs1.Secs1Message;
import com.shimizukenta.secs.secs1.Secs1SendMessageException;

public class Secs1OnTcpIpNotConnectedException extends Secs1SendMessageException {
	
	private static final long serialVersionUID = 5625836413996893863L;

	public Secs1OnTcpIpNotConnectedException() {
		super();
	}

	public Secs1OnTcpIpNotConnectedException(String message) {
		super(message);
	}

	public Secs1OnTcpIpNotConnectedException(Throwable cause) {
		super(cause);
	}

	public Secs1OnTcpIpNotConnectedException(String message, Throwable cause) {
		super(message, cause);
	}

	public Secs1OnTcpIpNotConnectedException(Secs1Message msg) {
		super(msg);
	}

	public Secs1OnTcpIpNotConnectedException(Secs1Message msg, Throwable cause) {
		super(msg, cause);
	}

}
