package com.shimizukenta.secs.secs1;

import java.util.Optional;

import com.shimizukenta.secs.SecsMessageSendable;

/**
 * Sendable SECS-I-Message.
 * 
 * @author kenta-shimizu
 *
 */
public interface Secs1MessageSendable extends SecsMessageSendable {
	
	/**
	 * Send SECS-I-Message.
	 * 
	 * <p>
	 * Send Secs1Message,<br />
	 * Blocking-method.<br />
	 * Wait until sended Primay-Message and Reply-Secs1Message received if exist.
	 * </p>
	 * 
	 * @param secs1Message SECS-I Message
	 * @return reply-Secs1Message if exist
	 * @throws Secs1SendMessageException if SECS-I Message send failed
	 * @throws Secs1WaitReplyMessageException if SECS-I wait reply Message failed
	 * @throws Secs1Exception if SECS-I communicate failed
	 * @throws InterruptedException if interrupted
	 */
	public Optional<Secs1Message> send(Secs1Message msg)
			throws Secs1SendMessageException,
			Secs1WaitReplyMessageException,
			Secs1Exception,
			InterruptedException;
	
}
