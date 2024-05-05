package com.shimizukenta.secs;

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
public interface SecsMessagePassThroughObservable {
	
	/**
	 * Add Listener to get SecsMesssage before sending.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if add success.
	 */
	public boolean addTrySendSecsMessagePassThroughListener(SecsMessagePassThroughListener<? super SecsMessage> listener);
	
	/**
	 * Remove Listener.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeTrySendSecsMessagePassThroughListener(SecsMessagePassThroughListener<? super SecsMessage> listener);	
	
	
	/**
	 * Add Listener to get sended SecsMesssage.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addSendedSecsMessagePassThroughListener(SecsMessagePassThroughListener<? super SecsMessage> listener);
	
	/**
	 * Remove Listener.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeSendedSecsMessagePassThroughListener(SecsMessagePassThroughListener<? super SecsMessage> listener);	
	
	
	/**
	 * Add Listener to receive both Primary and Reply Message.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addReceiveSecsMessagePassThroughListener(SecsMessagePassThroughListener<? super SecsMessage> listener);
	
	/**
	 * Remove BiListener.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeReceiveSecsMessagePassThroughListener(SecsMessagePassThroughListener<? super SecsMessage> listener);
	
}
