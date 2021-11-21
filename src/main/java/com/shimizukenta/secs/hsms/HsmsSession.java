package com.shimizukenta.secs.hsms;

import java.util.Optional;

public interface HsmsSession extends HsmsCommunicator {
	
	/**
	 * Returns Session-ID
	 * 
	 * @return Session-ID
	 */
	public int sessionId();
	
	/**
	 * Send HSMS-Message and receive Reply-Message if exist.
	 * 
	 * @param msg
	 * @return Optional has value if Reply-Message exist
	 * @throws HsmsSendMessageException
	 * @throws HsmsWaitReplyMessageException
	 * @throws HsmsException
	 * @throws InterruptedException
	 */
	public Optional<HsmsMessage> send(HsmsMessage msg)
			throws HsmsSendMessageException, HsmsWaitReplyMessageException, HsmsException,
			InterruptedException;
	
}
