package com.shimizukenta.secs.hsmsss.impl;

import java.util.Optional;

import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.hsms.HsmsException;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessageSelectStatus;
import com.shimizukenta.secs.hsms.HsmsMessageType;
import com.shimizukenta.secs.hsms.HsmsSendMessageException;
import com.shimizukenta.secs.hsms.HsmsWaitReplyMessageException;
import com.shimizukenta.secs.hsms.impl.AbstractHsmsSession;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicatorConfig;
import com.shimizukenta.secs.secs2.Secs2;

public abstract class AbstractHsmsSsSession extends AbstractHsmsSession {
	
	private final AbstractHsmsSsCommunicator communicator;
	private final HsmsSsCommunicatorConfig config;
	
	public AbstractHsmsSsSession(
			AbstractHsmsSsCommunicator communicator,
			HsmsSsCommunicatorConfig config) {
		
		super(communicator.executorService(), config.gem());
		
		this.communicator = communicator;
		this.config = config;
		
		
	}
	
	@Override
	public boolean isEquip() {
		return this.config.isEquip().booleanValue();
	}
	
	@Override
	public int deviceId() {
		return this.sessionId();
	}
	
	@Override
	public int sessionId() {
		return this.config.sessionId().intValue();
	}
	
	
	/* send Message */

	private HsmsSsMessageBuilder msgBuilder() {
		return this.communicator.getHsmsSmMessageBuilder();
	}
	
	/* send control message */
	
	@Override
	public boolean select() throws InterruptedException {
		try {
			HsmsMessage msg = this.msgBuilder().buildSelectRequest(this);
			return this.send(msg)
					.filter(m -> m.messageType() == HsmsMessageType.SELECT_RSP)
					.map(HsmsMessageSelectStatus::get)
					.filter(s -> {
						switch (s) {
						case SUCCESS:
						case ACTIVED: {
							return true;
							/* break; */
						}
						default: {
							return false;
						}
						}
					})
					.isPresent();
		}
		catch (HsmsSendMessageException | HsmsWaitReplyMessageException | HsmsException e) {
		}
		
		return false;
	}
	
	@Override
	public boolean deselect() throws InterruptedException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean separate() throws InterruptedException {
		try {
			HsmsMessage msg = this.msgBuilder().buildSeparateRequest(this);
			this.send(msg);
			return true;
		}
		catch (HsmsSendMessageException | HsmsWaitReplyMessageException | HsmsException e) {
		}
		
		return false;
	}
	
	/* send data message */
	
	@Override
	public Optional<SecsMessage> send(int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException,
			SecsWaitReplyMessageException,
			SecsException,
			InterruptedException {
		
		HsmsMessage msg = this.msgBuilder().buildDataMessage(this, strm, func, wbit, secs2);
		return this.send(msg).map(m -> (SecsMessage)m);
	}
	
	@Override
	public Optional<SecsMessage> send(SecsMessage primaryMsg, int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException,
			SecsWaitReplyMessageException,
			SecsException,
			InterruptedException {
		
		HsmsMessage msg = this.msgBuilder().buildDataMessage(this, primaryMsg, strm, func, wbit, secs2);
		return this.send(msg).map(m -> (SecsMessage)m);
	}

}
