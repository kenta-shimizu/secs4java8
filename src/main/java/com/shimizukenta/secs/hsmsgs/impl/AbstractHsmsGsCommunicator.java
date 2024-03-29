package com.shimizukenta.secs.hsmsgs.impl;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ClosedChannelException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import com.shimizukenta.secs.SecsCommunicatableStateChangeBiListener;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsMessagePassThroughListener;
import com.shimizukenta.secs.SecsMessageReceiveBiListener;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.hsms.HsmsCommunicateStateChangeBiListener;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessageDeselectStatus;
import com.shimizukenta.secs.hsms.HsmsMessagePassThroughListener;
import com.shimizukenta.secs.hsms.HsmsMessageReceiveBiListener;
import com.shimizukenta.secs.hsms.HsmsMessageRejectReason;
import com.shimizukenta.secs.hsms.HsmsMessageSelectStatus;
import com.shimizukenta.secs.hsms.HsmsMessageType;
import com.shimizukenta.secs.hsms.HsmsSendMessageException;
import com.shimizukenta.secs.hsms.HsmsSession;
import com.shimizukenta.secs.hsms.HsmsTimeoutT7Exception;
import com.shimizukenta.secs.hsms.HsmsWaitReplyMessageException;
import com.shimizukenta.secs.hsms.impl.AbstractHsmsMessage;
import com.shimizukenta.secs.hsms.impl.HsmsMessagePassThroughQueueObserver;
import com.shimizukenta.secs.hsmsgs.HsmsGsCommunicator;
import com.shimizukenta.secs.hsmsgs.HsmsGsCommunicatorConfig;
import com.shimizukenta.secs.hsmsgs.HsmsGsUnknownSessionIdException;
import com.shimizukenta.secs.impl.AbstractBaseCommunicator;
import com.shimizukenta.secs.impl.SecsMessagePassThroughQueueObserver;
import com.shimizukenta.secs.local.property.BooleanCompution;
import com.shimizukenta.secs.local.property.BooleanProperty;
import com.shimizukenta.secs.local.property.SetProperty;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.sml.SmlMessage;

public abstract class AbstractHsmsGsCommunicator extends AbstractBaseCommunicator implements HsmsGsCommunicator {
	
	private final HsmsGsCommunicatorConfig config;
	
	private final BooleanProperty sessionFixedProp = BooleanProperty.newInstance(false);
	
	private final HsmsGsMessageBuilder msgBuilder;
	
	private final SecsMessagePassThroughQueueObserver trySendSecsMsgPassThroughQueueObserver;
	private final SecsMessagePassThroughQueueObserver sendedSecsMsgPassThroughQueueObserver;
	private final SecsMessagePassThroughQueueObserver recvSecsMsgPassThroughQueueObserver;
	private final HsmsMessagePassThroughQueueObserver trySendHsmsMsgPassThroughQueueObserver;
	private final HsmsMessagePassThroughQueueObserver sendedHsmsMsgPassThroughQueueObserver;
	private final HsmsMessagePassThroughQueueObserver recvHsmsMsgPassThroughQueueObserver;
	
	
	public AbstractHsmsGsCommunicator(HsmsGsCommunicatorConfig config) {
		super();
		
		this.config = Objects.requireNonNull(config);
		
		this.config.sessionIds().addChangeListener(v -> {
			synchronized (this.sessionFixedProp) {
				if (this.sessionFixedProp.booleanValue()) {
					try {
						this.close();
					}
					catch (IOException giveup) {
					}
				}
			}
		});
		
		this.msgBuilder = new AbstractHsmsGsMessageBuilder(this.config) {};
		
		this.trySendSecsMsgPassThroughQueueObserver = new SecsMessagePassThroughQueueObserver(this.executorService());
		this.sendedSecsMsgPassThroughQueueObserver = new SecsMessagePassThroughQueueObserver(this.executorService());
		this.recvSecsMsgPassThroughQueueObserver = new SecsMessagePassThroughQueueObserver(this.executorService());
		this.trySendHsmsMsgPassThroughQueueObserver = new HsmsMessagePassThroughQueueObserver(this.executorService());
		this.sendedHsmsMsgPassThroughQueueObserver = new HsmsMessagePassThroughQueueObserver(this.executorService());
		this.recvHsmsMsgPassThroughQueueObserver = new HsmsMessagePassThroughQueueObserver(this.executorService());
	}
	
	public HsmsGsMessageBuilder getHsmsGsMessageBuilder() {
		return this.msgBuilder;
	}
	
