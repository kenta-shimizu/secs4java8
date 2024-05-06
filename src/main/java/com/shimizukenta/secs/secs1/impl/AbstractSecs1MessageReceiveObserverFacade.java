package com.shimizukenta.secs.secs1.impl;

import java.util.concurrent.Executor;

import com.shimizukenta.secs.impl.AbstractQueueBiObserver;
import com.shimizukenta.secs.impl.AbstractSecsMessageReceiveObserverFacade;
import com.shimizukenta.secs.secs1.Secs1GemAccessor;
import com.shimizukenta.secs.secs1.Secs1Message;
import com.shimizukenta.secs.secs1.Secs1MessageReceiveBiListener;
import com.shimizukenta.secs.secs1.Secs1MessageReceiveListener;
import com.shimizukenta.secs.secs1.Secs1MessageReceiveObservable;

public abstract class AbstractSecs1MessageReceiveObserverFacade extends AbstractSecsMessageReceiveObserverFacade
		implements Secs1MessageReceiveObservable {
	
	private class Secs1MsgRecvQueueBiObserver extends AbstractQueueBiObserver<Secs1GemAccessor, Secs1MessageReceiveListener, Secs1MessageReceiveBiListener, Secs1Message> {

		public Secs1MsgRecvQueueBiObserver(Executor executor, Secs1GemAccessor accessor) {
			super(executor, accessor);
		}

		@Override
		protected void notifyValueToListener(Secs1MessageReceiveListener listener, Secs1Message value) {
			listener.received(value);
		}

		@Override
		protected void notifyValueToBiListener(
				Secs1MessageReceiveBiListener biListener,
				Secs1Message value,
				Secs1GemAccessor accessor) {
			biListener.received(value, accessor);
		}
		
	}
	
	private final Secs1MsgRecvQueueBiObserver secs1MsgRecvObserver;
	
	public AbstractSecs1MessageReceiveObserverFacade(Executor executor, Secs1GemAccessor accessor) {
		super(executor, accessor);
		this.secs1MsgRecvObserver = new Secs1MsgRecvQueueBiObserver(executor, accessor);
	}

	@Override
	public boolean addSecs1MessageReceiveListener(Secs1MessageReceiveListener listener) {
		return this.secs1MsgRecvObserver.addListener(listener);
	}

	@Override
	public boolean removeSecs1MessageReceiveListener(Secs1MessageReceiveListener listener) {
		return this.secs1MsgRecvObserver.removeListener(listener);
	}

	@Override
	public boolean addSecs1MessageReceiveBiListener(Secs1MessageReceiveBiListener biListener) {
		return this.secs1MsgRecvObserver.addBiListener(biListener);
	}

	@Override
	public boolean removeSecs1MessageReceiveBiListener(Secs1MessageReceiveBiListener biListener) {
		return this.secs1MsgRecvObserver.removeBiListener(biListener);
	}
	
	public void putSecs1Message(Secs1Message message) throws InterruptedException {
		this.putSecsMessage(message);
		this.secs1MsgRecvObserver.put(message);
	}
	
}
