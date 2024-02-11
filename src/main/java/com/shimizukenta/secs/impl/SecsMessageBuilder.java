package com.shimizukenta.secs.impl;

import com.shimizukenta.secs.SecsCommunicatorConfigValueGettable;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.secs2.Secs2;

/**
 * SsecsMeessageBuilder.
 * 
 * @author kenta-shimizu
 *
 * @param <M> the Secs-Message extends
 * @param <C> the SecsCommunicator extends
 */
public interface SecsMessageBuilder<M extends SecsMessage, C extends SecsCommunicatorConfigValueGettable> {
	
	public M buildDataMessage(C communicator, int strm, int func, boolean wbit);
	
	public M buildDataMessage(C communicator, int strm, int func, boolean wbit, Secs2 body);
	
	public M buildDataMessage(C communicator, SecsMessage primaryMsg, int strm, int func, boolean wbit);
	
	public M buildDataMessage(C communicator, SecsMessage primaryMsg, int strm, int func, boolean wbit, Secs2 body);
}
