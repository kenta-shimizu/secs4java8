package com.shimizukenta.secs.hsms;

import java.util.Optional;

import com.shimizukenta.secs.SecsMessageSendable;

/**
 * HsmsMessageSendable.
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsMessageSendable extends SecsMessageSendable {
	
	/**
	 * Send HSMS-Message, returns Optional is present if reply-exist, otherwise Optional is empty.
	 * 
	 * @param message HSMS message
	 * @return Optional is present if reply-message is exists empty
	 * @throws HsmsSendMessageException if send failed
	 * @throws HsmsWaitReplyMessageException if receive reply message failed (et.al T3-timeout
	 * @throws HsmsException the if HSMS exception
	 * @throws InterruptedException if interrupted
	 */
	public Optional<HsmsMessage> send(HsmsMessage message)
			throws HsmsSendMessageException,
			HsmsWaitReplyMessageException,
			HsmsException,
			InterruptedException;
}
