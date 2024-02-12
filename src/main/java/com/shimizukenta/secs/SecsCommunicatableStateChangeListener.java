package com.shimizukenta.secs;

import java.util.EventListener;

/**
 * SECS-Communicate-State Change Listener.
 * 
 * @author kenta-shimizu
 *
 */
public interface SecsCommunicatableStateChangeListener extends EventListener {
	
	/**
	 * SECS-Communicate-State Changed.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * pass through quickly.<br />
	 * </p>
	 * 
	 * @param communicatable {@code true} if state is communicatable
	 */
	public void changed(boolean communicatable);
}
