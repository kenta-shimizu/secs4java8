package com.shimizukenta.secs.hsms;

import java.util.Optional;

import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.secs2.Secs2;

public abstract class AbstractHsmsSession extends AbstractHsmsCommunicator implements HsmsSession {
	
	private AbstractHsmsAsyncSocketChannel channel;
	
	public AbstractHsmsSession(AbstractHsmsCommunicatorConfig config) {
		super(config);
		this.channel = null;
	}
	
	private final Object syncChannel = new Object();
	
	public boolean setAsyncSocketChannel(AbstractHsmsAsyncSocketChannel channel) {
		synchronized ( this.syncChannel ) {
			if ( this.channel == null ) {
				this.channel = channel;
				return true;
			} else {
				return false;
			}
		}
	}
	
	public boolean unsetAsyncSocketChannel() {
		synchronized ( this.syncChannel ) {
			if ( this.channel == null ) {
				return false;
			} else {
				this.channel = null;
				return false;
			}
		}
	}
	
	protected AbstractHsmsAsyncSocketChannel asyncSocketChannel() throws HsmsSessionNotSelectedException {
		synchronized ( this.syncChannel ) {
			if ( this.channel == null ) {
				throw new HsmsSessionNotSelectedException();
			}
			return this.channel;
		}
	}
	
	@Override
	public Optional<SecsMessage> send(int strm, int func, boolean wbit, Secs2 secs2)
			throws HsmsSendMessageException, HsmsWaitReplyMessageException, HsmsException,
			InterruptedException {
		
		return this.asyncSocketChannel().send(this, strm, func, wbit, secs2).map(m -> (SecsMessage)m);
	}
	
	@Override
	public Optional<SecsMessage> send(SecsMessage primaryMsg, int strm, int func, boolean wbit, Secs2 secs2)
			throws HsmsSendMessageException, HsmsWaitReplyMessageException, HsmsException,
			InterruptedException {
		
		return this.asyncSocketChannel().send(primaryMsg, strm, func, wbit, secs2).map(m -> (SecsMessage)m);
	}
	
	@Override
	public Optional<HsmsMessage> send(HsmsMessage msg)
			throws HsmsSendMessageException, HsmsWaitReplyMessageException, HsmsException,
			InterruptedException {
		
		return this.asyncSocketChannel().sendHsmsMessage(msg);
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
