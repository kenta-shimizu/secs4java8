package com.shimizukenta.secs.secs1;

import java.util.Optional;

import com.shimizukenta.secs.SecsCommunicator;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.secs2.Secs2;

/**
 * This interface is implementation of SECS-I (SEMI-E4)<br />
 * 
 * @author kenta-shimizu
 *
 */
public interface Secs1Communicator extends SecsCommunicator {
	
	/**
	 * Create header-only Sesc1Message.
	 * 
	 * @param header
	 * @return Secs1Message
	 */
	public Secs1Message createSecs1Message(byte[] header);
	
	/**
	 * Create Secs1Message.
	 * 
	 * @param header
	 * @param body
	 * @return Secs1Message
	 */
	public Secs1Message createSecs1Message(byte[] header, Secs2 body);
	
	/**
	 * Blocking-method.<br />
	 * send Primary-Secs1Message,<br />
	 * wait until Reply-Secs1Message if exist.
	 * 
	 * @param msg
	 * @return reply-Secs1Message if exist
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<Secs1Message> send(Secs1Message msg)
			throws SecsSendMessageException,
			SecsWaitReplyMessageException,
			SecsException,
			InterruptedException;

}
