package com.shimizukenta.secs.hsmsgs;

/**
 * Add Session-ID IllegalArgumentException.
 * 
 * @author kenta-shimizu
 *
 */
public class AddSessionIdIllegalArgumentException extends IllegalArgumentException {
	
	private static final long serialVersionUID = 6236346097924069556L;
	
	/**
	 * Constructor.
	 * 
	 * @param sessionId Session-ID
	 */
	public AddSessionIdIllegalArgumentException(int sessionId) {
		super("Session-ID is in 0 - 65535, id=" + sessionId);
	}

}
