package com.shimizukenta.secs;

import java.util.EventListener;

/**
 * SecsMessage receive Listener.
 * 
 * <p>
 * Receive-Message is only Primary-SecsMessage.<br />
 * </p>
 * 
 * @author kenta-shimizu
 */
public interface SecsMessageReceiveBiListener extends EventListener {
	
	/**
	 * Putter Received Primary-SecsMessage and GemAccessor.
	 * 
	 * @param message only Primary-SecsMessage
	 * @param accessor the GemAccessor
	 */
	public void received(SecsMessage message, SecsGemAccessor accessor);

}
