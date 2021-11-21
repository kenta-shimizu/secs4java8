package com.shimizukenta.secs.hsms;

import java.util.EventListener;

/**
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsCommunicateStateChangeListener extends EventListener {
	
	public void changed(HsmsCommunicateState state);
}
