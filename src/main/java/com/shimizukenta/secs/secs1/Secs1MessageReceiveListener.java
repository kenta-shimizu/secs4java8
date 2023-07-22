package com.shimizukenta.secs.secs1;

import java.util.EventListener;

/**
 * Secs1Message receive Listener.
 * 
 * <p>
 * This interface is used in {@link Secs1Communicator#addSecs1MessageReceiveListener(Secs1MessageReceiveListener)}<br />
 * Receive-Message is only Primary-Secs1Message.<br />
 * </p>
 * 
 * @author kenta-shimizu
 */
public interface Secs1MessageReceiveListener extends EventListener {
	
	/**
	 * Putter Received Primary-Secs1Message.
	 * 
	 * @param message only Primary-Secs1Message
	 */
	public void received(Secs1Message message);
	
}
