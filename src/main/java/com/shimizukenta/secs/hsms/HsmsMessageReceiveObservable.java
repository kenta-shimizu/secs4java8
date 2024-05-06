package com.shimizukenta.secs.hsms;

import com.shimizukenta.secs.SecsMessageReceiveObservable;

/**
 * HsmsMessageReceiveObservable.
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsMessageReceiveObservable extends SecsMessageReceiveObservable {
	
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
	 * Add Listener to receive Primary-Message.
	 * 
	 * <p>
	 * This Listener not receive Reply-Message.<br />
	 * </p>
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if add success
	 */
	public boolean addHsmsMessageReceiveBiListener(HsmsMessageReceiveBiListener biListener);
	
	/**
	 * Remove Listener.
	 * 
	 * @param biListener Not accept {@code null}
	 * @return {@code true} if remove success
	 */
	public boolean removeHsmsMessageReceiveBiListener(HsmsMessageReceiveBiListener biListener);
	
}
