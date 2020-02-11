package com.shimizukenta.secs;

import java.util.EventListener;

public interface SecsCommunicatableStateChangeListener extends EventListener {
	
	/**
	 * Blocking-method<br />
	 * pass through quickly.
	 * 
	 * @param communicatable
	 */
	public void changed(boolean communicatable);
}