	@Override
	public void open() throws IOException {
		this.getAbstractHsmsGsSessions();
		super.open();
	}
	
	private final Object syncClosed = new Object();
	
	@Override
	public void close() throws IOException {
		
		synchronized ( this.syncClosed ) {
			
			if ( this.isClosed() ) {
				return;
			}
//			
//			try {
//				
//				for ( AbstractHsmsGsSession s : this.getAbstractHsmsGsSessions() ) {
//					s.separate();
//				}
//			}
//			catch ( InterruptedException ignore ) {
//			}
			
			super.close();
		}
	}
	
	
	private final Set<AbstractHsmsGsSession> sessions = new HashSet<>();
	
	private Set<AbstractHsmsGsSession> getAbstractHsmsGsSessions() {
		synchronized (this.sessionFixedProp) {
			if (! this.sessionFixedProp.booleanValue()) {
				this.sessionFixedProp.setTrue();
				for (Integer i : this.config.sessionIds()) {
					this.sessions.add(new AbstractHsmsGsSession(this, this.config, i.intValue()) {});
				}
			}
			return this.sessions;
		}
	}
	
	@Override
	public Set<HsmsSession> getHsmsSessions() {
		return Collections.unmodifiableSet(this.getAbstractHsmsGsSessions());
	}
	
	@Override
	public HsmsSession getHsmsSession(int sessionId) throws HsmsGsUnknownSessionIdException {
		return this.optionalHsmsSession(sessionId)
				.orElseThrow(() -> new HsmsGsUnknownSessionIdException(sessionId));
	}
	
	@Override
	public boolean existHsmsSession(int sessionId) {
		return this.getAbstractHsmsGsSessions().stream()
				.anyMatch(s -> s.sessionId() == sessionId);
	}
	
	@Override
	public Optional<HsmsSession> optionalHsmsSession(int sessionId) {
		return this.getAbstractHsmsGsSessions().stream()
				.filter(s -> s.sessionId() == sessionId)
				.findAny()
				.map(s -> (HsmsSession)s);
	}
	
	protected void completionAction(AsynchronousSocketChannel channel)
			throws InterruptedException {
		
		try (
			HsmsGsAsynchronousSocketChannelFacade asyncChannel = new HsmsGsAsynchronousSocketChannelFacade(this.config, channel, this);	
				) {
			
			final SetProperty<AbstractHsmsGsSession> selectedSessions = SetProperty.newInstance();
			
			final Collection<Callable<Void>> tasks = Arrays.asList(
					() -> {
						try {
							this.trySelectRequesting(asyncChannel, selectedSessions);
						}
						catch (InterruptedException ignore) {
						}
						
						return null;
					},
					() -> {
						try {
							this.mainTask(asyncChannel, selectedSessions);
						}
						catch (InterruptedException ignore) {
						}
						
						return null;
					},
					() -> {
						try {
							for (;;) {
								selectedSessions.waitUntilIsEmpty();
								selectedSessions.waitUntilIsNotEmpty(this.config.timeout().t7());
							}
						}
						catch (TimeoutException e) {
							this.notifyLog(new HsmsTimeoutT7Exception());
						}
						catch (InterruptedException ignore) {
						}
						
						return null;
					},
					() -> {
						try {
							asyncChannel.waitUntilShutdown();
						}
						catch (InterruptedException ignore) {
						}
						
						return null;
					});
			
			try {
				this.executeInvokeAny(tasks);
			}
			catch (ExecutionException e) {
				Throwable t = e.getCause();
				
				if (! (t instanceof ClosedChannelException)) {
					this.notifyLog(t);
				}
			}
			finally {
				synchronized (selectedSessions) {
					for (AbstractHsmsGsSession session : selectedSessions) {
						session.unsetChannel();
					}
				}
			}
		}
		catch (IOException e) {
			this.notifyLog(e);
		}
	}
	
