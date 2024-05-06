package com.shimizukenta.secs.secs1.impl;

import java.util.concurrent.Executor;

import com.shimizukenta.secs.SecsLogListener;
import com.shimizukenta.secs.impl.AbstractQueueObserver;
import com.shimizukenta.secs.impl.AbstractSecsLogObserverFacade;
import com.shimizukenta.secs.secs1.AbstractSecs1CommunicatorConfig;
import com.shimizukenta.secs.secs1.Secs1LogObservable;
import com.shimizukenta.secs.secs1.Secs1Message;
import com.shimizukenta.secs.secs1.Secs1MessageBlock;
import com.shimizukenta.secs.secs1.Secs1MessageBlockPassThroughLog;
import com.shimizukenta.secs.secs1.Secs1MessagePassThroughLog;

public abstract class AbstractSecs1LogObserverFacade extends AbstractSecsLogObserverFacade implements Secs1LogObservable {
	
	private class Secs1MessagePassThroghLogObserver extends AbstractQueueObserver<SecsLogListener<? super Secs1MessagePassThroughLog>, Secs1MessagePassThroughLog> {
		
		public Secs1MessagePassThroghLogObserver(Executor executor) {
			super(executor);
		}
		
		@Override
		protected void notifyValueToListener(SecsLogListener<? super Secs1MessagePassThroughLog> listener, Secs1MessagePassThroughLog value) {
			listener.received(value);
		}
	}
	
	private class Secs1MessageBlockPassThroghLogObserver extends AbstractQueueObserver<SecsLogListener<? super Secs1MessageBlockPassThroughLog>, Secs1MessageBlockPassThroughLog> {

		public Secs1MessageBlockPassThroghLogObserver(Executor executor) {
			super(executor);
		}

		@Override
		protected void notifyValueToListener(SecsLogListener<? super Secs1MessageBlockPassThroughLog> listener, Secs1MessageBlockPassThroughLog value) {
			listener.received(value);
		}
	}
	
	
	private final AbstractSecs1CommunicatorConfig config;
	
	private final Secs1MessagePassThroghLogObserver trySendSecs1MsgPassThroughLogObserver;
	private final Secs1MessagePassThroghLogObserver sendedSecs1MsgPassThroughLogObserver;
	private final Secs1MessagePassThroghLogObserver recvSecs1MsgPassThroughLogObserver;
	
	private final Secs1MessageBlockPassThroghLogObserver trySendSecs1MsgBlockPassThroughLogObserver;
	private final Secs1MessageBlockPassThroghLogObserver sendedSecs1MsgBlockPassThroughLogObserver;
	private final Secs1MessageBlockPassThroghLogObserver recvSecs1MsgBlockPassThroughLogObserver;
	
	public AbstractSecs1LogObserverFacade(AbstractSecs1CommunicatorConfig config, Executor executor) {
		super(config, executor);
		
		this.config = config;
		
		this.trySendSecs1MsgPassThroughLogObserver = new Secs1MessagePassThroghLogObserver(executor);
		this.sendedSecs1MsgPassThroughLogObserver = new Secs1MessagePassThroghLogObserver(executor);
		this.recvSecs1MsgPassThroughLogObserver = new Secs1MessagePassThroghLogObserver(executor);
		 
		this.trySendSecs1MsgBlockPassThroughLogObserver = new Secs1MessageBlockPassThroghLogObserver(executor);
		this.sendedSecs1MsgBlockPassThroughLogObserver = new Secs1MessageBlockPassThroghLogObserver(executor);
		this.recvSecs1MsgBlockPassThroughLogObserver = new Secs1MessageBlockPassThroghLogObserver(executor);
	}

	@Override
	public boolean addTrySendSecs1MessagePassThroughLogListener(SecsLogListener<? super Secs1MessagePassThroughLog> listener) {
		return this.trySendSecs1MsgPassThroughLogObserver.addListener(listener);
	}

	@Override
	public boolean removeTrySendSecs1MessagePassThroughLogListener(SecsLogListener<? super Secs1MessagePassThroughLog> listener) {
		return this.trySendSecs1MsgPassThroughLogObserver.removeListener(listener);
	}

	@Override
	public boolean addSendedSecs1MessagePassThroughLogListener(SecsLogListener<? super Secs1MessagePassThroughLog> listener) {
		return this.sendedSecs1MsgPassThroughLogObserver.addListener(listener);
	}

	@Override
	public boolean removeSendedSecs1MessagePassThroughLogListener(SecsLogListener<? super Secs1MessagePassThroughLog> listener) {
		return this.sendedSecs1MsgPassThroughLogObserver.removeListener(listener);
	}

	@Override
	public boolean addReceiveSecs1MessagePassThroughLogListener(SecsLogListener<? super Secs1MessagePassThroughLog> listener) {
		return this.recvSecs1MsgPassThroughLogObserver.addListener(listener);
	}

