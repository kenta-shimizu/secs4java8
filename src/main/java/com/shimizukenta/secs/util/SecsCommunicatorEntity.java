package com.shimizukenta.secs.util;

import java.io.Closeable;
import java.io.IOException;

import com.shimizukenta.secs.SecsMessageSendable;

/**
 * SecsCommunicator-Entity utility. open/close, receive/send message, detect communicate-state-change.
 * 
 * <p>
 * To reply SxF0 if has wbit and not exist SxFy, {@link #setReplySxF0(boolean)}.<br />
 * To send S9Fy if not exist SxFy, {@link #setSendS9Fy(boolean)}.<bbr />
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public interface SecsCommunicatorEntity extends Closeable, SecsMessageSendable {
	
	public void open() throws IOException;
	
	/**
	 * Reply SxF0 if set {@code true} and not exist match SxFy.
	 * 
	 * @param doReply
	 */
	public void setReplySxF0(boolean doReply);
	
	/**
	 * Send S9F1, S9F3, S9F5, S9F7, S9F9 if set {@code true}.
	 * 
	 * @param doSend
	 */
	public void setSendS9Fy(boolean doSend);
	
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

}
