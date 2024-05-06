package com.shimizukenta.secs.hsms.impl;

import java.util.concurrent.Executor;

import com.shimizukenta.secs.hsms.HsmsGemAccessor;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessageReceiveBiListener;
import com.shimizukenta.secs.hsms.HsmsMessageReceiveListener;
import com.shimizukenta.secs.hsms.HsmsMessageReceiveObservable;
import com.shimizukenta.secs.impl.AbstractQueueBiObserver;
import com.shimizukenta.secs.impl.AbstractSecsMessageReceiveObserverFacade;

public abstract class AbstractHsmsMessageReceiveObserverFacade extends AbstractSecsMessageReceiveObserverFacade
		implements HsmsMessageReceiveObservable {
	
	private class HsmsMsgRecvQueueBiObserver extends AbstractQueueBiObserver<HsmsGemAccessor, HsmsMessageReceiveListener, HsmsMessageReceiveBiListener, HsmsMessage> {

		public HsmsMsgRecvQueueBiObserver(Executor executor, HsmsGemAccessor accessor) {
			super(executor, accessor);
		}
		
		@Override
		protected void notifyValueToListener(HsmsMessageReceiveListener listener, HsmsMessage value) {
			listener.received(value);
		}
		
		@Override
		protected void notifyValueToBiListener(
				HsmsMessageReceiveBiListener biListener,
				HsmsMessage value,
				HsmsGemAccessor accessor) {
			
			biListener.received(value, accessor);
		}
	}
	
	private final HsmsMsgRecvQueueBiObserver hsmsMsgRecvObserver;
	
	public AbstractHsmsMessageReceiveObserverFacade(Executor executor, HsmsGemAccessor accessor) {
		super(executor, accessor);
		this.hsmsMsgRecvObserver = new HsmsMsgRecvQueueBiObserver(executor, accessor);
	}

	@Override
	public boolean addHsmsMessageReceiveListener(HsmsMessageReceiveListener listener) {
		return this.hsmsMsgRecvObserver.addListener(listener);
	}

	@Override
	public boolean removeHsmsMessageReceiveListener(HsmsMessageReceiveListener listener) {
		return this.hsmsMsgRecvObserver.removeListener(listener);
	}

	@Override
	public boolean addHsmsMessageReceiveBiListener(HsmsMessageReceiveBiListener biListener) {
		return this.hsmsMsgRecvObserver.addBiListener(biListener);
	}

	@Override
	public boolean removeHsmsMessageReceiveBiListener(HsmsMessageReceiveBiListener biListener) {
		return this.hsmsMsgRecvObserver.removeBiListener(biListener);
	}
	
	public void putHsmsMessage(HsmsMessage message) throws InterruptedException {
		if (message.isDataMessage()) {
			this.putSecsMessage(message);
		}
		this.hsmsMsgRecvObserver.put(message);
	}
	
}
