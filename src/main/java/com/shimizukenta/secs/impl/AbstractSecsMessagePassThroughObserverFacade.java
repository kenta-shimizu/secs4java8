package com.shimizukenta.secs.impl;

import java.util.concurrent.Executor;

import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsMessagePassThroughListener;
import com.shimizukenta.secs.SecsMessagePassThroughObservable;

public abstract class AbstractSecsMessagePassThroughObserverFacade implements SecsMessagePassThroughObservable {
	
	private class SecsMessagePassThroughObserver extends AbstractQueueObserver<SecsMessagePassThroughListener<? super SecsMessage>, SecsMessage> {
		
		public SecsMessagePassThroughObserver(Executor executor) {
			super(executor);
		}
		
		@Override
		protected void notifyValueToListener(SecsMessagePassThroughListener<? super SecsMessage> listener, SecsMessage value) {
			listener.passThrough(value);
		}
	}
	
	
	private final SecsMessagePassThroughObserver trySendSecsMsg;
	private final SecsMessagePassThroughObserver sendedSecsMsg;
	private final SecsMessagePassThroughObserver recvSecsMsg;
	
	public AbstractSecsMessagePassThroughObserverFacade(Executor executor) {
		this.trySendSecsMsg = new SecsMessagePassThroughObserver(executor);
		this.sendedSecsMsg = new SecsMessagePassThroughObserver(executor);
		this.recvSecsMsg = new SecsMessagePassThroughObserver(executor);
	}

	@Override
	public boolean addTrySendSecsMessagePassThroughListener(SecsMessagePassThroughListener<? super SecsMessage> listener) {
		return this.trySendSecsMsg.addListener(listener);
	}

	@Override
	public boolean removeTrySendSecsMessagePassThroughListener(SecsMessagePassThroughListener<? super SecsMessage> listener) {
		return this.trySendSecsMsg.removeListener(listener);
	}

	@Override
	public boolean addSendedSecsMessagePassThroughListener(SecsMessagePassThroughListener<? super SecsMessage> listener) {
		return this.sendedSecsMsg.addListener(listener);
	}

	@Override
	public boolean removeSendedSecsMessagePassThroughListener(SecsMessagePassThroughListener<? super SecsMessage> listener) {
		return this.sendedSecsMsg.removeListener(listener);
	}

	@Override
	public boolean addReceiveSecsMessagePassThroughListener(SecsMessagePassThroughListener<? super SecsMessage> listener) {
		return this.recvSecsMsg.addListener(listener);
	}

	@Override
	public boolean removeReceiveSecsMessagePassThroughListener(SecsMessagePassThroughListener<? super SecsMessage> listener) {
		return this.recvSecsMsg.removeListener(listener);
	}
	
	protected void putToTrySendSecsMessage(SecsMessage message) throws InterruptedException {
		this.trySendSecsMsg.put(message);
	}
	
	protected void putToSendedSecsMessage(SecsMessage message) throws InterruptedException {
		this.sendedSecsMsg.put(message);
	}
	
	protected void putToReceiveSecsMessage(SecsMessage message) throws InterruptedException {
		this.recvSecsMsg.put(message);
	}

}
