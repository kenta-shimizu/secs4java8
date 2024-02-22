package com.shimizukenta.secs.util;

import java.util.EventListener;

import com.shimizukenta.secs.SecsGemAccessor;

/**
 * 
 * @author kenta-shimizu
 *
 */
public interface EntityCommunicatableStateChangeListener extends EventListener {
	
	public void changed(boolean communicatable, SecsGemAccessor comm);
}
