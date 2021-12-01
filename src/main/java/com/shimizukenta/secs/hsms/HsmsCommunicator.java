package com.shimizukenta.secs.hsms;

import java.util.Optional;

import com.shimizukenta.secs.SecsCommunicator;
import com.shimizukenta.secs.SecsException;

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
	 * @param listener
	 * @return true if add success
	 */
	public boolean addHsmsCommunicateStateChangeListener(HsmsCommunicateStateChangeListener listener);
	
	/**
	 * Remove Listener.
	 * 
	 * @param listener
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
	 * @throws InterruptedException
	 * @throws SecsException
	 */
	public boolean linktest() throws InterruptedException;
	
	/**
	 * Add listener.
	 * 
	 * @param lstnr
	 * @return true if add success
	 */
	public boolean addTrySendHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr);
	
	/**
	 * Remove listener.
	 * 
	 * @param lstnr
	 * @return true if remove success
	 */
	public boolean removeTrySendHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr);	
	
	/**
	 * Add listener.
	 * 
	 * @param lstnr
	 * @return true if add success
	 */
	public boolean addSendedHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr);
	
	/**
	 * Remove listener.
	 * 
	 * @param lstnr
	 * @return true if remove success
	 */
	public boolean removeSendedHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr);	
	
	/**
	 * Add listener.
	 * 
	 * @param lstnr
	 * @return true if add success
	 */
	public boolean addReceiveHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr);
	
	/**
	 * Remove listener.
	 * 
	 * @param lstnr
	 * @return true if remove success
	 */
	public boolean removeReceiveHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr);
	
	/**
	 * Send HSMS-Message and receive Reply-Message if exist.
	 * 
	 * @param msg
	 * @return Optional has value if Reply-Message exist
	 * @throws HsmsSendMessageException
	 * @throws HsmsWaitReplyMessageException
	 * @throws HsmsException
	 * @throws InterruptedException
	 */
	public Optional<HsmsMessage> send(HsmsMessage msg)
			throws HsmsSendMessageException, HsmsWaitReplyMessageException, HsmsException,
			InterruptedException;
	
}
