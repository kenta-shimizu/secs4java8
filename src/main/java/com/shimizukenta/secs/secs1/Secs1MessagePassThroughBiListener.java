package com.shimizukenta.secs.secs1;

import java.util.EventListener;

/**
 * Secs1MessagePassThroughBiListener.
 * 
 * @author kenta-shimizu
 *
 */
@Deprecated
public interface Secs1MessagePassThroughBiListener extends EventListener {
	
	/**
	 * Pass through.
	 * 
	 * @param message SECS-I message
	 * @param communicator the Secs1Communicator
	 */
	public void passThrough(Secs1Message message, Secs1Communicator communicator);
	
}
