package com.shimizukenta.secs.util;

import java.util.EventListener;

import com.shimizukenta.secs.GemAccessor;

/**
 * 
 * @author kenta-shimizu
 *
 */
public interface EntityCommunicatableStateChangeListener extends EventListener {
	
	public void changed(boolean communicatable, GemAccessor comm);
}
