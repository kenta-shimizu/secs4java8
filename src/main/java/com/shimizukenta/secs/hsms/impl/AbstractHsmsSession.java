package com.shimizukenta.secs.hsms.impl;

import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.shimizukenta.secs.SecsCommunicatableStateChangeBiListener;
import com.shimizukenta.secs.SecsCommunicatableStateChangeListener;
import com.shimizukenta.secs.SecsMessageReceiveBiListener;
import com.shimizukenta.secs.SecsMessageReceiveListener;
import com.shimizukenta.secs.gem.Gem;
import com.shimizukenta.secs.gem.GemConfig;
import com.shimizukenta.secs.gem.impl.AbstractGem;
import com.shimizukenta.secs.hsms.HsmsCommunicateState;
import com.shimizukenta.secs.hsms.HsmsCommunicateStateChangeBiListener;
import com.shimizukenta.secs.hsms.HsmsCommunicateStateChangeListener;
import com.shimizukenta.secs.hsms.HsmsException;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessageReceiveBiListener;
import com.shimizukenta.secs.hsms.HsmsMessageReceiveListener;
import com.shimizukenta.secs.hsms.HsmsNotConnectedException;
import com.shimizukenta.secs.hsms.HsmsSendMessageException;
import com.shimizukenta.secs.hsms.HsmsSession;
import com.shimizukenta.secs.hsms.HsmsWaitReplyMessageException;
import com.shimizukenta.secs.impl.SecsCommunicatableStatePropertyBiObserver;
import com.shimizukenta.secs.impl.SecsMessageReceiveQueueBiObserver;
import com.shimizukenta.secs.impl.SecsMessageSendableImpl;
import com.shimizukenta.secs.local.property.BooleanProperty;
import com.shimizukenta.secs.local.property.ObjectProperty;

public abstract class AbstractHsmsSession implements HsmsSession, SecsMessageSendableImpl {
	
	private final Gem gem;
	
	private final SecsMessageReceiveQueueBiObserver secsMsgRecvQueueBiObserver;
	private final HsmsMessageReceiveQueueBiObserver hsmsMsgRecvQueueBiObserver;
	
	private final BooleanProperty secsCommStateProp;
	private final ObjectProperty<HsmsCommunicateState> hsmsCommStateProp;
	private final ObjectProperty<AbstractHsmsAsynchronousSocketChannelFacade> channelProp;
	
	private final SecsCommunicatableStatePropertyBiObserver secsCommStatePropBiObserver;
	private final HsmsCommunicateStatePropertyBiObserver hsmsCommStatePropBiObserver;
	
	public AbstractHsmsSession(Executor executor, GemConfig gemConfig) {
		
		this.gem = new AbstractGem(this, gemConfig) {};
		
		this.secsMsgRecvQueueBiObserver = new SecsMessageReceiveQueueBiObserver(executor, this);
		this.hsmsMsgRecvQueueBiObserver = new HsmsMessageReceiveQueueBiObserver(executor, this);
		
		this.secsCommStateProp = BooleanProperty.newInstance(false);
		this.hsmsCommStateProp = ObjectProperty.newInstance(HsmsCommunicateState.NOT_CONNECTED);
		this.channelProp = ObjectProperty.newInstance(null);
		
		this.hsmsCommStateProp.addChangeListener(state -> {
			this.secsCommStateProp.set(state.communicatable());
		});
		this.channelProp.addChangeListener(channel -> {
			if (channel == null) {
				this.hsmsCommStateProp.set(HsmsCommunicateState.NOT_CONNECTED);
			} else {
				this.hsmsCommStateProp.set(HsmsCommunicateState.NOT_SELECTED);
			}
		});
		
		this.secsCommStatePropBiObserver = new SecsCommunicatableStatePropertyBiObserver(this, this.secsCommStateProp);
		this.hsmsCommStatePropBiObserver = new HsmsCommunicateStatePropertyBiObserver(this, this.hsmsCommStateProp);
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
	public boolean isCommunicatable() {
		return this.secsCommStateProp.booleanValue();
	}
	
	@Override
	public void waitUntilCommunicatable() throws InterruptedException {
		this.secsCommStateProp.waitUntilTrue();
	}
	
	@Override
	public void waitUntilCommunicatable(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.secsCommStateProp.waitUntilTrue(timeout, unit);
	}
	
	@Override
	public void waitUntilNotCommunicatable() throws InterruptedException {
		this.secsCommStateProp.waitUntilFalse();
	}
	
	@Override
	public void waitUntilNotCommunicatable(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.secsCommStateProp.waitUntilFalse(timeout, unit);
	}
	
	@Override
	public boolean addSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeListener listener) {
		return this.secsCommStatePropBiObserver.addListener(listener);
	}
	
	@Override
	public boolean removeSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeListener listener) {
		return this.secsCommStatePropBiObserver.removeListener(listener);
	}
	
	@Override
	public boolean addSecsCommunicatableStateChangeBiListener(SecsCommunicatableStateChangeBiListener biListener) {
		return this.secsCommStatePropBiObserver.addBiListener(biListener);
	}
	
	@Override
	public boolean removeSecsCommunicatableStateChangeBiListener(SecsCommunicatableStateChangeBiListener biListener) {
		return this.secsCommStatePropBiObserver.removeBiListener(biListener);
	}
	
	@Override
	public HsmsCommunicateState getHsmsCommunicateState() {
		return this.hsmsCommStateProp.get();
	}
	
	@Override
	public void waitUntilHsmsCommunicateState(HsmsCommunicateState state) throws InterruptedException {
		this.hsmsCommStateProp.waitUntilEqualTo(state);
	}
	
	@Override
	public void waitUntilHsmsCommunicateState(HsmsCommunicateState state, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.hsmsCommStateProp.waitUntilEqualTo(state, timeout, unit);
	}
	
	@Override
	public void waitUntilNotHsmsCommunicateState(HsmsCommunicateState state) throws InterruptedException {
		this.hsmsCommStateProp.waitUntilNotEqualTo(state);
	}
	
	@Override
	public void waitUntilNotHsmsCommunicateState(HsmsCommunicateState state, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.hsmsCommStateProp.waitUntilNotEqualTo(state, timeout, unit);
	}
	
	@Override
	public boolean addHsmsCommunicateStateChangeListener(HsmsCommunicateStateChangeListener listener) {
		return this.hsmsCommStatePropBiObserver.addListener(listener);
	}
	
	@Override
	public boolean removeHsmsCommunicateStateChangeListener(HsmsCommunicateStateChangeListener listener) {
		return this.hsmsCommStatePropBiObserver.removeListener(listener);
	}
	
	@Override
	public boolean addHsmsCommunicateStateChangeBiListener(HsmsCommunicateStateChangeBiListener biListener) {
		return this.hsmsCommStatePropBiObserver.addBiListener(biListener);
	}
	
	@Override
	public boolean removeHsmsCommunicateStateChangeBiListener(HsmsCommunicateStateChangeBiListener biListener) {
		return this.hsmsCommStatePropBiObserver.removeBiListener(biListener);
	}
	
	public void notifyHsmsCommunicateStateToSelected() {
		this.hsmsCommStateProp.set(HsmsCommunicateState.SELECTED);
	}
	
	public void notifyHsmsCommunicateStateToNotSelected() {
		this.hsmsCommStateProp.set(HsmsCommunicateState.NOT_SELECTED);
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
