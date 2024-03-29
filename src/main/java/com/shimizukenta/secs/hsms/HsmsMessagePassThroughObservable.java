/**
 * 
 */
package com.shimizukenta.secs.hsms;

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
	public boolean addTrySendHsmsMessagePassThroughListener(HsmsMessagePassThroughListener listener);
	
	/**
	 * Remove listener.
	 * 
	 * @param listener the HSMS Message pass through listener
	 * @return true if remove success
	 */
	public boolean removeTrySendHsmsMessagePassThroughListener(HsmsMessagePassThroughListener listener);
	
	
	/**
	 * Add listener.
	 * 
	 * @param listener the HSMS Message pass through listener
	 * @return true if add success
	 */
	public boolean addSendedHsmsMessagePassThroughListener(HsmsMessagePassThroughListener listener);
	
	/**
	 * Remove listener.
	 * 
	 * @param listener the HSMS Message pass through listener
	 * @return true if remove success
	 */
	public boolean removeSendedHsmsMessagePassThroughListener(HsmsMessagePassThroughListener listener);
	
	
	/**
	 * Add listener.
	 * 
	 * @param listener the HSMS Message pass through listener
	 * @return true if add success
	 */
	public boolean addReceiveHsmsMessagePassThroughListener(HsmsMessagePassThroughListener listener);
	
	/**
	 * Remove listener.
	 * 
	 * @param listener the HSMS Message pass through listener
	 * @return true if remove success
	 */
	public boolean removeReceiveHsmsMessagePassThroughListener(HsmsMessagePassThroughListener listener);
	
}
