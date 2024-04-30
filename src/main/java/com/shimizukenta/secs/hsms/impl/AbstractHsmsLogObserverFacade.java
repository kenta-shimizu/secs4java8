package com.shimizukenta.secs.hsms.impl;

import java.net.SocketAddress;
import java.util.concurrent.Executor;

import com.shimizukenta.secs.SecsLogListener;
import com.shimizukenta.secs.hsms.AbstractHsmsCommunicatorConfig;
import com.shimizukenta.secs.hsms.HsmsChannelConnectionLog;
import com.shimizukenta.secs.hsms.HsmsCommunicateState;
import com.shimizukenta.secs.hsms.HsmsLogObservable;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessagePassThroughLog;
import com.shimizukenta.secs.hsms.HsmsSessionCommunicateStateLog;
import com.shimizukenta.secs.impl.AbstractQueueObserver;
import com.shimizukenta.secs.impl.AbstractSecsLogObserverFacade;

public abstract class AbstractHsmsLogObserverFacade extends AbstractSecsLogObserverFacade implements HsmsLogObservable {
	
	private class HsmsMessagePassThroghLogObserver extends AbstractQueueObserver<SecsLogListener<? super HsmsMessagePassThroughLog>, HsmsMessagePassThroughLog> {
		
		public HsmsMessagePassThroghLogObserver(Executor executor) {
			super(executor);
		}
		
		@Override
		protected void notifyValueToListener(SecsLogListener<? super HsmsMessagePassThroughLog> listener, HsmsMessagePassThroughLog value) {
			listener.received(value);
		}
	}
	
	private class HsmsChannelConnectionLogObserver extends AbstractQueueObserver<SecsLogListener<? super HsmsChannelConnectionLog>, HsmsChannelConnectionLog> {
		
		public HsmsChannelConnectionLogObserver(Executor executor) {
			super(executor);
		}
		
		@Override
		protected void notifyValueToListener(SecsLogListener<? super HsmsChannelConnectionLog> listener, HsmsChannelConnectionLog value) {
			listener.received(value);
		}
	}
	
	private class HsmsSessionCommunicateStateLogObserver extends AbstractQueueObserver<SecsLogListener<? super HsmsSessionCommunicateStateLog>, HsmsSessionCommunicateStateLog> {
		
		public HsmsSessionCommunicateStateLogObserver(Executor executor) {
			super(executor);
		}
		
		@Override
		protected void notifyValueToListener(SecsLogListener<? super HsmsSessionCommunicateStateLog> listener, HsmsSessionCommunicateStateLog value) {
			listener.received(value);
		}
	}

	
	private final AbstractHsmsCommunicatorConfig config;
	
	private final HsmsMessagePassThroghLogObserver trySendHsmsMsgPassThroughLogObserver;
	private final HsmsMessagePassThroghLogObserver sendedHsmsMsgPassThroughLogObserver;
	private final HsmsMessagePassThroghLogObserver recvHsmsMsgPassThroughLogObserver;
	
	private final HsmsChannelConnectionLogObserver channelConnectionLogObserver;
	
	private final HsmsSessionCommunicateStateLogObserver hsmsSessionCommunicateStateLogObserver;
	
	public AbstractHsmsLogObserverFacade(AbstractHsmsCommunicatorConfig config, Executor executor) {
		super(config, executor);
		
		this.config = config;
		
		this.trySendHsmsMsgPassThroughLogObserver = new HsmsMessagePassThroghLogObserver(executor);
		this.sendedHsmsMsgPassThroughLogObserver = new HsmsMessagePassThroghLogObserver(executor);
		this.recvHsmsMsgPassThroughLogObserver = new HsmsMessagePassThroghLogObserver(executor);
		
		this.channelConnectionLogObserver = new HsmsChannelConnectionLogObserver(executor);
		
		this.hsmsSessionCommunicateStateLogObserver = new HsmsSessionCommunicateStateLogObserver(executor);
	}
	
	@Override
	public boolean addTrySendHsmsMessagePassThroughLogListener(SecsLogListener<? super HsmsMessagePassThroughLog> listener) {
		return this.trySendHsmsMsgPassThroughLogObserver.addListener(listener);
	}

	@Override
	public boolean removeTrySendHsmsMessagePassThroughLogListener(SecsLogListener<? super HsmsMessagePassThroughLog> listener) {
		return this.trySendHsmsMsgPassThroughLogObserver.removeListener(listener);
	}

	@Override
	public boolean addSendedHsmsMessagePassThroughLogListener(SecsLogListener<? super HsmsMessagePassThroughLog> listener) {
		return this.sendedHsmsMsgPassThroughLogObserver.addListener(listener);
	}

	@Override
	public boolean removeSendedHsmsMessagePassThroughLogListener(SecsLogListener<? super HsmsMessagePassThroughLog> listener) {
		return this.sendedHsmsMsgPassThroughLogObserver.removeListener(listener);
	}

	@Override
	public boolean addReceiveHsmsMessagePassThroughLogListener(SecsLogListener<? super HsmsMessagePassThroughLog> listener) {
		return this.recvHsmsMsgPassThroughLogObserver.addListener(listener);
	}

