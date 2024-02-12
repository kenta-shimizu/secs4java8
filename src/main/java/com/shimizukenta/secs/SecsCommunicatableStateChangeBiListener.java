package com.shimizukenta.secs;

import java.util.EventListener;

/**
 * SECS-Communicate-State Change Listener.
 * 
 * @author kenta-shimizu
 *
 */
public interface SecsCommunicatableStateChangeBiListener extends EventListener {
	
	/**
	 * SECS-Communicate-State Changed.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * pass through quickly.<br />
	 * </p>
	 * 
	 * @param communicatable true if state is communicatable
	 * @param communicator the GemAccessor
	 */
	public void changed(boolean communicatable, GemAccessor communicator);
	
}
