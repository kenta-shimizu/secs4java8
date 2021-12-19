package com.shimizukenta.secs.util;

import java.io.IOException;
import java.util.Optional;

import com.shimizukenta.secs.OpenAndCloseable;
import com.shimizukenta.secs.SecsCommunicator;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsMessageSendable;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.gem.Gem;

/**
 * SecsCommunicator-Entity utility. open/close, receive/send message, detect communicate-state-change.
 * 
 * <ul>
 * <li>To reply SxF0 if has wbit and undefined-SxFy message, {@link #setReplySxF0(boolean)}.</li>
 * <li>To send S9Fy if any failed, {@link #setSendS9Fy(boolean)}.</li>
 * </ul>
 * <ul>
 * <li>To detect communicatable-state-changed, {@link #addCommunicatableStateChangeListener(EntityCommunicatableStateChangeListener)}</li>
 * <li>To receive Primary-Message, {@link #addMessageReceiveListener(int, int, EntityMessageReceiveListener)}</li>
 * </ul>
 * 
 * @author kenta-shimizu
 *
 */
public interface SecsCommunicatorEntity extends OpenAndCloseable, SecsMessageSendable {
	
	/**
	 * Open also SecsCommunicator.
	 * 
	 * @throws IOException
	 */
	public void open() throws IOException;
	
	/**
	 * Close also SecsCommunicaotor.
	 */
	public void close() throws IOException;
	
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
	
	/**
	 * S9F9, Transaction Timeout.
	 * 
	 * <p>
	 * blocking-method.<br />
	 * </p>
	 * 
	 * @param SecsWaitReplyMessageException
	 * @return {@code Optional.empty()}
	 * @throws SecsSendMessageException if send failed
	 * @throws SecsWaitReplyMessageException if receive message failed, e.g. Timeout-T3
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> sendS9F9(SecsWaitReplyMessageException e)
			throws SecsSendMessageException,
			SecsWaitReplyMessageException,
			SecsException,
			InterruptedException;
	
	/**
	 * Returns GEM-interface.
	 * 
	 * @return GEM-interface-instance
	 */
	public Gem gem();
	
	/**
	 * New instance builder.
	 * 
	 * @param communicator
	 * @return new-instance
	 */
	public static SecsCommunicatorEntity newInstance(SecsCommunicator communicator) {
		return new AbstractSecsCommunicatorEntity(communicator) {};
	}
	
}
