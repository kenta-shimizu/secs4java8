package com.shimizukenta.secs.hsms.impl;

import java.util.Optional;
import java.util.concurrent.Executor;

import com.shimizukenta.secs.gem.Gem;
import com.shimizukenta.secs.gem.GemConfig;
import com.shimizukenta.secs.gem.impl.AbstractGem;
import com.shimizukenta.secs.hsms.HsmsCommunicateState;
import com.shimizukenta.secs.hsms.HsmsException;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsNotConnectedException;
import com.shimizukenta.secs.hsms.HsmsSendMessageException;
import com.shimizukenta.secs.hsms.HsmsSession;
import com.shimizukenta.secs.hsms.HsmsWaitReplyMessageException;
import com.shimizukenta.secs.impl.SecsMessageSendableImpl;
import com.shimizukenta.secs.local.property.ObjectProperty;

public abstract class AbstractHsmsSession
		implements HsmsSession,
		SecsMessageSendableImpl,
		HsmsMessageReceiveObservableImpl,
		HsmsCommunicateStateDetectableImpl {
	
	private final Gem gem;
	
	private final AbstractHsmsMessageReceiveObserverFacade hsmsMsgRecvObserver;
	private final AbstractHsmsCommunicateStateObserverFacade hsmsCommStateObserver;
	
	private final ObjectProperty<AbstractHsmsAsynchronousSocketChannelFacade> channelProp;
	
	public AbstractHsmsSession(Executor executor, GemConfig gemConfig) {
		
		this.gem = new AbstractGem(this, gemConfig) {};
		
		this.hsmsMsgRecvObserver = new AbstractHsmsMessageReceiveObserverFacade(executor, this) {};
		this.hsmsCommStateObserver = new AbstractHsmsCommunicateStateObserverFacade(this) {};
		
		this.channelProp = ObjectProperty.newInstance(null);
		
		this.channelProp.addChangeListener(channel -> {
			if (channel == null) {
				this.hsmsCommunicateStateObserver().setHsmsCommunicateState(HsmsCommunicateState.NOT_CONNECTED);
			} else {
				this.hsmsCommunicateStateObserver().setHsmsCommunicateState(HsmsCommunicateState.NOT_SELECTED);
			}
		});
		
	}
	
	@Override
	public Gem gem() {
		return this.gem;
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
	
	
	/* channel setter/getter */
	
	public boolean setChannel(AbstractHsmsAsynchronousSocketChannelFacade channel) {
		synchronized (this.channelProp) {
			if (this.channelProp.isNull()) {
				this.channelProp.set(channel);
				return true;
			} else {
				return false;
			}
		}
	}
	
	public boolean unsetChannel() {
		synchronized (this.channelProp) {
			if (this.channelProp.isNull()) {
				return false;
			} else {
				this.channelProp.set(null);
				return true;
			}
		}
	}
	
	public AbstractHsmsAsynchronousSocketChannelFacade getChannel() {
		synchronized (this.channelProp) {
			return this.channelProp.get();
		}
	}
	
	
	/* HSMS Message Receivable */
	
	@Override
	public AbstractHsmsMessageReceiveObserverFacade hsmsMessageReceiveObserver() {
		return this.hsmsMsgRecvObserver;
	}
	
	
	/* Communicatable Detectable */
	
	@Override
	public AbstractHsmsCommunicateStateObserverFacade hsmsCommunicateStateObserver() {
		return this.hsmsCommStateObserver;
	}
	
	
	/* send */
	
	@Override
	public Optional<HsmsMessage> send(HsmsMessage msg)
			throws HsmsSendMessageException,
			HsmsWaitReplyMessageException,
			HsmsException,
			InterruptedException {
		
		final AbstractHsmsAsynchronousSocketChannelFacade asyncChannel = this.getChannel();
		
		if (asyncChannel == null) {
			throw new HsmsNotConnectedException();
		} else {
			return asyncChannel.send(msg);
		}
	}

}
