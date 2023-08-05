package com.shimizukenta.secs.secs1;

import java.util.Optional;

import com.shimizukenta.secs.SecsCommunicator;

/**
 * This interface is implementation of SECS-I (SEMI-E4).
 * 
 * @author kenta-shimizu
 *
 */
public interface Secs1Communicator extends SecsCommunicator {
	
	/**
	 * Send SECS-I-Message.
	 * 
	 * <p>
	 * Send Secs1Message,<br />
	 * Blocking-method.<br />
	 * Wait until sended Primay-Message and Reply-Secs1Message received if exist.
	 * </p>
	 * 
	 * @param secs1Message SECS-I Message
	 * @return reply-Secs1Message if exist
	 * @throws Secs1SendMessageException if SECS-I Message send failed
	 * @throws Secs1WaitReplyMessageException if SECS-I wait reply Message failed
	 * @throws Secs1Exception if SECS-I communicate failed
	 * @throws InterruptedException if interrupted
	 */
	public Optional<Secs1Message> send(Secs1Message secs1Message)
			throws Secs1SendMessageException,
			Secs1WaitReplyMessageException,
			Secs1Exception,
			InterruptedException;
	
	
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
	
	
	/* try send SECS-I Message Pass through */
	
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
	 * Add Listener to get Secs1Messsage before sending.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if add success.
	 */
	public boolean addTrySendSecs1MessagePassThroughBiListener(Secs1MessagePassThroughBiListener biListener);
	
	/**
	 * Remove Listener.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeTrySendSecs1MessagePassThroughBiListener(Secs1MessagePassThroughBiListener biListener);	
	
	
	/* Sended SECS-I Message Pass Throught */
	
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
	 * Add Listener to get sended Secs1Messsage.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addSendedSecs1MessagePassThroughBiListener(Secs1MessagePassThroughBiListener biListener);
	
	/**
	 * Remove Listener.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeSendedSecs1MessagePassThroughBiListener(Secs1MessagePassThroughBiListener biListener);	
	
	
	/* Receive SECS-I Message Pass Through */
	
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
	 * Add BiListener to receive both Primary and Reply Message.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addReceiveSecs1MessagePassThroughBiListener(Secs1MessagePassThroughBiListener biListener);
	
	/**
	 * Remove Listener.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeReceiveSecs1MessagePassThroughBiListener(Secs1MessagePassThroughBiListener biListener);
	
}
