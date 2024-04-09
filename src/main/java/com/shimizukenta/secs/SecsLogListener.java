package com.shimizukenta.secs;

import java.util.EventListener;

/**
 * SecsLog receive Listener.
 * 
 * <p>
 * This interface is used in {@link SecsCommunicator#addSecsLogListener(SecsLogListener)}<br />
 * </p>
 * 
 * @author kenta-shimizu
 *
 */

/**
 * SecsLog receive Listener.
 * 
 * <p>
 * This interface is used in {@link SecsCommunicator#addSecsLogListener(SecsLogListener)}<br />
 * </p>
 * 
 * @author shimizukenta
 *
 * @param <T> Type
 */
public interface SecsLogListener<T> extends EventListener {
	
	/**
	 * put received-SecsLog.
	 * 
	 * <p>
	 * Not accept {@code null}
	 * </p>
	 * 
	 * @param log the SecsLog
	 */
	public void received(T log);
	
}
