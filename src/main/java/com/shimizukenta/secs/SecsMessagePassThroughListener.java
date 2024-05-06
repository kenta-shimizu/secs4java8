package com.shimizukenta.secs;

import java.util.EventListener;

/**
 * SecsMessage pass through listener.
 * 
 * @author kenta-shimizu
 *
 * @param <T> type.
 */
public interface SecsMessagePassThroughListener<T> extends EventListener {
	
	/**
	 * Pass-through SECS-Message Listener.
	 * 
	 * @param message the SecsMessage
	 */
	public void passThrough(T message);
}
