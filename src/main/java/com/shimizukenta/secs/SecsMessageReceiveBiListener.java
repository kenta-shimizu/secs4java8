package com.shimizukenta.secs;

import java.util.EventListener;

/**
 * SecsMessage receive Listener.
 * 
 * <p>
 * This interface is used in {@link SecsCommunicator#addSecsMessageReceiveBiListener(SecsMessageReceiveBiListener)}<br />
 * Receive-Message is only Primary-SecsMessage.<br />
 * </p>
 * 
 * @author kenta-shimizu
 */
public interface SecsMessageReceiveBiListener extends EventListener {
	
	/**
	 * Putter Received Primary-SecsMessage and SecsCommunicator.
	 * 
	 * @param message only Primary-SecsMessage
	 * @param communicator the SecsCommunicator
	 */
	public void received(SecsMessage message, SecsCommunicator communicator);

}