	@Override
	public boolean removeReceiveHsmsMessagePassThroughLogListener(SecsLogListener<? super HsmsMessagePassThroughLog> listener) {
		return this.recvHsmsMsgPassThroughLogObserver.removeListener(listener);
	}
	
	@Override
	public boolean addHsmsChannelConnectionLogListener(SecsLogListener<? super HsmsChannelConnectionLog> listener) {
		return this.channelConnectionLogObserver.addListener(listener);
	}
	
	@Override
	public boolean removeHsmsChannelConnectionLogListener(SecsLogListener<? super HsmsChannelConnectionLog> listener) {
		return this.channelConnectionLogObserver.removeListener(listener);
	}
	
	@Override
	public boolean addHsmsSessionCommunicateStateLogListener(SecsLogListener<? super HsmsSessionCommunicateStateLog> listener) {
		return this.hsmsSessionCommunicateStateLogObserver.addListener(listener);
	}
	
	@Override
	public boolean removeHsmsSessionCommunicateStateLogListener(SecsLogListener<? super HsmsSessionCommunicateStateLog> listener) {
		return this.hsmsSessionCommunicateStateLogObserver.removeListener(listener);
	}
	
	
	public boolean offerTrySendHsmsMessagePassThrough(HsmsMessage message) {
		
		final AbstractHsmsMessagePassThroughLog log = AbstractHsmsMessagePassThroughLog.buildTrySend(message);
		log.subjectHeader(this.config.logSubjectHeader().toString());
		
		boolean f = this.trySendHsmsMsgPassThroughLogObserver.offer(log);
		
		if (message.isDataMessage()) {
			this.offerTrySendSecsMessagePassThroughLog(log);
		}
		
		this.offerToAllLog(log);
		
		return f;
	}
	
	public boolean offerSendedHsmsMessagePassThrough(HsmsMessage message) {
		
		final AbstractHsmsMessagePassThroughLog log = AbstractHsmsMessagePassThroughLog.buildSended(message);
		log.subjectHeader(this.config.logSubjectHeader().toString());
		
		boolean f = this.sendedHsmsMsgPassThroughLogObserver.offer(log);
		
		if (message.isDataMessage()) {
			this.offerSendedSecsMessagePassThroughLog(log);
		}
		
		this.offerToAllLog(log);
		
		return f;
	}
	
	public boolean offerReceiveHsmsMessagePassThrough(HsmsMessage message) {
		
		final AbstractHsmsMessagePassThroughLog log = AbstractHsmsMessagePassThroughLog.buildReceive(message);
		log.subjectHeader(this.config.logSubjectHeader().toString());
		
		boolean f = this.recvHsmsMsgPassThroughLogObserver.offer(log);
		
		if (message.isDataMessage()) {
			this.offerReceiveSecsMessagePassThroughLog(log);
		}
		
		this.offerToAllLog(log);
		
		return f;
	}
	
	
	private boolean offerHsmsChannelConnection(AbstractHsmsChannelConnectionLog log) {
		log.subjectHeader(this.config.logSubjectHeader().toString());
		boolean f = this.channelConnectionLogObserver.offer(log);
		this.offerToAllLog(log);
		return f;
	}
	
	public boolean offerHsmsChannelConnectionTryBind(SocketAddress local) {
		return this.offerHsmsChannelConnection(AbstractHsmsChannelConnectionLog.tryBind(local));
	}
	
	public boolean offerHsmsChannelConnectionBinded(SocketAddress local) {
		return this.offerHsmsChannelConnection(AbstractHsmsChannelConnectionLog.binded(local));
	}
	
	public boolean offerHsmsChannelConnectionBindClosed(SocketAddress local) {
		return this.offerHsmsChannelConnection(AbstractHsmsChannelConnectionLog.bindClosed(local));
	}
	
	public boolean offerHsmsChannelConnectionAccepted(SocketAddress local, SocketAddress remote) {
		return this.offerHsmsChannelConnection(AbstractHsmsChannelConnectionLog.accepted(local, remote));
	}
	
	public boolean offerHsmsChannelConnectionAcceptClosed(SocketAddress local, SocketAddress remote) {
		return this.offerHsmsChannelConnection(AbstractHsmsChannelConnectionLog.acceptClosed(local, remote));
	}
	
	public boolean offerHsmsChannelConnectionTryConnect(SocketAddress remote) {
		return this.offerHsmsChannelConnection(AbstractHsmsChannelConnectionLog.tryConnect(remote));
	}
	
	public boolean offerHsmsChannelConnectionConnected(SocketAddress local, SocketAddress remote) {
		return this.offerHsmsChannelConnection(AbstractHsmsChannelConnectionLog.connected(local, remote));
	}

	public boolean offerHsmsChannelConnectionConnectClosed(SocketAddress local, SocketAddress remote) {
		return this.offerHsmsChannelConnection(AbstractHsmsChannelConnectionLog.connectClosed(local, remote));
	}
	
	
	public boolean offerHsmsSessionCommunicateState(int sessionId, HsmsCommunicateState state) {
		final AbstractHsmsSessionCommunicateStateLog log = AbstractHsmsSessionCommunicateStateLog.build(sessionId, state);
		log.subjectHeader(this.config.logSubjectHeader());
		boolean f = this.hsmsSessionCommunicateStateLogObserver.offer(log);
		this.offerToAllLog(log);
		return f;
	}
	
}
