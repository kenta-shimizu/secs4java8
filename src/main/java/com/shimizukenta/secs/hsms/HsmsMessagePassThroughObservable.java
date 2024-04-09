package com.shimizukenta.secs.hsms;

import com.shimizukenta.secs.SecsMessagePassThroughListener;
import com.shimizukenta.secs.SecsMessagePassThroughObservable;

/**
 * HSMS Message pass through Observable.
 * 
 * <p>
 * Includes
 * <ul>
 * <li>receive</li>
 * <li>try send</li>
 * <li>sended</li>
 * </ul>
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsMessagePassThroughObservable extends SecsMessagePassThroughObservable {
	
	/**
	 * Add listener.
	 * 
	 * @param listener the HSMS Message pass through listener
	 * @return true if add success
	 */
	public boolean addTrySendHsmsMessagePassThroughListener(SecsMessagePassThroughListener<? super HsmsMessage> listener);
	
	/**
	 * Remove listener.
	 * 
	 * @param listener the HSMS Message pass through listener
	 * @return true if remove success
	 */
	public boolean removeTrySendHsmsMessagePassThroughListener(SecsMessagePassThroughListener<? super HsmsMessage> listener);
	
	
	/**
	 * Add listener.
	 * 
	 * @param listener the HSMS Message pass through listener
	 * @return true if add success
	 */
	public boolean addSendedHsmsMessagePassThroughListener(SecsMessagePassThroughListener<? super HsmsMessage> listener);
	
	/**
	 * Remove listener.
	 * 
	 * @param listener the HSMS Message pass through listener
	 * @return true if remove success
	 */
	public boolean removeSendedHsmsMessagePassThroughListener(SecsMessagePassThroughListener<? super HsmsMessage> listener);
	
	
	/**
	 * Add listener.
	 * 
	 * @param listener the HSMS Message pass through listener
	 * @return true if add success
	 */
	public boolean addReceiveHsmsMessagePassThroughListener(SecsMessagePassThroughListener<? super HsmsMessage> listener);
	
	/**
	 * Remove listener.
	 * 
	 * @param listener the HSMS Message pass through listener
	 * @return true if remove success
	 */
	public boolean removeReceiveHsmsMessagePassThroughListener(SecsMessagePassThroughListener<? super HsmsMessage> listener);
	
}
