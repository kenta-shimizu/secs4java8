package com.shimizukenta.secs.secs1;

/**
 * Retry Count Illegal Argument Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class RetryCountIllegalArgumentException extends IllegalArgumentException {
	
	private static final long serialVersionUID = 4617129427581417717L;
	
	/**
	 * Constructor.
	 * 
	 * @param retryCount the SECS-I config Retry Count
	 */
	public RetryCountIllegalArgumentException(int retryCount) {
		super("retry is >= 0, count=" + retryCount);
	}
	
}