	private void trySelectRequesting(
			HsmsGsAsynchronousSocketChannelFacade asyncChannel,
			SetProperty<AbstractHsmsGsSession> selectedSessions
			) throws InterruptedException {
		
		final BooleanCompution existNotSelectedSession = selectedSessions.computeSize().computeIsNotEqualTo(this.getAbstractHsmsGsSessions().size());
		
		for ( ;; ) {
			
			existNotSelectedSession.waitUntilTrue();
			
			this.config.isTrySelectRequest().waitUntilTrue();
			
			for (AbstractHsmsGsSession session : this.getAbstractHsmsGsSessions()) {
				if (! selectedSessions.contains(session)) {
					
					try {
						HsmsMessageSelectStatus status = asyncChannel.send(this.getHsmsGsMessageBuilder().buildSelectRequest(session))
								.map(HsmsMessageSelectStatus::get)
								.orElse(HsmsMessageSelectStatus.UNKNOWN);
						
						switch ( status ) {
						case SUCCESS:
						case ACTIVED:
						case ENTITY_ACTIVED: {
							
							synchronized (selectedSessions) {
								session.setChannel(asyncChannel);
								session.notifyHsmsCommunicateStateToSelected();
								selectedSessions.add(session);
							}
							
							break;
						}
						default: {
							/* Nothing */
						}
						}
					}
					catch (HsmsSendMessageException | HsmsWaitReplyMessageException e) {
						this.notifyLog(e);
					}
				}
			}
			
			this.config.retrySelectRequestTimeout().sleep();
		}
	}
	
