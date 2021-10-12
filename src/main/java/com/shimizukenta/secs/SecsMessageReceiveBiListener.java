package com.shimizukenta.secs;

import java.util.EventListener;

/**
 * SecsMessage receive Listener.
 * 
 * <p>
 * This interface is used in {@link SecsCommunicator#addSecsMessageReceiveListener(SecsMessageReceiveBiListener)}<br />
 * Receive-Message is only Primary-Message.<br />
 * </p>
 * 
 * @author kenta-shimizu
 */
public interface SecsMessageReceiveBiListener extends EventListener {
	
	/**
	 * Putter Received Primary-Message and Communicator.
	 * 
	 * <p>
	 * Not accept {@code null}.<br />
	 * </p>
	 * 
	 * @param message only Primary-Message
	 * @param communicator
	 */
	public void received(SecsMessage message, SecsCommunicator communicator);

}
