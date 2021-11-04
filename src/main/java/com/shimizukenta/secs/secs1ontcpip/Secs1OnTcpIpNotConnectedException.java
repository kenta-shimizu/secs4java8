package com.shimizukenta.secs.secs1ontcpip;

import com.shimizukenta.secs.secs1.Secs1SendByteException;

public class Secs1OnTcpIpNotConnectedException extends Secs1SendByteException {
	
	private static final long serialVersionUID = 1762320175657050884L;
	
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
	
}
