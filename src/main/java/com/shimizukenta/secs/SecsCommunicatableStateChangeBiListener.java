package com.shimizukenta.secs;

import java.util.EventListener;

/**
 * SECS-Communicate-State Change Listener.
 * 
 * <p>
 * This interface is called in {@link SecsCommunicator#addSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeBiListener)}<br />
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public interface SecsCommunicatableStateChangeBiListener extends EventListener {
	
	/**
	 * SECS-Communicate-State Changed
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * pass through quickly.<br />
	 * </p>
	 * 
	 * @param communicatable {@code true} if state is communicatable
	 * @param communicaotr
	 */
	public void changed(boolean communicatable, SecsCommunicator communicator);
	
}
