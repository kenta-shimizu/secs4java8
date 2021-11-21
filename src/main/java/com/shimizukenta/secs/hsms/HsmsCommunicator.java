package com.shimizukenta.secs.hsms;

import com.shimizukenta.secs.SecsCommunicator;
import com.shimizukenta.secs.SecsException;

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
	
	public boolean addTrySendHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr);
	public boolean removeTrySendHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr);	
	
	public boolean addSendedHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr);
	public boolean removeSendedHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr);	
	
	public boolean addReceiveHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr);
	public boolean removeReceiveHsmsMessagePassThroughListener(HsmsMessagePassThroughListener lstnr);
	
}
