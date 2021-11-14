package com.shimizukenta.secs.hsms;

import java.util.Optional;

import com.shimizukenta.secs.AbstractSecsCommunicator;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.secs2.Secs2;

public abstract class AbstractHsmsSession extends AbstractSecsCommunicator implements HsmsSession {
	
	public AbstractHsmsSession(AbstractHsmsCommunicatorConfig config) {
		super(config);
	}
	
	abstract protected HsmsMessageBuilder msgBuilder();
	
	@Override
	public Optional<SecsMessage> send(int strm, int func, boolean wbit, Secs2 secs2)
			throws HsmsSendMessageException, HsmsWaitReplyMessageException, HsmsException,
			InterruptedException {
		
		return this.send(this.msgBuilder().buildDataMessage(this, strm, func, wbit, secs2)).map(m -> (SecsMessage)m);
	}
	
	@Override
	public Optional<SecsMessage> send(SecsMessage primary, int strm, int func, boolean wbit, Secs2 secs2)
			throws HsmsSendMessageException, HsmsWaitReplyMessageException, HsmsException,
			InterruptedException {
		
		return this.send(this.msgBuilder().buildDataMessage(primary, strm, func, wbit, secs2)).map(m -> (SecsMessage)m);
	}
	
	@Override
	public Optional<HsmsMessage> send(AbstractHsmsMessage msg)
			throws HsmsSendMessageException, HsmsWaitReplyMessageException, HsmsException,
			InterruptedException {
		
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int hashCode() {
		return this.sessionId();
	}
	
	@Override
	public boolean equals(Object o) {
		if ( (o != null) && (o instanceof AbstractHsmsSession) ) {
			return ((AbstractHsmsSession)o).sessionId() == this.sessionId();
		}
		return false;
	}
	
}
