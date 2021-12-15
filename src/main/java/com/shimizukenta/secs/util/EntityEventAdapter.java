package com.shimizukenta.secs.util;

import com.shimizukenta.secs.SecsCommunicatableStateChangeBiListener;
import com.shimizukenta.secs.SecsCommunicator;
import com.shimizukenta.secs.SecsMessageReceiveBiListener;

/**
 * 
 * @author kenta-shimizu
 *
 */
public interface EntityEventAdapter extends SecsCommunicatableStateChangeBiListener, SecsMessageReceiveBiListener {
	
	/**
	 * Reply SxF0 if set {@code true} and not exist match SxFy.
	 * 
	 * @param doReply
	 */
	public void setReplySxF0(boolean doReply);
	
	/**
	 * Send S9F1, S9F3, S9F5, S9F7, if set {@code true}.
	 * 
	 * @param doReply
	 */
	public void setReplyS9Fy(boolean doReply);
	
	/**
	 * Add Communicate-state-change-listner.
	 * 
	 * @param listener
	 * @return {@code true} if add success.
	 */
	public boolean addCommunicatableStateChangeListener(EntityCommunicatableStateChangeListener listener);
	
	/**
	 * Remove Communicate-state-change-listner.
	 * 
	 * @param listener
	 * @return {@code true} if remove success.
	 */
	public boolean removeCommunicatableStateChangeListener(EntityCommunicatableStateChangeListener listener);
	
	/**
	 * Add Message-Receive-Listener with Stream-Number and Function-Number.
	 * 
	 * @param strm
	 * @param func
	 * @param listener
	 * @return {@code true} if add success.
	 */
	public boolean addMessageReceiveListener(int strm, int func, EntityMessageReceiveListener listener);
	
	/**
	 * Remove listener by Stream-Number and Function-Number.
	 * 
	 * @param strm
	 * @param func
	 * @return {@code true} if remove success.
	 */
	public boolean removeMessageReceiveListener(int strm, int func);
	
	/**
	 * Add listeners to Communicator.
	 * 
	 * @param communicator
	 */
	public void adaptToSecsCommunicator(SecsCommunicator communicator);
	
	/**
	 * New instance builder.
	 * 
	 * @return new-instance
	 */
	public static EntityEventAdapter newInstance() {
		return new AbstractEntityEventAdapter() {};
	}
	
}
