package com.shimizukenta.secs;

import java.io.IOException;

/**
 * Already closed Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class AlreadyClosedException extends IOException {

	private static final long serialVersionUID = -6241055031788338779L;
	
	/**
	 * Constructor.
	 * 
	 */
	public AlreadyClosedException() {
		super();
	}
	
}
