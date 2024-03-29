/**
 * 
 */
package com.shimizukenta.secs.secs1;

import com.shimizukenta.secs.SecsMessagePassThroughObservable;

/**
 * SECS-Message path through Observable.
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
public interface Secs1MessagePassThroughObservable extends SecsMessagePassThroughObservable {
	
	/**
	 * Add listener.
	 * 
	 * @param listener the SECS-I Message pass through listener
	 * @return true if add success
	 */
	public boolean addTrySendSecs1MessagePassThroughListener(Secs1MessagePassThroughListener listener);
	
	/**
	 * Remove listener.
	 * 
	 * @param listener the SECS-I Message pass through listener
	 * @return true if remove success
	 */
	public boolean removeTrySendSecs1MessagePassThroughListener(Secs1MessagePassThroughListener listener);
	
	
	/**
	 * Add listener.
	 * 
	 * @param listener the SECS-I Message pass through listener
	 * @return true if add success
	 */
	public boolean addSendedSecs1MessagePassThroughListener(Secs1MessagePassThroughListener listener);
	
	/**
	 * Remove listener.
	 * 
	 * @param listener the SECS-I Message pass through listener
	 * @return true if remove success
	 */
	public boolean removeSendedSecs1MessagePassThroughListener(Secs1MessagePassThroughListener listener);
	
	
	/**
	 * Add listener.
	 * 
	 * @param listener the SECS-I Message pass through listener
	 * @return true if add success
	 */
	public boolean addReceiveSecs1MessagePassThroughListener(Secs1MessagePassThroughListener listener);
	
	/**
	 * Remove listener.
	 * 
	 * @param listener the SECS-I Message pass through listener
	 * @return true if remove success
	 */
	public boolean removeReceiveSecs1MessagePassThroughListener(Secs1MessagePassThroughListener listener);
	
}
