package com.shimizukenta.secs.secs1;

import java.util.EventListener;

/**
 * 
 * @author kenta-shimizu
 *
 */
public interface Secs1MessagePassThroughListener extends EventListener {
	
	public void passThrough(Secs1Message message);
}
