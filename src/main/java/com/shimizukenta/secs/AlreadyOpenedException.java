package com.shimizukenta.secs;

import java.io.IOException;

/**
 * Already opened Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class AlreadyOpenedException extends IOException {
	
	private static final long serialVersionUID = -5947426384188466642L;
	
	/**
	 * Constructor.
	 * 
	 */
	public AlreadyOpenedException() {
		super();
	}

}