	private void mainTask(
			HsmsGsAsynchronousSocketChannelFacade asyncChannel,
			SetProperty<AbstractHsmsGsSession> selectedSessions
			) throws InterruptedException {
		
		for ( ;; ) {
			final AbstractHsmsMessage msg = asyncChannel.takePrimaryHsmsMessage();
			
			try {
				switch (msg.messageType()) {
				case DATA: {
					
					final AbstractHsmsGsSession session = selectedSessions.stream()
							.filter(s -> s.sessionId() == msg.sessionId())
							.findAny()
							.orElse(null);
					
					if (session == null) {
						asyncChannel.send(this.getHsmsGsMessageBuilder().buildRejectRequest(msg, HsmsMessageRejectReason.NOT_SELECTED));
					} else {
						session.notifyHsmsMessageReceive(msg);
					}
					
					break;
				}
				case SELECT_REQ: {
					
					final AbstractHsmsGsSession selectedSession = selectedSessions.stream()
							.filter(s -> s.sessionId() == msg.sessionId())
							.findAny()
							.orElse(null);
					
					if (selectedSession == null) {
						
						final AbstractHsmsGsSession session = this.getAbstractHsmsGsSessions().stream()
								.filter(s -> s.sessionId() == msg.sessionId())
								.findAny()
								.orElse(null);
						
						if (session == null) {
							
							asyncChannel.send(this.getHsmsGsMessageBuilder().buildSelectResponse(msg, HsmsMessageSelectStatus.ENTITY_UNKNOWN));
							
						} else {
							
							if (session.setChannel(asyncChannel)) {
								
								synchronized (selectedSessions) {
									selectedSessions.add(session);
									session.notifyHsmsCommunicateStateToSelected();
								}
								
								asyncChannel.send(this.getHsmsGsMessageBuilder().buildSelectResponse(msg, HsmsMessageSelectStatus.SUCCESS));
								
							} else {
								
								asyncChannel.send(this.getHsmsGsMessageBuilder().buildSelectResponse(msg, HsmsMessageSelectStatus.ENTITY_ALREADY_USED));
							}
						}
						
					} else {
						
						asyncChannel.send(this.getHsmsGsMessageBuilder().buildSelectResponse(msg, HsmsMessageSelectStatus.ENTITY_ACTIVED));
					}
					
					break;
				}
				case DESELECT_REQ: {
					
					final AbstractHsmsGsSession selectedSession = selectedSessions.stream()
							.filter(s -> s.sessionId() == msg.sessionId())
							.findAny()
							.orElse(null);
					
					if (selectedSession == null) {
						
						asyncChannel.send(this.getHsmsGsMessageBuilder().buildDeselectResponse(msg, HsmsMessageDeselectStatus.NO_SELECTED));
						
					} else {
						
						synchronized (selectedSessions) {
							selectedSession.unsetChannel();
							selectedSessions.remove(selectedSession);
						}
						
						asyncChannel.send(this.getHsmsGsMessageBuilder().buildDeselectResponse(msg, HsmsMessageDeselectStatus.SUCCESS));
					}
					
					break;
				}
				case LINKTEST_REQ: {
					
					asyncChannel.send(this.getHsmsGsMessageBuilder().buildLinktestResponse(msg));
					break;
				}
				case SEPARATE_REQ: {
					
					final AbstractHsmsGsSession session = selectedSessions.stream()
							.filter(s -> s.sessionId() == msg.sessionId())
							.findAny()
							.orElse(null);
					
					if (session != null) {
						
						synchronized (selectedSessions) {
							selectedSessions.remove(session);
							session.unsetChannel();
						}
					}
					
					break;
				}
				case SELECT_RSP:
				case DESELECT_RSP:
				case LINKTEST_RSP:
				case REJECT_REQ: {
					
					asyncChannel.send(this.getHsmsGsMessageBuilder().buildRejectRequest(msg, HsmsMessageRejectReason.TRANSACTION_NOT_OPEN));
					break;
				}
				default: {
					
					if (HsmsMessageType.supportSType(msg) ) {
						
						if (! HsmsMessageType.supportPType(msg)) {
							asyncChannel.send(this.getHsmsGsMessageBuilder().buildRejectRequest(msg, HsmsMessageRejectReason.NOT_SUPPORT_TYPE_P));
						}
						
					} else {
						
						asyncChannel.send(this.getHsmsGsMessageBuilder().buildRejectRequest(msg, HsmsMessageRejectReason.NOT_SUPPORT_TYPE_S));
					}
				}
				}
			}
			catch (HsmsSendMessageException | HsmsWaitReplyMessageException e) {
				this.notifyLog(e);
			}
		}
	}
	
	
	@Override
	public Optional<SecsMessage> send(int sessionId, int strm, int func, boolean wbit)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		
		return getHsmsSession(sessionId).send(strm, func, wbit);
	}
	
	@Override
	public Optional<SecsMessage> send(int sessionId, int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		
		return getHsmsSession(sessionId).send(strm, func, wbit, secs2);
	}
	
	@Override
	public Optional<SecsMessage> send(int sessionId, SecsMessage primaryMsg, int strm, int func, boolean wbit)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		
		return getHsmsSession(sessionId).send(primaryMsg, strm, func, wbit);
	}
	
	@Override
	public Optional<SecsMessage> send(int sessionId, SecsMessage primaryMsg, int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		
		return getHsmsSession(sessionId).send(primaryMsg, strm, func, wbit, secs2);
	}
	
	@Override
	public Optional<SecsMessage> send(int sessionId, SmlMessage sml)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		
		return getHsmsSession(sessionId).send(sml);
	}
	
	@Override
	public Optional<SecsMessage> send(int sessionId, SecsMessage primaryMsg, SmlMessage sml)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException, InterruptedException {
		
		return getHsmsSession(sessionId).send(primaryMsg, sml);
	}
	
	
	/* Receive Primary-Data-Message */
	
	@Override
	public boolean addSecsMessageReceiveBiListener(SecsMessageReceiveBiListener biListener) {
		boolean f = true;
		for (AbstractHsmsGsSession s : this.getAbstractHsmsGsSessions()) {
			if(! s.addSecsMessageReceiveBiListener(biListener)) {
				f = false;
			}
		}
		return f;
	}
	
	@Override
	public boolean removeSecsMessageReceiveBiListener(SecsMessageReceiveBiListener biListener) {
		boolean f = true;
		for (AbstractHsmsGsSession s : this.getAbstractHsmsGsSessions()) {
			if(! s.removeSecsMessageReceiveBiListener(biListener)) {
				f = false;
			}
		}
		return f;
	}
	
	@Override
	public boolean addHsmsMessageReceiveBiListener(HsmsMessageReceiveBiListener biListener) {
		boolean f = true;
		for (AbstractHsmsGsSession s : this.getAbstractHsmsGsSessions()) {
			if(! s.addHsmsMessageReceiveBiListener(biListener)) {
				f = false;
			}
		}
		return f;
	}
	
	@Override
	public boolean removeHsmsMessageReceiveBiListener(HsmsMessageReceiveBiListener biListener) {
		boolean f = true;
		for (AbstractHsmsGsSession s : this.getAbstractHsmsGsSessions()) {
			if(! s.removeHsmsMessageReceiveBiListener(biListener)) {
				f = false;
			}
		}
		return f;
	}
	
	
	/* Communicate state Detectable */
	
	@Override
	public boolean addSecsCommunicatableStateChangeBiListener(SecsCommunicatableStateChangeBiListener biListener) {
		boolean f = true;
		for (AbstractHsmsGsSession s : this.getAbstractHsmsGsSessions()) {
			if (! s.addSecsCommunicatableStateChangeBiListener(biListener)) {
				f = false;
			}
		}
		return f;
	}
	
	@Override
	public boolean removeSecsCommunicatableStateChangeBiListener(SecsCommunicatableStateChangeBiListener biListener) {
		boolean f = true;
		for (AbstractHsmsGsSession s : this.getAbstractHsmsGsSessions()) {
			if (! s.removeSecsCommunicatableStateChangeBiListener(biListener)) {
				f = false;
			}
		}
		return f;
	}
	
	@Override
	public boolean addHsmsCommunicateStateChangeBiListener(HsmsCommunicateStateChangeBiListener biListener) {
		boolean f = true;
		for (AbstractHsmsGsSession s : this.getAbstractHsmsGsSessions()) {
			if (! s.addHsmsCommunicateStateChangeBiListener(biListener)) {
				f = false;
			}
		}
		return f;
	}
	
	@Override
	public boolean removeHsmsCommunicateStateChangeBiListener(HsmsCommunicateStateChangeBiListener biListener) {
		boolean f = true;
		for (AbstractHsmsGsSession s : this.getAbstractHsmsGsSessions()) {
			if (! s.removeHsmsCommunicateStateChangeBiListener(biListener)) {
				f = false;
			}
		}
		return f;
	}
	
	
	
