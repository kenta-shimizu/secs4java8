package com.shimizukenta.secs.secs1;

import java.util.EventListener;

/**
 * 
 * @author kenta-shimizu
 *
 */
public interface Secs1MessagePassThroughListener extends EventListener {
	
	/**
	 * Pass through.
	 * 
	 * @param message SECS-I message
	 */
	public void passThrough(Secs1Message message);
}
