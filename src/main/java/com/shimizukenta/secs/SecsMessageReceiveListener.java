package com.shimizukenta.secs;

import java.util.EventListener;

/**
 * SecsMessage receive Listener.
 * 
 * <p>
 * This interface is used in {@link SecsCommunicator#addSecsMessageReceiveListener(SecsMessageReceiveListener)}<br />
 * Receive-Message is only Primary-Message.<br />
 * </p>
 * 
 * @author kenta-shimizu
 */
public interface SecsMessageReceiveListener extends EventListener {
	
	/**
	 * Putter Received Primary-Message.
	 * 
	 * <p>
	 * Not accept {@code null}.<br />
	 * </p>
	 * 
	 * @param message only Primary-Message
	 */
	public void received(SecsMessage message);
}
