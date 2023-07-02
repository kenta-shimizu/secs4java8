package com.shimizukenta.secs;

import java.io.IOException;

/**
 * ExecutorService shutdown failed Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class ExecutorServiceShutdownFailedException extends IOException {

	private static final long serialVersionUID = -4385999042815468643L;
	
	/**
	 * Constructor.
	 * 
	 */
	public ExecutorServiceShutdownFailedException() {
		super();
	}
	
}
