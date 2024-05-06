package com.shimizukenta.secs.secs1;

import com.shimizukenta.secs.SecsMessageReceiveObservable;

/**
 * Secs1MessageReceiveObservable.
 * 
 * @author kenta-shimizu
 *
 */
public interface Secs1MessageReceiveObservable extends SecsMessageReceiveObservable {
	
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
	
}
