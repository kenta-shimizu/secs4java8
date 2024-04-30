package com.shimizukenta.secs.impl;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.shimizukenta.secs.AbstractSecsCommunicatorConfig;
import com.shimizukenta.secs.SecsCommunicatableStateChangeBiListener;
import com.shimizukenta.secs.SecsCommunicatableStateChangeListener;
import com.shimizukenta.secs.SecsCommunicator;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsMessageReceiveBiListener;
import com.shimizukenta.secs.SecsMessageReceiveListener;
import com.shimizukenta.secs.gem.Gem;
import com.shimizukenta.secs.gem.impl.AbstractGem;
import com.shimizukenta.secs.local.property.BooleanProperty;

/**
 * This abstract class is implementation of SECS-communicate.
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractSecsCommunicator extends AbstractBaseCommunicator implements SecsCommunicator, SecsMessageSendableImpl, SecsMessagePassThroughObservableImpl, SecsLogObservableImpl {
	
	private final BooleanProperty communicatable = BooleanProperty.newInstance(false);
	
	private final AbstractSecsCommunicatorConfig config;
	private final Gem gem;
	
	
	private final SecsMessageReceiveQueueBiObserver secsMsgRecvQueueObserver;
	
	private final SecsCommunicatableStatePropertyBiObserver communicatableStatePropOberser;
	
	public AbstractSecsCommunicator(AbstractSecsCommunicatorConfig config) {
		super();
		
		this.config = config;
		this.gem = new AbstractGem(this, config.gem()) {};
		
		this.secsMsgRecvQueueObserver = new SecsMessageReceiveQueueBiObserver(this.executorService(), this);
		this.communicatableStatePropOberser = new SecsCommunicatableStatePropertyBiObserver(this, this.communicatable);
	}
	
	@Override
	public void open() throws IOException {
		
		synchronized (this.syncOpen) {
			super.open();
		}
	}
	
	@Override
	public void close() throws IOException {
		super.close();
	}
	
	@Override
	public Gem gem() {
		return gem;
	}
	
	@Override
	public boolean isEquip() {
		return config.isEquip().booleanValue();
	}
	
	@Override
	public boolean isCommunicatable() {
		return this.communicatable.booleanValue();
	}
	
	@Override
	public void waitUntilCommunicatable() throws InterruptedException {
		this.communicatable.waitUntilTrue();
	}
	
	@Override
	public void waitUntilCommunicatable(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.communicatable.waitUntilTrue(timeout, unit);
	}
	
	@Override
	public void waitUntilNotCommunicatable() throws InterruptedException {
		this.communicatable.waitUntilFalse();
	}
	
	@Override
	public void waitUntilNotCommunicatable(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.communicatable.waitUntilFalse(timeout, unit);
	}
	
	@Override
	public void openAndWaitUntilCommunicatable() throws IOException, InterruptedException {
		this.openIfNotOpen();
		this.waitUntilCommunicatable();
	}
	
	@Override
	public void openAndWaitUntilCommunicatable(long timeout, TimeUnit unit) throws IOException, InterruptedException, TimeoutException {
		this.openIfNotOpen();
		this.waitUntilCommunicatable(timeout, unit);
	}
	
	private final Object syncOpen = new Object();
	
	private void openIfNotOpen() throws IOException {
		synchronized ( this.syncOpen ) {
			if (! this.isOpen()) {
				this.open();
			}
		}
	}
	
	
	/* Secs-Message Receive Listener */
	
	@Override
	public boolean addSecsMessageReceiveListener(SecsMessageReceiveListener l) {
		return this.secsMsgRecvQueueObserver.addListener(l);
	}
	
	@Override
	public boolean removeSecsMessageReceiveListener(SecsMessageReceiveListener l) {
		return this.secsMsgRecvQueueObserver.removeListener(l);
	}
	
	@Override
	public boolean addSecsMessageReceiveBiListener(SecsMessageReceiveBiListener l) {
		return this.secsMsgRecvQueueObserver.addBiListener(l);
	}
	
	@Override
	public boolean removeSecsMessageReceiveBiListener(SecsMessageReceiveBiListener l) {
		return this.secsMsgRecvQueueObserver.removeBiListener(l);
	}
	
	protected final void notifyReceiveSecsMessage(SecsMessage msg) throws InterruptedException {
		this.secsMsgRecvQueueObserver.put(msg);
	}
	
	
	/* Secs-Communicatable-State-Changed-Listener */
	
	@Override
	public boolean addSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeListener listener) {
		return this.communicatableStatePropOberser.addListener(listener);
	}
	
	@Override
	public boolean removeSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeListener listener) {
		return this.communicatableStatePropOberser.removeListener(listener);
	}
	
	@Override
	public boolean addSecsCommunicatableStateChangeBiListener(SecsCommunicatableStateChangeBiListener biListener) {
		return this.communicatableStatePropOberser.addBiListener(biListener);
	}
	
	@Override
	public boolean removeSecsCommunicatableStateChangeBiListener(SecsCommunicatableStateChangeBiListener biListener) {
		return this.communicatableStatePropOberser.removeBiListener(biListener);
	}
	
	public void notifyCommunicatableStateChange(boolean f) {
		this.communicatable.set(f);
	}
	
	/* Logger */
	public boolean offerThrowableToLog(Throwable t) {
		return this.secsLogObserver().offerThrowable(t);
	}
	
}
