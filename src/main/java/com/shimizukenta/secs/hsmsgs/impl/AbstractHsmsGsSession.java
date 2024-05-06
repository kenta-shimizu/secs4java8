package com.shimizukenta.secs.hsmsgs.impl;

import java.util.Optional;

import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.hsms.HsmsException;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessageDeselectStatus;
import com.shimizukenta.secs.hsms.HsmsMessageSelectStatus;
import com.shimizukenta.secs.hsms.HsmsMessageType;
import com.shimizukenta.secs.hsms.HsmsSendMessageException;
import com.shimizukenta.secs.hsms.HsmsWaitReplyMessageException;
import com.shimizukenta.secs.hsms.impl.AbstractHsmsSession;
import com.shimizukenta.secs.hsmsgs.HsmsGsCommunicatorConfig;
import com.shimizukenta.secs.secs2.Secs2;

public abstract class AbstractHsmsGsSession extends AbstractHsmsSession {
	
	private final AbstractHsmsGsCommunicator communicator;
	private final HsmsGsCommunicatorConfig config;
	private final int sessionId;
	
	public AbstractHsmsGsSession(
			AbstractHsmsGsCommunicator communicator,
			HsmsGsCommunicatorConfig config,
			int sessionId) {
		
		super(communicator.executorService(), config.gem());
		
		this.communicator = communicator;
		this.config = config;
		this.sessionId = sessionId;
	}
	
	@Override
	public boolean isEquip() {
		return this.config.isEquip().booleanValue();
	}
	
	@Override
	public int deviceId() {
		return -1;
	}
	
	@Override
	public int sessionId() {
		return this.sessionId;
	}
	
	/* send Message */
	
	private HsmsGsMessageBuilder msgBuilder() {
		return this.communicator.getHsmsGsMessageBuilder();
	}
	
	@Override
	public boolean select() throws InterruptedException {
		try {
			HsmsMessage msg = this.msgBuilder().buildSelectRequest(this);
			return this.send(msg)
					.filter(m -> m.messageType() == HsmsMessageType.SELECT_RSP)
					.filter(m -> m.sessionId() == this.sessionId())
					.map(HsmsMessageSelectStatus::get)
					.filter(s -> {
						switch (s) {
						case SUCCESS:
						case ACTIVED:
						case ENTITY_ACTIVED: {
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
		try {
			HsmsMessage msg = this.msgBuilder().buildDeselectRequest(this);
			return this.send(msg)
					.filter(m -> m.messageType() == HsmsMessageType.DESELECT_RSP)
					.filter(m -> m.sessionId() == this.sessionId())
					.map(HsmsMessageDeselectStatus::get)
					.filter(s -> {
						switch (s) {
						case SUCCESS:
						case NO_SELECTED:
							return true;
							/* break; */
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

	@Override
	public Optional<SecsMessage> send(int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		
		HsmsMessage msg = this.msgBuilder().buildDataMessage(this, strm, func, wbit, secs2);
		return this.send(msg).map(m -> (SecsMessage)m);
	}

	@Override
	public Optional<SecsMessage> send(SecsMessage primaryMsg, int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		
		HsmsMessage msg = this.msgBuilder().buildDataMessage(this, primaryMsg, strm, func, wbit, secs2);
		return this.send(msg).map(m -> (SecsMessage)m);
	}
	
}
