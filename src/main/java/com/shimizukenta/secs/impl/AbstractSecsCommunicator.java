package com.shimizukenta.secs.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.shimizukenta.secs.AbstractSecsCommunicatorConfig;
import com.shimizukenta.secs.SecsCommunicatableStateChangeBiListener;
import com.shimizukenta.secs.SecsCommunicatableStateChangeListener;
import com.shimizukenta.secs.SecsCommunicator;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsLogListener;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsMessagePassThroughListener;
import com.shimizukenta.secs.SecsMessageReceiveBiListener;
import com.shimizukenta.secs.SecsMessageReceiveListener;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.gem.Gem;
import com.shimizukenta.secs.gem.impl.AbstractGem;
import com.shimizukenta.secs.local.property.BooleanProperty;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.sml.SmlMessage;

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
	
	private final SecsLogQueueObserver logQueuObserver;
	
	private final SecsMessageReceiveQueueBiObserver msgRecvQueueObserver;
	
	private final SecsMessagePassThroughQueueObserver trySendMsgPassThroughQueueObserver;
	private final SecsMessagePassThroughQueueObserver sendedMsgPassThroughQueueObserver;
	private final SecsMessagePassThroughQueueObserver recvMsgPassThroughQueueObserver;
	
	
	public AbstractSecsCommunicator(AbstractSecsCommunicatorConfig config) {
		super();
		
		this.config = config;
		this.gem = new AbstractGem(this, config.gem()) {};
		
		this.logQueuObserver = new SecsLogQueueObserver(this);
		
		this.msgRecvQueueObserver = new SecsMessageReceiveQueueBiObserver(this);
		
		this.trySendMsgPassThroughQueueObserver = new SecsMessagePassThroughQueueObserver(this);
		this.sendedMsgPassThroughQueueObserver = new SecsMessagePassThroughQueueObserver(this);
		this.recvMsgPassThroughQueueObserver = new SecsMessagePassThroughQueueObserver(this);
	}
	
	@Override
	public void open() throws IOException {
		
		synchronized ( this.syncOpen ) {
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
			if ( ! this.isOpen() ) {
				this.open();
			}
		}
	}
	
	abstract public Optional<SecsMessage> templateSend(int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException;
	
	abstract public Optional<SecsMessage> templateSend(SecsMessage primaryMsg, int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException;
	
	@Override
	public Optional<SecsMessage> send(int strm, int func, boolean wbit)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		return templateSend(strm, func, wbit, Secs2.empty());
	}
	
	@Override
	public Optional<SecsMessage> send(int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		return templateSend(strm, func, wbit, secs2);
	}
	
	@Override
	public Optional<SecsMessage> send(SecsMessage primaryMsg, int strm, int func, boolean wbit)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		return templateSend(primaryMsg, strm, func, wbit, Secs2.empty());
	}
	
	@Override
	public Optional<SecsMessage> send(SecsMessage primaryMsg, int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		return templateSend(primaryMsg, strm, func, wbit, secs2);
	}
	
	@Override
	public Optional<SecsMessage> send(SmlMessage sml)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		return templateSend(sml.getStream(), sml.getFunction(), sml.wbit(), sml.secs2());
	}
	
	@Override
	public Optional<SecsMessage> send(SecsMessage primaryMsg, SmlMessage sml)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		return templateSend(primaryMsg, sml.getStream(), sml.getFunction(), sml.wbit(), sml.secs2());
	}
	
	
	/* Secs-Message Receive Listener */
//	private final Collection<SecsMessageReceiveListener> msgRecvListeners = new CopyOnWriteArrayList<>();
	
	@Override
	public boolean addSecsMessageReceiveListener(SecsMessageReceiveListener l) {
//		return msgRecvListeners.add(Objects.requireNonNull(l));
		return this.msgRecvQueueObserver.addListener(l);
	}
	
	@Override
	public boolean removeSecsMessageReceiveListener(SecsMessageReceiveListener l) {
//		return msgRecvListeners.remove(Objects.requireNonNull(l));
		return this.msgRecvQueueObserver.removeListener(l);
	}
	
	@Override
	public boolean addSecsMessageReceiveListener(SecsMessageReceiveBiListener l) {
		return this.msgRecvQueueObserver.addBiListener(l);
	}
	
	@Override
	public boolean removeSecsMessageReceiveListener(SecsMessageReceiveBiListener l) {
		return this.msgRecvQueueObserver.removeBiListener(l);
	}
	
	public void notifyReceiveMessage(AbstractSecsMessage msg) throws InterruptedException {
		this.msgRecvQueueObserver.put(msg);
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
	public boolean addSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeListener l) {
		return this.communicatable.addChangeListener(l::changed);
	}
	
	@Override
	public boolean removeSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeListener l) {
		return this.communicatable.removeChangeListener(l::changed);
	}
	
	private final Map<SecsCommunicatableStateChangeBiListener, SecsCommunicatableStateChangeListener> biCommStateMap = new HashMap<>();
	
	@Override
	public boolean addSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeBiListener l) {
		
		final SecsCommunicatableStateChangeListener x = f -> {
			l.changed(f, this);
		};
		
		synchronized ( this.biCommStateMap ) {
			this.biCommStateMap.put(l, x);
			return this.communicatable.addChangeListener(x::changed);
		}
	}
	
	@Override
	public boolean removeSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeBiListener l) {
		synchronized ( this.biCommStateMap ) {
			final SecsCommunicatableStateChangeListener x = this.biCommStateMap.remove(l);
			if ( x != null ) {
				return this.communicatable.removeChangeListener(x::changed);
			}
			return false;
		}
	}
	
	public void notifyCommunicatableStateChange(boolean f) {
		this.communicatable.set(f);
	}
	
	
	/* Try-Send Secs-Message Pass-through Listener */
	
	@Override
	public boolean addTrySendMessagePassThroughListener(SecsMessagePassThroughListener l) {
		return this.trySendMsgPassThroughQueueObserver.addListener(l);
	}
	
	@Override
	public boolean removeTrySendMessagePassThroughListener(SecsMessagePassThroughListener l) {
		return this.trySendMsgPassThroughQueueObserver.removeListener(l);
	}
	
	public void notifyTrySendMessagePassThrough(SecsMessage msg) throws InterruptedException {
		this.trySendMsgPassThroughQueueObserver.put(msg);
	}
	
	
	/* Sended Secs-Message Pass-through Listener */
	
	@Override
	public boolean addSendedMessagePassThroughListener(SecsMessagePassThroughListener l) {
		return this.sendedMsgPassThroughQueueObserver.addListener(l);
	}
	
	@Override
	public boolean removeSendedMessagePassThroughListener(SecsMessagePassThroughListener l) {
		return this.sendedMsgPassThroughQueueObserver.removeListener(l);
	}
	
	public void notifySendedMessagePassThrough(SecsMessage msg) throws InterruptedException {
		this.sendedMsgPassThroughQueueObserver.put(msg);
	}
	
	
	/* Receive Secs-Message Pass-through Listener */
	
	@Override
	public boolean addReceiveMessagePassThroughListener(SecsMessagePassThroughListener l) {
		return this.recvMsgPassThroughQueueObserver.addListener(l);
	}
	
	@Override
	public boolean removeReceiveMessagePassThroughListener(SecsMessagePassThroughListener l) {
		return this.recvMsgPassThroughQueueObserver.removeListener(l);
	}
	
	public void notifyReceiveMessagePassThrough(SecsMessage msg) throws InterruptedException {
		this.recvMsgPassThroughQueueObserver.put(msg);
	}
	
}
