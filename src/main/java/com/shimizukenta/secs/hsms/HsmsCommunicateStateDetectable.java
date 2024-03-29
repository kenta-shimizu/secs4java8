/**
 * 
 */
package com.shimizukenta.secs.hsms;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.shimizukenta.secs.SecsCommunicateStateDetectable;

/**
 * HSMS-Communicate-State Detectable.
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsCommunicateStateDetectable extends SecsCommunicateStateDetectable {
	
	/**
	 * Returns HSMS communicate state.
	 * 
	 * @return HSMS communicate state.
	 */
	public HsmsCommunicateState getHsmsCommunicateState();
	
	/**
	 * Wait until equals state.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * If Already communicatable, do nothing.<br />
	 * </p>
	 * 
	 * @param state the HSMS communicate state
	 * @throws InterruptedException if interrupted
	 */
	public void waitUntilHsmsCommunicateState(HsmsCommunicateState state) throws InterruptedException;
	
	/**
	 * Wait until equals state.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * If Already communicatable, do nothing.<br />
	 * </p>
	 * 
	 * @param state the HSMS communicate state
	 * @param timeout the timeout value
	 * @param unit the timeout unit
	 * @throws InterruptedException if interrupted
	 * @throws TimeoutException if timeout
	 */
	public void waitUntilHsmsCommunicateState(HsmsCommunicateState state, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException;
	
	/**
	 * Wait until <strong>NOT</strong> equals state.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * If Already communicatable, do nothing.<br />
	 * </p>
	 * 
	 * @param state the HSMS communicate state
	 * @throws InterruptedException if interrupted
	 */
	public void waitUntilNotHsmsCommunicateState(HsmsCommunicateState state) throws InterruptedException;
	
	/**
	 * Wait until <string>NOT</strong> equals state.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * If Already communicatable, do nothing.<br />
	 * </p>
	 * 
	 * @param state the HSMS communicate state
	 * @param timeout the timeout value
	 * @param unit the timeout unit
	 * @throws InterruptedException if interrupted
	 * @throws TimeoutException if timeout
	 */
	public void waitUntilNotHsmsCommunicateState(HsmsCommunicateState state, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException;
	
	
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
	public boolean addHsmsCommunicateStateChangeBiListener(HsmsCommunicateStateChangeBiListener biListener);
	
	/**
	 * Remove listener.
	 * 
	 * @param biListener the state change listener
	 * @return {@code true} if remove success
	 * @throws NullPointerException if biListener is null
	 */
	public boolean removeHsmsCommunicateStateChangeBiListener(HsmsCommunicateStateChangeBiListener biListener);
	
}
