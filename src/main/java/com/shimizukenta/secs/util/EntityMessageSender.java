package com.shimizukenta.secs.util;

import java.util.Optional;

import com.shimizukenta.secs.SecsCommunicator;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsMessageSendable;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;

/**
 * 
 * @author kenta-shimizu
 *
 */
public interface EntityMessageSender extends SecsMessageSendable {
	
	/**
	 * Send S9F9 if set {@code true} and T3-Timeout.
	 * 
	 * @param doSend
	 */
	public void setSendS9F9(boolean doSend);
	
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
	 * New-instance builder.
	 * 
	 * @param communicator
	 * @return new-instance
	 */
	public static EntityMessageSender newInstance(SecsCommunicator communicator) {
		return new AbstractEntityMessageSender(communicator) {};
	}
	
}
