package com.shimizukenta.secs;

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
public interface SecsMessagePassThroughObservable {
	
	/**
	 * Add Listener to get SecsMesssage before sending.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if add success.
	 */
	public boolean addTrySendSecsMessagePassThroughListener(SecsMessagePassThroughListener listener);
	
	/**
	 * Remove Listener.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeTrySendSecsMessagePassThroughListener(SecsMessagePassThroughListener listener);	
	
	
	/**
	 * Add Listener to get sended SecsMesssage.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addSendedSecsMessagePassThroughListener(SecsMessagePassThroughListener listener);
	
	/**
	 * Remove Listener.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeSendedSecsMessagePassThroughListener(SecsMessagePassThroughListener listener);	
	
	
	/**
	 * Add Listener to receive both Primary and Reply Message.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addReceiveSecsMessagePassThroughListener(SecsMessagePassThroughListener listener);
	
	/**
	 * Remove BiListener.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeReceiveSecsMessagePassThroughListener(SecsMessagePassThroughListener listener);
	
}