	@Override
	public boolean removeReceiveSecs1MessagePassThroughLogListener(SecsLogListener<? super Secs1MessagePassThroughLog> listener) {
		return this.recvSecs1MsgPassThroughLogObserver.removeListener(listener);
	}

	@Override
	public boolean addTrySendSecs1MessageBlockPassThroughLogListener(SecsLogListener<? super Secs1MessageBlockPassThroughLog> listener) {
		return this.trySendSecs1MsgBlockPassThroughLogObserver.addListener(listener);
	}

	@Override
	public boolean removeTrySendSecs1MessageBlockPassThroughLogListener(SecsLogListener<? super Secs1MessageBlockPassThroughLog> listener) {
		return this.trySendSecs1MsgBlockPassThroughLogObserver.removeListener(listener);
	}

	@Override
	public boolean addSendedSecs1MessageBlockPassThroughLogListener(SecsLogListener<? super Secs1MessageBlockPassThroughLog> listener) {
		return this.sendedSecs1MsgBlockPassThroughLogObserver.addListener(listener);
	}

	@Override
	public boolean removeSendedSecs1MessageBlockPassThroughLogListener(SecsLogListener<? super Secs1MessageBlockPassThroughLog> listener) {
		return this.sendedSecs1MsgBlockPassThroughLogObserver.removeListener(listener);
	}

	@Override
	public boolean addReceiveSecs1MessageBlockPassThroughLogListener(SecsLogListener<? super Secs1MessageBlockPassThroughLog> listener) {
		return this.recvSecs1MsgBlockPassThroughLogObserver.addListener(listener);
	}

	@Override
	public boolean removeReceiveSecs1MessageBlockPassThroughLogListener(SecsLogListener<? super Secs1MessageBlockPassThroughLog> listener) {
		return this.recvSecs1MsgBlockPassThroughLogObserver.removeListener(listener);
	}
	
	
	public boolean offerTrySendSecs1MessagePassThrough(Secs1Message message) {
		
		final AbstractSecs1MessagePassThroughLog log = AbstractSecs1MessagePassThroughLog.buildTrySend(message);
		log.subjectHeader(this.config.logSubjectHeader().toString());
		
		boolean f = this.trySendSecs1MsgPassThroughLogObserver.offer(log);
		this.offerTrySendSecsMessagePassThroughLog(log);
		
		this.offerToAllLog(log);
		
		return f;
	}
	
	public boolean offerSendedSecs1MessagePassThrough(Secs1Message message) {
		
		final AbstractSecs1MessagePassThroughLog log = AbstractSecs1MessagePassThroughLog.buildSended(message);
		log.subjectHeader(this.config.logSubjectHeader().toString());
		
		boolean f = this.sendedSecs1MsgPassThroughLogObserver.offer(log);
		this.offerSendedSecsMessagePassThroughLog(log);
		
		this.offerToAllLog(log);
		
		return f;
	}
	
	public boolean offerReceiveSecs1MessagePassThrough(Secs1Message message) {
		
		final AbstractSecs1MessagePassThroughLog log = AbstractSecs1MessagePassThroughLog.buildReceive(message);
		log.subjectHeader(this.config.logSubjectHeader().toString());
		
		boolean f = this.recvSecs1MsgPassThroughLogObserver.offer(log);
		this.offerReceiveSecsMessagePassThroughLog(log);
		
		this.offerToAllLog(log);
		
		return f;
	}
	
	public boolean offerTrySendSecs1MessageBlockPassThrough(Secs1MessageBlock block) {
		
		final AbstractSecs1MessageBlockPassThroughLog log = AbstractSecs1MessageBlockPassThroughLog.buildTrySend(block);
		log.subjectHeader(this.config.logSubjectHeader().toString());
		
		boolean f = this.trySendSecs1MsgBlockPassThroughLogObserver.offer(log);
		this.offerToAllLog(log);
		
		return f;
	}
	
	public boolean offerSendedSecs1MessageBlockPassThrough(Secs1MessageBlock block) {
		
		final AbstractSecs1MessageBlockPassThroughLog log = AbstractSecs1MessageBlockPassThroughLog.buildSended(block);
		log.subjectHeader(this.config.logSubjectHeader().toString());
		
		boolean f = this.sendedSecs1MsgBlockPassThroughLogObserver.offer(log);
		this.offerToAllLog(log);
		
		return f;
	}
	
	public boolean offerReceiveSecs1MessageBlockPassThrough(Secs1MessageBlock block) {
		
		final AbstractSecs1MessageBlockPassThroughLog log = AbstractSecs1MessageBlockPassThroughLog.buildReceive(block);
		log.subjectHeader(this.config.logSubjectHeader().toString());
		
		boolean f = this.recvSecs1MsgBlockPassThroughLogObserver.offer(log);
		this.offerToAllLog(log);
		
		return f;
	}
	
}
