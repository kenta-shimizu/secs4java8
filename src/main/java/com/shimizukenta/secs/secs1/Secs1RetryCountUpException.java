package com.shimizukenta.secs.secs1;

/**
 * Secs1RetryCountUpException.
 * 
 * @author kenta-shimizu
 *
 */
public class Secs1RetryCountUpException extends Secs1Exception {
	
	private static final long serialVersionUID = 2922346356930475590L;
	
	/**
	 * Constructor.
	 * 
	 * @param retry the retry count
	 */
	public Secs1RetryCountUpException(int retry) {
		super("retry=" + retry);
	}}
