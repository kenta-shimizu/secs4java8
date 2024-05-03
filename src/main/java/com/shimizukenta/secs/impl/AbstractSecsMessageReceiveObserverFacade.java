package com.shimizukenta.secs.impl;

import java.util.concurrent.Executor;

import com.shimizukenta.secs.SecsGemAccessor;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsMessageReceiveBiListener;
import com.shimizukenta.secs.SecsMessageReceiveListener;
import com.shimizukenta.secs.SecsMessageReceiveObservable;

public abstract class AbstractSecsMessageReceiveObserverFacade implements SecsMessageReceiveObservable {
	
	private class SecsMsgRecvQueueBiObserver extends AbstractQueueBiObserver<SecsGemAccessor, SecsMessageReceiveListener, SecsMessageReceiveBiListener, SecsMessage> {

		public SecsMsgRecvQueueBiObserver(Executor executor, SecsGemAccessor accessor) {
			super(executor, accessor);
		}

		@Override
		protected void notifyValueToListener(SecsMessageReceiveListener listener, SecsMessage value) {
			listener.received(value);
		}

		@Override
		protected void notifyValueToBiListener(
				SecsMessageReceiveBiListener biListener,
				SecsMessage value,
				SecsGemAccessor accessor) {
			biListener.received(value, accessor);
		}
		
	}
	
	private final SecsMsgRecvQueueBiObserver secsMsgRecvObserver;
	
	public AbstractSecsMessageReceiveObserverFacade(Executor executor, SecsGemAccessor accessor) {
		this.secsMsgRecvObserver = new SecsMsgRecvQueueBiObserver(executor, accessor);
	}

	@Override
	public boolean addSecsMessageReceiveListener(SecsMessageReceiveListener listener) {
		return this.secsMsgRecvObserver.addListener(listener);
	}

	@Override
	public boolean removeSecsMessageReceiveListener(SecsMessageReceiveListener listener) {
		return this.secsMsgRecvObserver.removeListener(listener);
	}

	@Override
	public boolean addSecsMessageReceiveBiListener(SecsMessageReceiveBiListener biListener) {
		return this.secsMsgRecvObserver.addBiListener(biListener);
	}

	@Override
	public boolean removeSecsMessageReceiveBiListener(SecsMessageReceiveBiListener biListener) {
		return this.secsMsgRecvObserver.removeBiListener(biListener);
	}
	
	public void putSecsMessage(SecsMessage message) throws InterruptedException {
		this.secsMsgRecvObserver.put(message);
	}

}