//	@Override
//	public boolean addSecsLogListener(SecsLogListener lstnr) {
//		return this.logQueueObserver.addListener(lstnr);
//	}
//	
//	@Override
//	public boolean removeSecsLogListener(SecsLogListener lstnr) {
//		return this.logQueueObserver.removeListener(lstnr);
//	}
//	
//	public void notifyLog(AbstractSecsLog log) throws InterruptedException {
//		
//		log.subjectHeader(this.config.logSubjectHeader().toString());
//		
//		this.logQueueObserver.put(log);
//		
//		final SecsMessage msg = log.optionalSecsMessage().orElse(null);
//		if ( msg != null ) {
//			for ( AbstractHsmsSession s : this.getAbstractHsmsSessions() ) {
//				if ( s.sessionId() == msg.sessionId() ) {
//					s.notifyLog(log);
//				}
//			}
//		}
//	}
	
	public void notifyLog(Throwable t) throws InterruptedException {
		
		//TODO
		//nothing
	}
	
	public void notifyLog(Object o) throws InterruptedException {
		
		//TODO
		//nothing
	}
	
	
	
	
	
	/* Pass Through */
	
	@Override
	public boolean addTrySendSecsMessagePassThroughListener(SecsMessagePassThroughListener listener) {
		return this.trySendSecsMsgPassThroughQueueObserver.addListener(listener);
	}
	
	@Override
	public boolean removeTrySendSecsMessagePassThroughListener(SecsMessagePassThroughListener listener) {
		return this.trySendSecsMsgPassThroughQueueObserver.removeListener(listener);
	}
	
	@Override
	public boolean addTrySendHsmsMessagePassThroughListener(HsmsMessagePassThroughListener listener) {
		return this.trySendHsmsMsgPassThroughQueueObserver.addListener(listener);
	}
	
	@Override
	public boolean removeTrySendHsmsMessagePassThroughListener(HsmsMessagePassThroughListener listener) {
		return this.trySendHsmsMsgPassThroughQueueObserver.removeListener(listener);
	}
	
	@Override
	public boolean addSendedSecsMessagePassThroughListener(SecsMessagePassThroughListener listener) {
		return this.sendedSecsMsgPassThroughQueueObserver.addListener(listener);
	}
	
	@Override
	public boolean removeSendedSecsMessagePassThroughListener(SecsMessagePassThroughListener listener) {
		return this.sendedSecsMsgPassThroughQueueObserver.removeListener(listener);
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
	public boolean addReceiveSecsMessagePassThroughListener(SecsMessagePassThroughListener listener) {
		return this.recvSecsMsgPassThroughQueueObserver.addListener(listener);
	}
	
	@Override
	public boolean removeReceiveSecsMessagePassThroughListener(SecsMessagePassThroughListener listener) {
		return this.recvSecsMsgPassThroughQueueObserver.removeListener(listener);
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
		this.trySendSecsMsgPassThroughQueueObserver.put(message);
		this.trySendHsmsMsgPassThroughQueueObserver.put(message);
	}
	
	public void notifySendedHsmsMessagePassThrough(HsmsMessage message) throws InterruptedException {
		this.sendedSecsMsgPassThroughQueueObserver.put(message);
		this.sendedHsmsMsgPassThroughQueueObserver.put(message);
	}
	
	public void notifyReceiveHsmsMessagePassThrough(HsmsMessage message) throws InterruptedException {
		this.recvSecsMsgPassThroughQueueObserver.put(message);
		this.recvHsmsMsgPassThroughQueueObserver.put(message);
	}

}
