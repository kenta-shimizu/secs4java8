package com.shimizukenta.secs.hsms;

import com.shimizukenta.secs.SecsLogListener;
import com.shimizukenta.secs.SecsLogObservable;

/**
 * HSMS-Log Observable.
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsLogObservable extends SecsLogObservable {
	
	public boolean addTrySendHsmsMessagePassThroughLogListener(SecsLogListener<? super HsmsMessagePassThroughLog> listener);
	
	public boolean removeTrySendHsmsMessagePassThroughLogListener(SecsLogListener<? super HsmsMessagePassThroughLog> listener);
	
	public boolean addSendedHsmsMessagePassThroughLogListener(SecsLogListener<? super HsmsMessagePassThroughLog> listener);
	
	public boolean removeSendedHsmsMessagePassThroughLogListener(SecsLogListener<? super HsmsMessagePassThroughLog> listener);
	
	public boolean addReceiveHsmsMessagePassThroughLogListener(SecsLogListener<? super HsmsMessagePassThroughLog> listener);
	
	public boolean removeReceiveHsmsMessagePassThroughLogListener(SecsLogListener<? super HsmsMessagePassThroughLog> listener);
	
	public boolean addHsmsChannelConnectionLogListener(SecsLogListener<? super HsmsChannelConnectionLog> listener);
	
	public boolean removeHsmsChannelConnectionLogListener(SecsLogListener<? super HsmsChannelConnectionLog> listener);
	
	public boolean addHsmsSessionCommunicateStateLogListener(SecsLogListener<? super HsmsSessionCommunicateStateLog> listener);
	
	public boolean removeHsmsSessionCommunicateStateLogListener(SecsLogListener<? super HsmsSessionCommunicateStateLog> listener);
	
}
