package com.shimizukenta.secs.impl;

import java.util.concurrent.Executor;

import com.shimizukenta.secs.AbstractSecsCommunicatorConfig;
import com.shimizukenta.secs.SecsLog;
import com.shimizukenta.secs.SecsLogListener;
import com.shimizukenta.secs.SecsLogObservable;
import com.shimizukenta.secs.SecsMessagePassThroughLog;
import com.shimizukenta.secs.SecsThrowableLog;

public abstract class AbstractSecsLogObserverFacade implements SecsLogObservable {
	
	private class AllLogObserver extends AbstractQueueObserver<SecsLogListener<? super SecsLog>, SecsLog> {
		
		public AllLogObserver(Executor executor) {
			super(executor);
		}
		
		@Override
		protected void notifyValueToListener(SecsLogListener<? super SecsLog> listener, SecsLog value) {
			listener.received(value);
		}
	}
	
	private class ThrowableLogObserver extends AbstractQueueObserver<SecsLogListener<? super SecsThrowableLog>, SecsThrowableLog> {
		
		public ThrowableLogObserver(Executor executor) {
			super(executor);
		}
		
		@Override
		protected void notifyValueToListener(SecsLogListener<? super SecsThrowableLog> listener, SecsThrowableLog value) {
			listener.received(value);
		}
	}
	
	private class SecsMessagePassThroghLogObserver extends AbstractQueueObserver<SecsLogListener<? super SecsMessagePassThroughLog>, SecsMessagePassThroughLog> {
		
		public SecsMessagePassThroghLogObserver(Executor executor) {
			super(executor);
		}
		
		@Override
		protected void notifyValueToListener(SecsLogListener<? super SecsMessagePassThroughLog> listener, SecsMessagePassThroughLog value) {
			listener.received(value);
		}
	}
	
	
	private final AbstractSecsCommunicatorConfig config;
	
	private final AllLogObserver allLogObserver;
	private final ThrowableLogObserver throwableLogObserver;
	private final SecsMessagePassThroghLogObserver trySendSecsMsgPassThroughLogObserver;
	private final SecsMessagePassThroghLogObserver sendedSecsMsgPassThroughLogObserver;
	private final SecsMessagePassThroghLogObserver recvSecsMsgPassThroughLogObserver;
	
	public AbstractSecsLogObserverFacade(AbstractSecsCommunicatorConfig config, Executor executor) {
		this.config = config;
		
		this.allLogObserver = new AllLogObserver(executor);
		this.throwableLogObserver = new ThrowableLogObserver(executor);
		this.trySendSecsMsgPassThroughLogObserver = new SecsMessagePassThroghLogObserver(executor);
		this.sendedSecsMsgPassThroughLogObserver = new SecsMessagePassThroghLogObserver(executor);
		this.recvSecsMsgPassThroughLogObserver = new SecsMessagePassThroghLogObserver(executor);
	}
	
	@Override
	public boolean addSecsLogListener(SecsLogListener<? super SecsLog> listener) {
		return this.allLogObserver.addListener(listener);
	}

	@Override
	public boolean removeSecsLogListener(SecsLogListener<? super SecsLog> listener) {
		return this.allLogObserver.removeListener(listener);
	}

	@Override
	public boolean addSecsThrowableLogListener(SecsLogListener<? super SecsThrowableLog> listener) {
		return this.throwableLogObserver.addListener(listener);
	}

	@Override
	public boolean removeSecsThrowableLogListener(SecsLogListener<? super SecsThrowableLog> listener) {
		return this.throwableLogObserver.removeListener(listener);
	}

	@Override
	public boolean addTrySendSecsMessagePassThroughLogListener(SecsLogListener<? super SecsMessagePassThroughLog> listener) {
		return this.trySendSecsMsgPassThroughLogObserver.addListener(listener);
	}

	@Override
	public boolean removeTrySendSecsMessagePassThroughLogListener(SecsLogListener<? super SecsMessagePassThroughLog> listener) {
		return this.trySendSecsMsgPassThroughLogObserver.removeListener(listener);
	}

	@Override
	public boolean addSendedSecsMessagePassThroughLogListener(SecsLogListener<? super SecsMessagePassThroughLog> listener) {
		return this.sendedSecsMsgPassThroughLogObserver.addListener(listener);
	}

	@Override
	public boolean removeSendedSecsMessagePassThroughLogListener(SecsLogListener<? super SecsMessagePassThroughLog> listener) {
		return this.sendedSecsMsgPassThroughLogObserver.removeListener(listener);
	}

	@Override
	public boolean addReceiveSecsMessagePassThroughLogListener(SecsLogListener<? super SecsMessagePassThroughLog> listener) {
		return this.recvSecsMsgPassThroughLogObserver.addListener(listener);
	}

	@Override
	public boolean removeReceiveSecsMessagePassThroughLogListener(SecsLogListener<? super SecsMessagePassThroughLog> listener) {
		return this.recvSecsMsgPassThroughLogObserver.removeListener(listener);
	}
	
	protected boolean offerToAllLog(SecsLog log) {
		return this.allLogObserver.offer(log);
	}
	
	public boolean offerThrowable(Throwable t) {
		
		final AbstractSecsThrowableLog log = new AbstractSecsThrowableLog(t) {

			private static final long serialVersionUID = -2686364462032700654L;
		};
		
		log.subjectHeader(config.logSubjectHeader().toString());
		
		boolean f = this.throwableLogObserver.offer(log);
		
		this.allLogObserver.offer(log);
		
		return f;
	}
	
	protected boolean offerTrySendSecsMessagePassThroughLog(SecsMessagePassThroughLog log) {
		return this.trySendSecsMsgPassThroughLogObserver.offer(log);
	}
	
	protected boolean offerSendedSecsMessagePassThroughLog(SecsMessagePassThroughLog log) {
		return this.sendedSecsMsgPassThroughLogObserver.offer(log);
	}
	
	protected boolean offerReceiveSecsMessagePassThroughLog(SecsMessagePassThroughLog log) {
		return this.recvSecsMsgPassThroughLogObserver.offer(log);
	}
	
}
