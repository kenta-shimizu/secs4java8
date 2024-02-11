package com.shimizukenta.secs;

/**
 * SecsMessage Listenable.
 * 
 * @author kenta-shimizu
 *
 */
public interface SecsMessageListenable {
	
	/**
	 * Add Listener to receive Primary-Message.
	 * 
	 * <p>
	 * This Listener not receive Reply-Message.<br />
	 * </p>
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addSecsMessageReceiveBiListener(SecsMessageReceiveBiListener biListener);
	
	/**
	 * Remove Listener.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeSecsMessageReceiveBiListener(SecsMessageReceiveBiListener biListener);
	
	
	
	/* Try-Send Secs-Message Pass-through Listener */
	
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
	 * Add Listener to get SecsMesssage before sending.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if add success.
	 */
	public boolean addTrySendSecsMessagePassThroughBiListener(SecsMessagePassThroughBiListener biListener);
	
	/**
	 * Remove Listener.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeTrySendSecsMessagePassThroughBiListener(SecsMessagePassThroughBiListener biListener);	

	
	/* Sended Secs-Message Pass-through Listener */
	
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
	 * Add Listener to get sended SecsMesssage.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addSendedSecsMessagePassThroughBiListener(SecsMessagePassThroughBiListener biListener);
	
	/**
	 * Remove Listener.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeSendedSecsMessagePassThroughBiListener(SecsMessagePassThroughBiListener biListener);	

	
	/* Receive Secs-Message Pass-through Listener */
	
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
	
	/**
	 * Add BiListener to receive both Primary and Reply Message.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addReceiveSecsMessagePassThroughBiListener(SecsMessagePassThroughBiListener biListener);
	
	/**
	 * Remove Listener.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeReceiveSecsMessagePassThroughBiListener(SecsMessagePassThroughBiListener biListener);


}
