package com.shimizukenta.secs.hsms;

import java.util.Optional;

import com.shimizukenta.secs.SecsCommunicator;

/**
 * This interface is implementation of HSMS (SEMI-E37).
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsCommunicator extends SecsCommunicator {

	/**
	 * Add Listener to get communicate-state-changed.
	 * 
	 * <p>
	 * Blocking-Listener.<br />
	 * Pass through quickly.<br />
	 * </p>
	 * 
	 * @param listener the state change listener
	 * @return true if add success
	 */
	public boolean addHsmsCommunicateStateChangeListener(HsmsCommunicateStateChangeListener listener);
	
	/**
	 * Remove Listener.
	 * 
	 * @param listener the state change listener
	 * @return true if remove success
	 */
	public boolean removeHsmsCommunicateStateChangeListener(HsmsCommunicateStateChangeListener listener);
	
	/**
	 * HSMS-SS linktest.
	 * 
	 * <p>
	 * Blocking-method.
	 * </p>
	 * 
	 * @return {@code true} if success
	 * @throws InterruptedException if interrupted
	 */
	public boolean linktest() throws InterruptedException;
	
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
	public boolean addHsmsMessageReceiveListener(HsmsMessageReceiveListener listener);
	
	/**
	 * Remove Listener.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeHsmsMessageReceiveListener(HsmsMessageReceiveListener listener);
	
	
	/**
	 * Add listener.
	 * 
	 * @param lstnr the pass through message listener
	 * @return true if add success
	 */
	public boolean addTrySendHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr);
	
	/**
	 * Remove listener.
	 * 
	 * @param lstnr the pass through message listener
	 * @return true if remove success
	 */
	public boolean removeTrySendHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr);	
	
	/**
	 * Add listener.
	 * 
	 * @param lstnr the pass through message listener
	 * @return true if add success
	 */
	public boolean addSendedHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr);
	
	/**
	 * Remove listener.
	 * 
	 * @param lstnr the pass through message listener
	 * @return true if remove success
	 */
	public boolean removeSendedHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr);	
	
	/**
	 * Add listener.
	 * 
	 * @param lstnr the pass through message listener
	 * @return true if add success
	 */
	public boolean addReceiveHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr);
	
	/**
	 * Remove listener.
	 * 
	 * @param lstnr the pass through message listener
	 * @return true if remove success
	 */
	public boolean removeReceiveHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr);
	
	
	/**
	 * Send HSMS-Message and receive Reply-Message if exist.
	 * 
	 * @param msg the HSMS Message
	 * @return Optional has value if Reply-Message exist
	 * @throws HsmsSendMessageException if send message failed
	 * @throws HsmsWaitReplyMessageException if reply message timeout
	 * @throws HsmsException if HSMS communicate failed
	 * @throws InterruptedException if interrupted
	 */
	public Optional<HsmsMessage> send(HsmsMessage msg)
			throws HsmsSendMessageException, HsmsWaitReplyMessageException, HsmsException,
			InterruptedException;
	
}
