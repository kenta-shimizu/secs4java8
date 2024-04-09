package com.shimizukenta.secs.hsms.impl;

import java.util.concurrent.Executor;

import com.shimizukenta.secs.SecsMessagePassThroughListener;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessagePassThroughObservable;
import com.shimizukenta.secs.impl.AbstractQueueObserver;
import com.shimizukenta.secs.impl.AbstractSecsMessagePassThroughObserverFacade;

public class AbstractHsmsMessagePassThroughObserverFacade extends AbstractSecsMessagePassThroughObserverFacade implements HsmsMessagePassThroughObservable {
	
	private class HsmsMessagePassThroughObserver extends AbstractQueueObserver<SecsMessagePassThroughListener<? super HsmsMessage>, HsmsMessage> {
		
		public HsmsMessagePassThroughObserver(Executor executor) {
			super(executor);
		}
		
		@Override
		protected void notifyValueToListener(SecsMessagePassThroughListener<? super HsmsMessage> listener, HsmsMessage value) {
			listener.passThrough(value);
		}
	}
	
	final HsmsMessagePassThroughObserver trySendHsmsMsg;
	final HsmsMessagePassThroughObserver sendedHsmsMsg;
	final HsmsMessagePassThroughObserver recvHsmsMsg;
	
	public AbstractHsmsMessagePassThroughObserverFacade(Executor executor) {
		super(executor);
		
		this.trySendHsmsMsg = new HsmsMessagePassThroughObserver(executor);
		this.sendedHsmsMsg = new HsmsMessagePassThroughObserver(executor);
		this.recvHsmsMsg = new HsmsMessagePassThroughObserver(executor);
	}

	@Override
	public boolean addTrySendHsmsMessagePassThroughListener(SecsMessagePassThroughListener<? super HsmsMessage> listener) {
		return this.trySendHsmsMsg.addListener(listener);
	}

	@Override
	public boolean removeTrySendHsmsMessagePassThroughListener(SecsMessagePassThroughListener<? super HsmsMessage> listener) {
		return this.trySendHsmsMsg.removeListener(listener);
	}

	@Override
	public boolean addSendedHsmsMessagePassThroughListener(SecsMessagePassThroughListener<? super HsmsMessage> listener) {
		return this.sendedHsmsMsg.addListener(listener);
	}

	@Override
	public boolean removeSendedHsmsMessagePassThroughListener(SecsMessagePassThroughListener<? super HsmsMessage> listener) {
		return this.sendedHsmsMsg.removeListener(listener);
	}

	@Override
	public boolean addReceiveHsmsMessagePassThroughListener(SecsMessagePassThroughListener<? super HsmsMessage> listener) {
		return this.recvHsmsMsg.addListener(listener);
	}

	@Override
	public boolean removeReceiveHsmsMessagePassThroughListener(SecsMessagePassThroughListener<? super HsmsMessage> listener) {
		return this.recvHsmsMsg.removeListener(listener);
	}
	
	public void putToTrySendHsmsMessage(HsmsMessage message) throws InterruptedException {
		if (message.isDataMessage()) {
			this.putToTrySendSecsMessage(message);
		}
		this.trySendHsmsMsg.put(message);
	}
	
	public void putToSendedHsmsMessage(HsmsMessage message) throws InterruptedException {
		if (message.isDataMessage()) {
			this.putToSendedSecsMessage(message);
		}
		this.sendedHsmsMsg.put(message);
	}
	
	public void putToReceiveHsmsMessage(HsmsMessage message) throws InterruptedException {
		if (message.isDataMessage()) {
			this.putToReceiveSecsMessage(message);
		}
		this.recvHsmsMsg.put(message);
	}
	
}
