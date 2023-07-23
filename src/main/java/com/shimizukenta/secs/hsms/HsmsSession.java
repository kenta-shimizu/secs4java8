package com.shimizukenta.secs.hsms;

/**
 * HSMS Session interface.
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsSession extends HsmsCommunicator {
	
	/**
	 * SELECT.REQ.
	 * 
	 * @return true if SELECT Success, otherwise false
	 * @throws InterruptedException if interrupted
	 */
	public boolean select() throws InterruptedException;
	
	/**
	 * DESELECT.REQ.
	 * 
	 * @return true if DESELECT Success, otherwise false
	 * @throws InterruptedException if interrupted
	 */
	public boolean deselect() throws InterruptedException;
	
	/**
	 * SEPARATE.REQ.
	 * 
	 * @return true if SEPARATE Success, otherwise false
	 * @throws InterruptedException if interrupted
	 */
	public boolean separate() throws InterruptedException;
	
	/**
	 * Add Listener to receive Primary-Message.
	 * 
	 * <p>
	 * This Listener not receive Reply-Message.<br />
	 * </p>
	 * 
	 * @param biListener the HSMS Message ans HSMS-Session communicator listener
	 * @return {@code true} if add success
	 * @throws NullPointerException if biListener is null
	 */
	public boolean addHsmsMessageReceiveBiListener(HsmsSessionMessageReceiveBiListener biListener);
	
	/**
	 * Remove listener.
	 * 
	 * @param biListener the HSMS Message ans HSMS-Session communicator listener
	 * @return {@code true} if remove success
	 * @throws NullPointerException if biListener is null
	 */
	public boolean removeHsmsMessageReceiveBiListener(HsmsSessionMessageReceiveBiListener biListener);
	
	/**
	 * Add Listener to get communicate-state-changed.
	 * 
	 * <p>
	 * Blocking-Listener.<br />
	 * Pass through quickly.<br />
	 * </p>
	 * 
	 * @param biListener the state change listener
	 * @return {@code true} if add success
	 * @throws NullPointerException if biListener is null
	 */
	public boolean addHsmsCommunicateStateChangeBiListener(HsmsSessionCommunicateStateChangeBiListener biListener);
	
	/**
	 * Remove listener.
	 * 
	 * @param biListener the state change listener
	 * @return {@code true} if remove success
	 * @throws NullPointerException if biListener is null
	 */
	public boolean removeHsmsCommunicateStateChangeBiListener(HsmsSessionCommunicateStateChangeBiListener biListener);
	
}
