package com.shimizukenta.secs.util;

import java.util.EventListener;

import com.shimizukenta.secs.SecsCommunicator;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.secs2.Secs2Exception;

/**
 * 
 * @author kenta-shimizu
 *
 */
public interface EntityMessageReceiveListener extends EventListener {
	
	public void received(SecsMessage msg, SecsCommunicator comm) throws Secs2Exception, InterruptedException;
}
