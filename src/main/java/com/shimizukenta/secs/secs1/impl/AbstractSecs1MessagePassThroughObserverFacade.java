package com.shimizukenta.secs.secs1.impl;

import java.util.concurrent.Executor;

import com.shimizukenta.secs.SecsMessagePassThroughListener;
import com.shimizukenta.secs.impl.AbstractQueueObserver;
import com.shimizukenta.secs.impl.AbstractSecsMessagePassThroughObserverFacade;
import com.shimizukenta.secs.secs1.Secs1Message;
import com.shimizukenta.secs.secs1.Secs1MessagePassThroughObservable;

public abstract class AbstractSecs1MessagePassThroughObserverFacade extends AbstractSecsMessagePassThroughObserverFacade implements Secs1MessagePassThroughObservable {
	
	private class Secs1MessagePassThroughObserver extends AbstractQueueObserver<SecsMessagePassThroughListener<? super Secs1Message>, Secs1Message> {
		
		public Secs1MessagePassThroughObserver(Executor executor) {
			super(executor);
		}
		
		@Override
		protected void notifyValueToListener(SecsMessagePassThroughListener<? super Secs1Message> listener, Secs1Message value) {
			listener.passThrough(value);
		}
	}
	
	private final Secs1MessagePassThroughObserver trySendSecs1Msg;
	private final Secs1MessagePassThroughObserver sendedSecs1Msg;
	private final Secs1MessagePassThroughObserver recvSecs1Msg;
	
	public AbstractSecs1MessagePassThroughObserverFacade(Executor executor) {
		super(executor);
		
		this.trySendSecs1Msg = new Secs1MessagePassThroughObserver(executor);
		this.sendedSecs1Msg = new Secs1MessagePassThroughObserver(executor);
		this.recvSecs1Msg = new Secs1MessagePassThroughObserver(executor);
	}

	@Override
	public boolean addTrySendSecs1MessagePassThroughListener(SecsMessagePassThroughListener<? super Secs1Message> listener) {
		return this.trySendSecs1Msg.addListener(listener);
	}

	@Override
	public boolean removeTrySendSecs1MessagePassThroughListener(SecsMessagePassThroughListener<? super Secs1Message> listener) {
		return this.trySendSecs1Msg.removeListener(listener);
	}

	@Override
	public boolean addSendedSecs1MessagePassThroughListener(SecsMessagePassThroughListener<? super Secs1Message> listener) {
		return this.sendedSecs1Msg.addListener(listener);
	}

	@Override
	public boolean removeSendedSecs1MessagePassThroughListener(SecsMessagePassThroughListener<? super Secs1Message> listener) {
		return this.sendedSecs1Msg.removeListener(listener);
	}

	@Override
	public boolean addReceiveSecs1MessagePassThroughListener(SecsMessagePassThroughListener<? super Secs1Message> listener) {
		return this.recvSecs1Msg.addListener(listener);
	}

	@Override
	public boolean removeReceiveSecs1MessagePassThroughListener(SecsMessagePassThroughListener<? super Secs1Message> listener) {
		return this.recvSecs1Msg.removeListener(listener);
	}
	
	public void putToTrySendSecs1Message(Secs1Message message) throws InterruptedException {
		this.putToTrySendSecsMessage(message);
		this.trySendSecs1Msg.put(message);
	}

	public void putToSendedSecs1Message(Secs1Message message) throws InterruptedException {
		this.putToSendedSecsMessage(message);
		this.sendedSecs1Msg.put(message);
	}
	
	public void putToReceiveSecs1Message(Secs1Message message) throws InterruptedException {
		this.putToReceiveSecsMessage(message);
		this.recvSecs1Msg.put(message);
	}
	
}
