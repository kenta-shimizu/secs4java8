package com.shimizukenta.secs.secs1;

import com.shimizukenta.secs.SecsMessageObservable;

public interface Secs1MessageObservable extends SecsMessageObservable {
	
	/* Secs-Message Receive Listener */
	
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
	public boolean addSecs1MessageReceiveListener(Secs1MessageReceiveListener listener);
	
	/**
	 * Remove Listener.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeSecs1MessageReceiveListener(Secs1MessageReceiveListener listener);
	
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
	public boolean addSecs1MessageReceiveBiListener(Secs1MessageReceiveBiListener biListener);
	
	/**
	 * Remove Listener.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeSecs1MessageReceiveBiListener(Secs1MessageReceiveBiListener biListener);
	
	
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
	
	
	/**
	 * Add Listener to get Secs1Messsage before sending.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if add success.
	 */
	@Deprecated
	default public boolean addTrySendSecs1MessagePassThroughBiListener(Secs1MessagePassThroughBiListener biListener) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Remove Listener.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	@Deprecated
	default public boolean removeTrySendSecs1MessagePassThroughBiListener(Secs1MessagePassThroughBiListener biListener) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Add Listener to get sended Secs1Messsage.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if add success
	 */
	@Deprecated
	default public boolean addSendedSecs1MessagePassThroughBiListener(Secs1MessagePassThroughBiListener biListener) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Remove Listener.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	@Deprecated
	default public boolean removeSendedSecs1MessagePassThroughBiListener(Secs1MessagePassThroughBiListener biListener) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Add BiListener to receive both Primary and Reply Message.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if add success
	 */
	@Deprecated
	default public boolean addReceiveSecs1MessagePassThroughBiListener(Secs1MessagePassThroughBiListener biListener) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Remove Listener.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	@Deprecated
	default public boolean removeReceiveSecs1MessagePassThroughBiListener(Secs1MessagePassThroughBiListener biListener) {
		throw new UnsupportedOperationException();
	}
	
}
