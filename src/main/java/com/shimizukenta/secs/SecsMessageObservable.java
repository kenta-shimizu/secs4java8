package com.shimizukenta.secs;

/**
 * SecsMessage Observer methods.
 * 
 * @author kenta-shimizu
 *
 */
public interface SecsMessageObservable {
	
	/**
	 * Add Listener to receive Primary-Message.
	 * 
	 * <p>
	 * This Listener not receive Reply-Message.<br />
	 * </p>
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addSecsMessageReceiveListener(SecsMessageReceiveListener listener);
	
	/**
	 * Remove Listener.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeSecsMessageReceiveListener(SecsMessageReceiveListener listener);
	
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
	
	
	
	/* Deprecateds */
	
	/**
	 * Add Listener to get SecsMesssage before sending.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if add success.
	 */
	@Deprecated
	default public boolean addTrySendSecsMessagePassThroughBiListener(SecsMessagePassThroughBiListener biListener) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Remove Listener.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	@Deprecated
	default public boolean removeTrySendSecsMessagePassThroughBiListener(SecsMessagePassThroughBiListener biListener) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Add Listener to get sended SecsMesssage.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if add success
	 */
	@Deprecated
	default public boolean addSendedSecsMessagePassThroughBiListener(SecsMessagePassThroughBiListener biListener) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Remove Listener.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	@Deprecated
	default public boolean removeSendedSecsMessagePassThroughBiListener(SecsMessagePassThroughBiListener biListener) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Add BiListener to receive both Primary and Reply Message.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if add success
	 */
	@Deprecated
	default public boolean addReceiveSecsMessagePassThroughBiListener(SecsMessagePassThroughBiListener biListener) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Remove Listener.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	@Deprecated
	default public boolean removeReceiveSecsMessagePassThroughBiListener(SecsMessagePassThroughBiListener biListener) {
		throw new UnsupportedOperationException();
	}
	
}
