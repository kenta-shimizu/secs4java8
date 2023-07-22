package com.shimizukenta.secs;

import java.util.EventListener;

/**
 * SecsMessage and SecsCommunicator pass through listener.
 * 
 * @author kenta-shimizu
 *
 */
public interface SecsMessagePassThroughBiListener extends EventListener {
	
	/**
	 * Pass-through SECS-Message and SecsCommunicator Listener.
	 * 
	 * @param message the SecsMessage
	 * @param communicator the SecsCommunicator
	 */
	public void passThrough(SecsMessage message, SecsCommunicator communicator);
	
}
