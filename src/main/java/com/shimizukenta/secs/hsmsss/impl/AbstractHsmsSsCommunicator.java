package com.shimizukenta.secs.hsmsss.impl;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsMessageReceiveBiListener;
import com.shimizukenta.secs.SecsMessageReceiveListener;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.hsms.HsmsCommunicateState;
import com.shimizukenta.secs.hsms.HsmsCommunicateStateChangeBiListener;
import com.shimizukenta.secs.hsms.HsmsCommunicateStateChangeListener;
import com.shimizukenta.secs.hsms.HsmsException;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessagePassThroughListener;
import com.shimizukenta.secs.hsms.HsmsMessageReceiveBiListener;
import com.shimizukenta.secs.hsms.HsmsMessageReceiveListener;
import com.shimizukenta.secs.hsms.HsmsSendMessageException;
import com.shimizukenta.secs.hsms.HsmsWaitReplyMessageException;
import com.shimizukenta.secs.hsms.impl.AbstractHsmsAsynchronousSocketChannelFacade;
import com.shimizukenta.secs.hsms.impl.HsmsMessagePassThroughQueueObserver;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicator;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicatorConfig;
import com.shimizukenta.secs.impl.AbstractSecsCommunicator;
import com.shimizukenta.secs.secs2.Secs2;

