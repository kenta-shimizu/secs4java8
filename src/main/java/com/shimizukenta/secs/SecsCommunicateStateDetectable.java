package com.shimizukenta.secs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * SECS Communicate State Detectable.
 * 
 * @author kenta-shimizu
 *
 */
public interface SecsCommunicateStateDetectable {
	
	/**
	 * Returns true if communicatable.
	 * 
	 * <p>
	 * Communicatable is send and receive message.
	 * </p>
	 * 
	 * @return true if communicatable
	 */
	public boolean isCommunicatable();
	
	/**
	 * Wait until communicatable.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * If Already communicatable, do nothing.<br />
	 * </p>
	 * 
	 * @throws InterruptedException if interrupted
	 */
	public void waitUntilCommunicatable() throws InterruptedException;
	
	/**
	 * Wait until communicatable.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * If Already communicatable, do nothing.<br />
	 * </p>
	 * 
	 * @param timeout the timeout value
	 * @param unit the timeout unit
	 * @throws InterruptedException if interrupted
	 * @throws TimeoutException if timeout
	 */
	public void waitUntilCommunicatable(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException;
	
	/**
	 * Wait until <strong>NOT</strong> communicatable.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * If Already not communicatable, do nothing.<br />
	 * </p>
	 * 
	 * @throws InterruptedException if interrupted
	 */
	public void waitUntilNotCommunicatable() throws InterruptedException;
	
	/**
	 * Wait until <strong>NOT</strong> communicatable.
	 * 
	 * <p>
	 * Blocking-method.<br />
	 * If Already not communicatable, do nothing.<br />
	 * </p>
	 * 
	 * @param timeout the timeout value
	 * @param unit the timeout unit
	 * @throws InterruptedException if interrupted
	 * @throws TimeoutException if timeout
	 */
	public void waitUntilNotCommunicatable(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException;
	
	
	/**
	 * Add Listener to get communicate-state-changed.
	 * 
	 * <p>
	 * Blocking-Listener.<br />
	 * Pass through quickly.<br />
	 * </p>
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeListener listener);
	
	/**
	 * Remove Listener.
	 * 
	 * @param listener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeListener listener);	
	
	/**
	 * Add Listener to get communicate-state-changed.
	 * 
	 * <p>
	 * Blocking-Listener.<br />
	 * Pass through quickly.<br />
	 * </p>
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addSecsCommunicatableStateChangeBiListener(SecsCommunicatableStateChangeBiListener biListener);
	
	/**
	 * Remove Listener.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeSecsCommunicatableStateChangeBiListener(SecsCommunicatableStateChangeBiListener biListener);
	
}
