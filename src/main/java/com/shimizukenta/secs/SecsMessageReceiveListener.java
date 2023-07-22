package com.shimizukenta.secs;

import java.util.EventListener;

/**
 * SecsMessage receive Listener.
 * 
 * <p>
 * This interface is used in {@link SecsCommunicator#addSecsMessageReceiveListener(SecsMessageReceiveListener)}<br />
 * Receive-Message is only Primary-SecsMessage.<br />
 * </p>
 * 
 * @author kenta-shimizu
 */
public interface SecsMessageReceiveListener extends EventListener {
	
	/**
	 * Putter Received Primary-SecsMessage.
	 * 
	 * @param message only Primary-SecsMessage
	 */
	public void received(SecsMessage message);
	
}
