package com.shimizukenta.secs.secs1;

import java.util.Optional;

import com.shimizukenta.secs.SecsCommunicator;
import com.shimizukenta.secs.secs2.Secs2;

/**
 * This interface is implementation of SECS-I (SEMI-E4).
 * 
 * @author kenta-shimizu
 *
 */
public interface Secs1Communicator extends SecsCommunicator {
	
	/**
	 * Create header-only Sesc1Message.
	 * 
	 * @param header header-10-bytes
	 * @return AbstractSecs1Message
	 * @throws Secs1SendMessageException
	 */
	public AbstractSecs1Message createSecs1Message(byte[] header) throws Secs1SendMessageException;
	
	/**
	 * Create Secs1Message.
	 * 
	 * @param header header-10-bytes
	 * @param body SECS-II data
	 * @return AbstractSecs1Message
	 * @throws Secs1SendMessageException
	 */
	public AbstractSecs1Message createSecs1Message(byte[] header, Secs2 body) throws Secs1SendMessageException;
	
	/**
	 * Send SECS-I-Message.
	 * 
	 * <p>
	 * Send Primary-Secs1Message,<br />
	 * Blocking-method.<br />
	 * Wait until sended Primay-Message and Reply-Secs1Message received if exist.
	 * </p>
	 * 
	 * @param msg
	 * @return reply-Secs1Message if exist
	 * @throws Secs1SendMessageException
	 * @throws Secs1WaitReplyMessageException
	 * @throws Secs1Exception
	 * @throws InterruptedException
	 */
	public Optional<? extends Secs1Message> send(AbstractSecs1Message msg)
			throws Secs1SendMessageException,
			Secs1WaitReplyMessageException,
			Secs1Exception,
			InterruptedException;

}
