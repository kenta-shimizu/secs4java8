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
	
	/**
	 * Returns {@code true} if add Try-send HSMS Message pass through log listener success, otherwise {@code false}, pass through HSMS message.
	 * 
	 * @param listener the Try-send HSMS message pass through log listener
	 * @return true if add success, otherwise false
	 */
	public boolean addTrySendHsmsMessagePassThroughLogListener(SecsLogListener<? super HsmsMessagePassThroughLog> listener);
	
	/**
	 * Returns {@code trrue} if add success, otherwise {@code false}.
	 * 
	 * @param listener the Try-send HSMS message pass through log listener
	 * @return true if remove success, otherwise false
	 */
	public boolean removeTrySendHsmsMessagePassThroughLogListener(SecsLogListener<? super HsmsMessagePassThroughLog> listener);
	
	/**
	 * Returns {@code true} if add Sended HSMS Message pass through log listener success, otherwise {@code false}, pass through HSMS message.
	 * 
	 * @param listener the Sended HSMS message pass through log listener
	 * @return true if add success, otherwise false
	 */
	public boolean addSendedHsmsMessagePassThroughLogListener(SecsLogListener<? super HsmsMessagePassThroughLog> listener);
	
	/**
	 * Returns {@code trrue} if remove success, otherwise {@code false}.
	 * 
	 * @param listener the Sended HSMS message pass through log listener
	 * @return true if remove success, otherwise false
	 */
	public boolean removeSendedHsmsMessagePassThroughLogListener(SecsLogListener<? super HsmsMessagePassThroughLog> listener);
	
	/**
	 * Returns {@code true} if add Receive HSMS Message pass through log listener success, otherwise {@code false}, pass through HSMS message.
	 * 
	 * @param listener the Receive HSMS message pass through log listener
	 * @return true if add success, otherwise false
	 */
	public boolean addReceiveHsmsMessagePassThroughLogListener(SecsLogListener<? super HsmsMessagePassThroughLog> listener);
	
	/**
	 * Returns {@code trrue} if remove success, otherwise {@code false}.
	 * 
	 * @param listener the Receive HSMS message pass through log listener
	 * @return true if remove success, otherwise false
	 */
	public boolean removeReceiveHsmsMessagePassThroughLogListener(SecsLogListener<? super HsmsMessagePassThroughLog> listener);
	
	/**
	 * Returns {@code true} if add HSMS channel connection log listener success, otherwise {@code false}, receive connection log.
	 * 
	 * @param listener the HSMS channel connection log listener
	 * @return true if add success, otherwise false
	 */
	public boolean addHsmsChannelConnectionLogListener(SecsLogListener<? super HsmsChannelConnectionLog> listener);
	
	/**
	 * Returns {@code true} if add success, otherwise {@code false}.
	 * 
	 * @param listener the HSMS channel connection log listener
	 * @return true if remove success, otherwise false
	 */
	public boolean removeHsmsChannelConnectionLogListener(SecsLogListener<? super HsmsChannelConnectionLog> listener);
	
	/**
	 * Returns {@code true} if add HSMS communicate state log listener success, otherwise {@code false}, receive HSMS communicate state log.
	 * 
	 * @param listener the HSMS communicate state change log listener
	 * @return true if add success, otherwise false
	 */
	public boolean addHsmsSessionCommunicateStateLogListener(SecsLogListener<? super HsmsSessionCommunicateStateLog> listener);
	
	/**
	 * Returns {@code true} if add success, otherwise {@code false}.
	 * 
	 * @param listener the HSMS communicate state change log listener
	 * @return true if remove success, otherwise false
	 */
	public boolean removeHsmsSessionCommunicateStateLogListener(SecsLogListener<? super HsmsSessionCommunicateStateLog> listener);
	
}
