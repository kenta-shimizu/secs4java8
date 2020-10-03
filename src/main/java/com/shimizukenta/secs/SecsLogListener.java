package com.shimizukenta.secs;

import com.shimizukenta.secs.SecsCommunicator;

import java.util.EventListener;

/**
 * SecsLog receive Listener
 * 
 * <p>
 * This interface is used in {@link SecsCommunicator#addSecsLogListener(SecsLogListener)}<br />
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public interface SecsLogListener extends EventListener {
	
	/**
	 * put received-SecsLog.
	 * 
	 * <p>
	 * Not accept {@code null}
	 * </p>
	 * 
	 * @param log
	 */
	public void received(SecsLog log);
	
}
