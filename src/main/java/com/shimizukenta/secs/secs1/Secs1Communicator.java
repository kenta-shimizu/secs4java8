package com.shimizukenta.secs.secs1;

import java.util.Optional;

import com.shimizukenta.secs.SecsCommunicator;

/**
 * This interface is implementation of SECS-I (SEMI-E4).
 * 
 * @author kenta-shimizu
 *
 */
public interface Secs1Communicator extends SecsCommunicator {
	
	/**
	 * Send SECS-I-Message.
	 * 
	 * <p>
	 * Send Secs1Message,<br />
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
	public Optional<Secs1Message> send(Secs1Message msg)
			throws Secs1SendMessageException,
			Secs1WaitReplyMessageException,
			Secs1Exception,
			InterruptedException;
	
	/**
	 * Add listener.
	 * 
	 * @param lstnr
	 * @return true if add success
	 */
	public boolean addTrySendSecs1MessagePassThroughListener(Secs1MessagePassThroughListener lstnr);
	
	/**
	 * Remove listener.
	 * 
	 * @param lstnr
	 * @return true if remove success
	 */
	public boolean removeTrySendSecs1MessagePassThroughListener(Secs1MessagePassThroughListener lstnr);
	
	/**
	 * Add listener.
	 * 
	 * @param lstnr
	 * @return true if add success
	 */
	public boolean addSendedSecs1MessagePassThroughListener(Secs1MessagePassThroughListener lstnr);
	
	/**
	 * Remove listener.
	 * 
	 * @param lstnr
	 * @return true if remove success
	 */
	public boolean removeSendedSecs1MessagePassThroughListener(Secs1MessagePassThroughListener lstnr);
	
	/**
	 * Add listener.
	 * 
	 * @param lstnr
	 * @return true if add success
	 */
	public boolean addReceiveSecs1MessagePassThroughListener(Secs1MessagePassThroughListener lstnr);
	
	/**
	 * Remove listener.
	 * 
	 * @param lstnr
	 * @return true if remove success
	 */
	public boolean removeReceiveSecs1MessagePassThroughListener(Secs1MessagePassThroughListener lstnr);
	
}
