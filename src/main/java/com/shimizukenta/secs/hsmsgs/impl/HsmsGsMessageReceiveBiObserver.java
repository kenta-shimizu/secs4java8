package com.shimizukenta.secs.hsmsgs.impl;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

import com.shimizukenta.secs.SecsMessageReceiveBiListener;
import com.shimizukenta.secs.SecsMessageReceiveListener;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessageReceiveListener;
import com.shimizukenta.secs.hsms.HsmsSession;
import com.shimizukenta.secs.hsms.HsmsSessionMessageReceiveBiListener;

public class HsmsGsMessageReceiveBiObserver implements HsmsSessionMessageReceiveBiListener {

	public HsmsGsMessageReceiveBiObserver() {
		/* Nothing */
	}
	
	private final Collection<SecsMessageReceiveListener> secsListeners = new CopyOnWriteArrayList<>();
	private final Collection<SecsMessageReceiveBiListener> secsBiListeners = new CopyOnWriteArrayList<>();
	private final Collection<HsmsMessageReceiveListener> hsmsListeners = new CopyOnWriteArrayList<>();
	private final Collection<HsmsSessionMessageReceiveBiListener> hsmsBiListeners = new CopyOnWriteArrayList<>();
	
	public boolean addListener(SecsMessageReceiveListener listener) {
		return this.secsListeners.add(listener);
	}
	
	public boolean removeListener(SecsMessageReceiveListener listener) {
		return this.secsListeners.remove(listener);
	}
	
	public boolean addBiListener(SecsMessageReceiveBiListener biListener) {
		return this.secsBiListeners.add(biListener);
	}
	
	public boolean removeBiListener(SecsMessageReceiveBiListener biListener) {
		return this.secsBiListeners.remove(biListener);
	}
	
	public boolean addListener(HsmsMessageReceiveListener listener) {
		return this.hsmsListeners.add(listener);
	}
	
	public boolean removeListener(HsmsMessageReceiveListener listener) {
		return this.hsmsListeners.remove(listener);
	}
	
	public boolean addBiListener(HsmsSessionMessageReceiveBiListener biListener) {
		return this.hsmsBiListeners.add(biListener);
	}
	
	public boolean removeBiListener(HsmsSessionMessageReceiveBiListener biListener) {
		return this.hsmsBiListeners.remove(biListener);
	}
	
	@Override
	public void received(HsmsMessage message, HsmsSession hsmsSession) {
		
		for ( SecsMessageReceiveListener l : this.secsListeners ) {
			l.received(message);
		}
		
		for ( SecsMessageReceiveBiListener l : this.secsBiListeners ) {
			l.received(message, hsmsSession);
		}
		
		for ( HsmsMessageReceiveListener l : this.hsmsListeners ) {
			l.received(message);
		}
		
		for ( HsmsSessionMessageReceiveBiListener l : this.hsmsBiListeners ) {
			l.received(message, hsmsSession);
		}
	}
	
}
