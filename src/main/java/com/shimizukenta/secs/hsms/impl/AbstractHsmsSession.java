package com.shimizukenta.secs.hsms.impl;

import java.util.Optional;
import java.util.concurrent.Executor;

import com.shimizukenta.secs.SecsMessageReceiveBiListener;
import com.shimizukenta.secs.SecsMessageReceiveListener;
import com.shimizukenta.secs.gem.Gem;
import com.shimizukenta.secs.gem.GemConfig;
import com.shimizukenta.secs.gem.impl.AbstractGem;
import com.shimizukenta.secs.hsms.HsmsCommunicateState;
import com.shimizukenta.secs.hsms.HsmsException;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessageReceiveBiListener;
import com.shimizukenta.secs.hsms.HsmsMessageReceiveListener;
import com.shimizukenta.secs.hsms.HsmsNotConnectedException;
import com.shimizukenta.secs.hsms.HsmsSendMessageException;
import com.shimizukenta.secs.hsms.HsmsSession;
import com.shimizukenta.secs.hsms.HsmsWaitReplyMessageException;
import com.shimizukenta.secs.impl.SecsMessageReceiveQueueBiObserver;
import com.shimizukenta.secs.impl.SecsMessageSendableImpl;
import com.shimizukenta.secs.local.property.ObjectProperty;

public abstract class AbstractHsmsSession
		implements HsmsSession,
		SecsMessageSendableImpl,
		HsmsCommunicateStateDetectableImpl {
	
	private final Gem gem;
	
	private final SecsMessageReceiveQueueBiObserver secsMsgRecvQueueBiObserver;
	private final HsmsMessageReceiveQueueBiObserver hsmsMsgRecvQueueBiObserver;
	
	private final AbstractHsmsCommunicateStateObserverFacade hsmsCommStateObserver;
	
	private final ObjectProperty<AbstractHsmsAsynchronousSocketChannelFacade> channelProp;
	
	public AbstractHsmsSession(Executor executor, GemConfig gemConfig) {
		
		this.gem = new AbstractGem(this, gemConfig) {};
		
		this.secsMsgRecvQueueBiObserver = new SecsMessageReceiveQueueBiObserver(executor, this);
		this.hsmsMsgRecvQueueBiObserver = new HsmsMessageReceiveQueueBiObserver(executor, this);
		
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
	
	
	/* receive Message */
	
	@Override
	public boolean addSecsMessageReceiveListener(SecsMessageReceiveListener listener) {
		return this.secsMsgRecvQueueBiObserver.addListener(listener);
	}
	
	@Override
	public boolean removeSecsMessageReceiveListener(SecsMessageReceiveListener listener) {
		return this.secsMsgRecvQueueBiObserver.removeListener(listener);
	}
	
	@Override
	public boolean addSecsMessageReceiveBiListener(SecsMessageReceiveBiListener biListener) {
		return this.secsMsgRecvQueueBiObserver.addBiListener(biListener);
	}
	
	@Override
	public boolean removeSecsMessageReceiveBiListener(SecsMessageReceiveBiListener biListener) {
		return this.secsMsgRecvQueueBiObserver.removeBiListener(biListener);
	}
	
	@Override
	public boolean addHsmsMessageReceiveListener(HsmsMessageReceiveListener listener) {
		return this.hsmsMsgRecvQueueBiObserver.addListener(listener);
	}
	
	@Override
	public boolean removeHsmsMessageReceiveListener(HsmsMessageReceiveListener listener) {
		return this.hsmsMsgRecvQueueBiObserver.removeListener(listener);
	}
	
	@Override
	public boolean addHsmsMessageReceiveBiListener(HsmsMessageReceiveBiListener biListener) {
		return this.hsmsMsgRecvQueueBiObserver.addBiListener(biListener);
	}
	
	@Override
	public boolean removeHsmsMessageReceiveBiListener(HsmsMessageReceiveBiListener biListener) {
		return this.hsmsMsgRecvQueueBiObserver.removeBiListener(biListener);
	}
	
	public void notifyHsmsMessageReceive(HsmsMessage msg) throws InterruptedException {
		if (msg.isDataMessage()) {
			this.secsMsgRecvQueueBiObserver.put(msg);
		}
		this.hsmsMsgRecvQueueBiObserver.put(msg);
	}
	
	
	/* Communicatable Detectable */
	
	@Override
	public AbstractHsmsCommunicateStateObserverFacade hsmsCommunicateStateObserver() {
		return this.hsmsCommStateObserver;
	}
	
	
	public void notifyHsmsCommunicateStateToSelected() {
		this.hsmsCommunicateStateObserver().setHsmsCommunicateState(HsmsCommunicateState.SELECTED);
	}
	
	public void notifyHsmsCommunicateStateToNotSelected() {
		this.hsmsCommunicateStateObserver().setHsmsCommunicateState(HsmsCommunicateState.NOT_SELECTED);
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
