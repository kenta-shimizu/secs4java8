package com.shimizukenta.secs.impl;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.shimizukenta.secs.AbstractSecsCommunicatorConfig;
import com.shimizukenta.secs.SecsCommunicatableStateChangeBiListener;
import com.shimizukenta.secs.SecsCommunicatableStateChangeListener;
import com.shimizukenta.secs.SecsCommunicator;
import com.shimizukenta.secs.SecsLogListener;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsMessagePassThroughListener;
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
public abstract class AbstractSecsCommunicator extends AbstractBaseCommunicator implements SecsCommunicator {
	
	private final BooleanProperty communicatable = BooleanProperty.newInstance(false);
	
	private final AbstractSecsCommunicatorConfig config;
	private final Gem gem;
	
	
	private final SecsMessageReceiveQueueBiObserver secsMsgRecvQueueObserver;
	
	private final SecsLogQueueObserver logQueuObserver;
	
	private final SecsCommunicatableStatePropertyBiObserver communicatableStatePropOberser;
	
	private final SecsMessagePassThroughQueueObserver trySendSecsMsgPassThroughQueueObserver;
	private final SecsMessagePassThroughQueueObserver sendedSecsMsgPassThroughQueueObserver;
	private final SecsMessagePassThroughQueueObserver recvSecsMsgPassThroughQueueObserver;
	
	
	public AbstractSecsCommunicator(AbstractSecsCommunicatorConfig config) {
		super();
		
		this.config = config;
		this.gem = new AbstractGem(this, config.gem()) {};
		
		
		this.secsMsgRecvQueueObserver = new SecsMessageReceiveQueueBiObserver(this.executorService(), this);
		
		this.logQueuObserver = new SecsLogQueueObserver(this.executorService());
		
		this.communicatableStatePropOberser = new SecsCommunicatableStatePropertyBiObserver(this, this.communicatable);
		
		this.trySendSecsMsgPassThroughQueueObserver = new SecsMessagePassThroughQueueObserver(this.executorService());
		this.sendedSecsMsgPassThroughQueueObserver = new SecsMessagePassThroughQueueObserver(this.executorService());
		this.recvSecsMsgPassThroughQueueObserver = new SecsMessagePassThroughQueueObserver(this.executorService());
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
	
	
	/* Secs-Log Receive Listener */
	
	@Override
	public boolean addSecsLogListener(SecsLogListener l) {
		return this.logQueuObserver.addListener(l);
	}
	
	@Override
	public boolean removeSecsLogListener(SecsLogListener l) {
		return this.logQueuObserver.removeListener(l);
	}
	
	public void notifyLog(AbstractSecsLog log) throws InterruptedException {
		log.subjectHeader(this.config.logSubjectHeader().toString());
		this.logQueuObserver.put(log);
	}
	
	public void notifyLog(Throwable t) throws InterruptedException {
		
		this.notifyLog(new AbstractSecsThrowableLog(t) {
			
			private static final long serialVersionUID = -1271705310309086030L;
		});
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
	
	
	/* Try-Send Secs-Message Pass-through Listener */
	
	@Override
	public boolean addTrySendSecsMessagePassThroughListener(SecsMessagePassThroughListener l) {
		return this.trySendSecsMsgPassThroughQueueObserver.addListener(l);
	}
	
	@Override
	public boolean removeTrySendSecsMessagePassThroughListener(SecsMessagePassThroughListener l) {
		return this.trySendSecsMsgPassThroughQueueObserver.removeListener(l);
	}
	
	public final void notifyTrySendSecsMessagePassThrough(SecsMessage msg) throws InterruptedException {
		this.trySendSecsMsgPassThroughQueueObserver.put(msg);
	}
	
	
	/* Sended Secs-Message Pass-through Listener */
	
	@Override
	public boolean addSendedSecsMessagePassThroughListener(SecsMessagePassThroughListener l) {
		return this.sendedSecsMsgPassThroughQueueObserver.addListener(l);
	}
	
	@Override
	public boolean removeSendedSecsMessagePassThroughListener(SecsMessagePassThroughListener l) {
		return this.sendedSecsMsgPassThroughQueueObserver.removeListener(l);
	}
	
	public final void notifySendedSecsMessagePassThrough(SecsMessage msg) throws InterruptedException {
		this.sendedSecsMsgPassThroughQueueObserver.put(msg);
	}
	
	
	/* Receive Secs-Message Pass-through Listener */
	
	@Override
	public boolean addReceiveSecsMessagePassThroughListener(SecsMessagePassThroughListener l) {
		return this.recvSecsMsgPassThroughQueueObserver.addListener(l);
	}
	
	@Override
	public boolean removeReceiveSecsMessagePassThroughListener(SecsMessagePassThroughListener l) {
		return this.recvSecsMsgPassThroughQueueObserver.removeListener(l);
	}
	
	public final void notifyReceiveSecsMessagePassThrough(SecsMessage msg) throws InterruptedException {
		this.recvSecsMsgPassThroughQueueObserver.put(msg);
	}
	
}
