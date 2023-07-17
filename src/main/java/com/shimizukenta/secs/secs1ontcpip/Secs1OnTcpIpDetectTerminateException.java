package com.shimizukenta.secs.secs1ontcpip;

import com.shimizukenta.secs.secs1.Secs1SendByteException;

/**
 * SECS-I-on-TCP/IP detect terminate Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class Secs1OnTcpIpDetectTerminateException extends Secs1SendByteException {
	
	private static final long serialVersionUID = 4719709951319519171L;
	
	/**
	 * Constructor.
	 * 
	 */
	public Secs1OnTcpIpDetectTerminateException() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 */
	public Secs1OnTcpIpDetectTerminateException(String message) {
		super(message);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param cause the cause
	 */
	public Secs1OnTcpIpDetectTerminateException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 * @param cause the cause
	 */
	public Secs1OnTcpIpDetectTerminateException(String message, Throwable cause) {
		super(message, cause);
	}

}
