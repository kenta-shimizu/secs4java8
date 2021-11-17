package com.shimizukenta.secs.hsms;

import java.util.EventListener;

/**
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsMessagePassThroughListener extends EventListener {
	
	public void passThrough(HsmsMessage message);
}