/**
 * This abstract class is implementation of HSMS-SS (SEMI-E37.1).
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractHsmsSsCommunicator extends AbstractSecsCommunicator implements HsmsSsCommunicator {
	
	private final AbstractHsmsSsSession session;
	
	private final HsmsMessagePassThroughQueueObserver trySendHsmsMsgPassThroughQueueObserver;
	private final HsmsMessagePassThroughQueueObserver sendedHsmsMsgPassThroughQueueObserver;
	private final HsmsMessagePassThroughQueueObserver recvHsmsMsgPassThroughQueueObserver;
	
	
	public AbstractHsmsSsCommunicator(HsmsSsCommunicatorConfig config) {
		super(Objects.requireNonNull(config));
		
		this.session = new AbstractHsmsSsSession(this, config) {};
		this.session.addSecsCommunicatableStateChangeListener(this::notifyCommunicatableStateChange);
		
		this.trySendHsmsMsgPassThroughQueueObserver = new HsmsMessagePassThroughQueueObserver(this.executorService());
		this.sendedHsmsMsgPassThroughQueueObserver = new HsmsMessagePassThroughQueueObserver(this.executorService());
		this.recvHsmsMsgPassThroughQueueObserver = new HsmsMessagePassThroughQueueObserver(this.executorService());
	}
	
	public AbstractHsmsSsSession getSession() {
		return this.session;
	}
	
	@Override
	public int deviceId() {
		return this.getSession().deviceId();
	}
	
	@Override
	public int sessionId() {
		return this.getSession().sessionId();
	}
	
	@Override
	public boolean isEquip() {
		return this.getSession().isEquip();
	}
	
	
	private final HsmsSsMessageBuilder msgBuilder = new AbstractHsmsSsMessageBuilder() {};
	
	public HsmsSsMessageBuilder getHsmsSmMessageBuilder() {
		return this.msgBuilder;
	}
	
	
	@Override
	public void open() throws IOException {
		super.open();
	}
	
	private final Object syncClosed = new Object();
	
	@Override
	public void close() throws IOException {
		
		synchronized ( this.syncClosed ) {
			
			if ( this.isClosed() ) {
				return;
			}
			
			try {
				this.getSession().separate();
			}
			catch ( InterruptedException giveup ) {
			}
			
			super.close();
		}
	}
	
	@Override
	public boolean linktest() throws InterruptedException {
		AbstractHsmsAsynchronousSocketChannelFacade channel = this.getSession().getChannel();
		if (channel != null) {
			return channel.linktest();
		}
		return false;
	}
	
	@Override
	public Optional<SecsMessage> send(int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		
		return this.getSession().send(strm, func, wbit, secs2);
	}
	
	@Override
	public Optional<SecsMessage> send(SecsMessage primaryMsg, int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		
		return this.getSession().send(primaryMsg, strm, func, wbit, secs2);
	}
	
	@Override
	public Optional<HsmsMessage> send(HsmsMessage msg)
			throws HsmsSendMessageException,
			HsmsWaitReplyMessageException,
			HsmsException,
			InterruptedException {
		
		return this.getSession().send(msg);
	}
	
	
	/* Receive Primary-Data-Message */
	
	@Override
	public boolean addSecsMessageReceiveListener(SecsMessageReceiveListener listener) {
		return this.getSession().addSecsMessageReceiveListener(listener);
	}
	
	@Override
	public boolean removeSecsMessageReceiveListener(SecsMessageReceiveListener listener) {
		return this.getSession().removeSecsMessageReceiveListener(listener);
	}
	
	@Override
	public boolean addSecsMessageReceiveBiListener(SecsMessageReceiveBiListener biListener) {
		return this.getSession().addSecsMessageReceiveBiListener(biListener);
	}
	
	@Override
	public boolean removeSecsMessageReceiveBiListener(SecsMessageReceiveBiListener biListener) {
		return this.getSession().removeSecsMessageReceiveBiListener(biListener);
	}
	
	@Override
	public boolean addHsmsMessageReceiveListener(HsmsMessageReceiveListener listener) {
		return this.getSession().addHsmsMessageReceiveListener(listener);
	}
	
	@Override
	public boolean removeHsmsMessageReceiveListener(HsmsMessageReceiveListener listener) {
		return this.getSession().removeHsmsMessageReceiveListener(listener);
	}
	
	@Override
	public boolean addHsmsMessageReceiveBiListener(HsmsMessageReceiveBiListener biListener) {
		return this.getSession().addHsmsMessageReceiveBiListener(biListener);
	}
	
	@Override
	public boolean removeHsmsMessageReceiveBiListener(HsmsMessageReceiveBiListener biListener) {
		return this.getSession().removeHsmsMessageReceiveBiListener(biListener);
	}
	
	
	/* HSMS communicate state */
	
	@Override
	public HsmsCommunicateState getHsmsCommunicateState() {
		return this.getSession().getHsmsCommunicateState();
	}
	
	@Override
	public void waitUntilHsmsCommunicateState(HsmsCommunicateState state) throws InterruptedException {
		this.getSession().waitUntilHsmsCommunicateState(state);
	}
	
	@Override
	public void waitUntilHsmsCommunicateState(HsmsCommunicateState state, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.getSession().waitUntilHsmsCommunicateState(state, timeout, unit);
	}
	
	@Override
	public void waitUntilNotHsmsCommunicateState(HsmsCommunicateState state) throws InterruptedException {
		this.getSession().waitUntilNotHsmsCommunicateState(state);
	}
	
	@Override
	public void waitUntilNotHsmsCommunicateState(HsmsCommunicateState state, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		this.getSession().waitUntilNotHsmsCommunicateState(state, timeout, unit);
	}
	
	@Override
	public boolean addHsmsCommunicateStateChangeListener(HsmsCommunicateStateChangeListener listener) {
		return this.getSession().addHsmsCommunicateStateChangeListener(listener);
	}
	
	@Override
	public boolean removeHsmsCommunicateStateChangeListener(HsmsCommunicateStateChangeListener listener) {
		return this.getSession().removeHsmsCommunicateStateChangeListener(listener);
	}
	
	@Override
	public boolean addHsmsCommunicateStateChangeBiListener(HsmsCommunicateStateChangeBiListener biListener) {
		return this.getSession().addHsmsCommunicateStateChangeBiListener(biListener);
	}
	
	@Override
	public boolean removeHsmsCommunicateStateChangeBiListener(HsmsCommunicateStateChangeBiListener biListener) {
		return this.getSession().removeHsmsCommunicateStateChangeBiListener(biListener);
	}
	
	
	/* Pass Through */
	
	@Override
	public boolean addTrySendHsmsMessagePassThroughListener(HsmsMessagePassThroughListener listener) {
		return this.trySendHsmsMsgPassThroughQueueObserver.addListener(listener);
	}
	
	@Override
	public boolean removeTrySendHsmsMessagePassThroughListener(HsmsMessagePassThroughListener listener) {
		return this.trySendHsmsMsgPassThroughQueueObserver.removeListener(listener);
	}
	
	@Override
	public boolean addSendedHsmsMessagePassThroughListener(HsmsMessagePassThroughListener listener) {
		return this.sendedHsmsMsgPassThroughQueueObserver.addListener(listener);
	}
	
	@Override
	public boolean removeSendedHsmsMessagePassThroughListener(HsmsMessagePassThroughListener listener) {
		return this.sendedHsmsMsgPassThroughQueueObserver.removeListener(listener);
	}
	
	@Override
	public boolean addReceiveHsmsMessagePassThroughListener(HsmsMessagePassThroughListener listener) {
		return this.recvHsmsMsgPassThroughQueueObserver.addListener(listener);
	}
	
	@Override
	public boolean removeReceiveHsmsMessagePassThroughListener(HsmsMessagePassThroughListener listener) {
		return this.recvHsmsMsgPassThroughQueueObserver.removeListener(listener);
	}
	
	
	public void notifyTrySendHsmsMessagePassThrough(HsmsMessage message) throws InterruptedException {
		if (message.isDataMessage()) {
			this.notifyTrySendSecsMessagePassThrough(message);
		}
		this.trySendHsmsMsgPassThroughQueueObserver.put(message);
	}
	
	public void notifySendedHsmsMessagePassThrough(HsmsMessage message) throws InterruptedException {
		if (message.isDataMessage()) {
			this.notifySendedSecsMessagePassThrough(message);
		}
		this.sendedHsmsMsgPassThroughQueueObserver.put(message);
	}
	
	public void notifyReceiveHsmsMessagePassThrough(HsmsMessage message) throws InterruptedException {
		if (message.isDataMessage()) {
			this.notifyReceiveSecsMessagePassThrough(message);
		}
		this.recvHsmsMsgPassThroughQueueObserver.put(message);
	}
	
}
