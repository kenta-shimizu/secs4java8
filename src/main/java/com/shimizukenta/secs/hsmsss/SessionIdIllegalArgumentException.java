package com.shimizukenta.secs.hsmsss;

/**
 * HSMS-SS SEssion-D IllegalArgumentException.
 * 
 * @author kenta-shimizu
 *
 */
public class SessionIdIllegalArgumentException extends IllegalArgumentException {
	
	private static final long serialVersionUID = 4022072581260042725L;
	
	/**
	 * Constructor.
	 * 
	 * @param id SESSION-ID
	 */
	public SessionIdIllegalArgumentException(int id) {
		super("SESSION-ID is in 0 - 32767, id=" + id);
	}

}
