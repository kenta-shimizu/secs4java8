package com.shimizukenta.secs.secs1ontcpip;

import com.shimizukenta.secs.secs1.Secs1SendByteException;

/**
 * SECS-I-on-TCP/IP NOT connected Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class Secs1OnTcpIpNotConnectedException extends Secs1SendByteException {
	
	private static final long serialVersionUID = 1762320175657050884L;
	
	/**
	 * Constructor.
	 * 
	 */
	public Secs1OnTcpIpNotConnectedException() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 */
	public Secs1OnTcpIpNotConnectedException(String message) {
		super(message);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param cause the cause
	 */
	public Secs1OnTcpIpNotConnectedException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 * @param cause the cause
	 */
	public Secs1OnTcpIpNotConnectedException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
