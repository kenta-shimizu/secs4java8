package com.shimizukenta.secs.hsmsgs;

import com.shimizukenta.secs.hsms.HsmsException;

/**
 * HSMS-GS Unknown Session-ID Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class HsmsGsUnknownSessionIdException extends HsmsException {
	
	private static final long serialVersionUID = -3532958513890338098L;
	
	/**
	 * Constructor.
	 * 
	 * @param id Session-ID
	 */
	public HsmsGsUnknownSessionIdException(int id) {
		super("Unknown Session-ID: " + id);
	}
	
}
