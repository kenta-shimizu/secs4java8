package com.shimizukenta.secs.secs1;

import com.shimizukenta.secs.SecsMessagePassThroughListener;
import com.shimizukenta.secs.SecsMessagePassThroughObservable;

/**
 * SECS-Message path through Observable.
 * 
 * <p>
 * Includes
 * </p>
 * <ul>
 * <li>receive</li>
 * <li>try send</li>
 * <li>sended</li>
 * </ul>
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
	public boolean addTrySendSecs1MessagePassThroughListener(SecsMessagePassThroughListener<? super Secs1Message> listener);
	
	/**
	 * Remove listener.
	 * 
	 * @param listener the SECS-I Message pass through listener
	 * @return true if remove success
	 */
	public boolean removeTrySendSecs1MessagePassThroughListener(SecsMessagePassThroughListener<? super Secs1Message> listener);
	
	
	/**
	 * Add listener.
	 * 
	 * @param listener the SECS-I Message pass through listener
	 * @return true if add success
	 */
	public boolean addSendedSecs1MessagePassThroughListener(SecsMessagePassThroughListener<? super Secs1Message> listener);
	
	/**
	 * Remove listener.
	 * 
	 * @param listener the SECS-I Message pass through listener
	 * @return true if remove success
	 */
	public boolean removeSendedSecs1MessagePassThroughListener(SecsMessagePassThroughListener<? super Secs1Message> listener);
	
	
	/**
	 * Add listener.
	 * 
	 * @param listener the SECS-I Message pass through listener
	 * @return true if add success
	 */
	public boolean addReceiveSecs1MessagePassThroughListener(SecsMessagePassThroughListener<? super Secs1Message> listener);
	
	/**
	 * Remove listener.
	 * 
	 * @param listener the SECS-I Message pass through listener
	 * @return true if remove success
	 */
	public boolean removeReceiveSecs1MessagePassThroughListener(SecsMessagePassThroughListener<? super Secs1Message> listener);
	
}
