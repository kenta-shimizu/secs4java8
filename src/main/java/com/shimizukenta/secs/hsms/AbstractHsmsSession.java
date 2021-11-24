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
				return true;
			}
		}
	}
	
	protected Optional<AbstractHsmsAsyncSocketChannel> optionalAsyncSocketChannel() {
		synchronized ( this.syncChannel ) {
			return this.channel == null ? Optional.empty() : Optional.of(this.channel);
		}
	}
	
	protected AbstractHsmsAsyncSocketChannel asyncSocketChannel() throws HsmsSessionNotSelectedException {
		return this.optionalAsyncSocketChannel()
				.orElseThrow(HsmsSessionNotSelectedException::new);
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
	public boolean linktest() throws InterruptedException {
		
		try {
			Optional<HsmsMessage> op = this.asyncSocketChannel().sendLinktestRequest(this);
			boolean f = op.isPresent();
			if ( f ) {
				return true;
			}
		}
		catch ( HsmsSendMessageException | HsmsWaitReplyMessageException | HsmsException giveup ) {
		}
		
		this.optionalAsyncSocketChannel().ifPresent(AbstractHsmsAsyncSocketChannel::shutdown);
		
		return false;
	}
	
	@Override
	public boolean select() throws InterruptedException {
		
		try {
			return this.asyncSocketChannel().sendSelectRequest(this)
					.map(HsmsMessageSelectStatus::get)
					.filter(status -> {
						switch ( status ) {
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
		catch ( HsmsSendMessageException | HsmsWaitReplyMessageException | HsmsException giveup ) {
		}
		
		return false;
	}
	
	@Override
	public boolean deselect() throws InterruptedException {
		
		try {
			boolean f = this.asyncSocketChannel().sendDeselectRequest(this)
					.map(HsmsMessageDeselectStatus::get)
					.filter(status -> {
						switch ( status ) {
						case SUCCESS:
						case NO_SELECTED: {
							return true;
							/* break; */
						}
						default: {
							return false;
						}
						}
					})
					.isPresent();
			
			if ( f ) {
				this.optionalAsyncSocketChannel().ifPresent(AbstractHsmsAsyncSocketChannel::shutdown);
				return true;
			}
		}
		catch ( HsmsSendMessageException | HsmsWaitReplyMessageException | HsmsException giveup ) {
		}
		
		return false;
	}
	
	@Override
	public boolean separate() throws InterruptedException {
		
		try {
			this.asyncSocketChannel().sendSeparateRequest(this);
			this.optionalAsyncSocketChannel().ifPresent(AbstractHsmsAsyncSocketChannel::shutdown);
			return true;
		}
		catch ( HsmsSendMessageException | HsmsWaitReplyMessageException | HsmsException giveup ) {
		}
		
		return false;
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
