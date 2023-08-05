package com.shimizukenta.secs.hsmsgs.impl;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessagePassThroughListener;
import com.shimizukenta.secs.hsms.HsmsSession;
import com.shimizukenta.secs.hsms.HsmsSessionMessagePassThroughBiListener;

public class HsmsGsMessagePassThroughBiObserver implements HsmsSessionMessagePassThroughBiListener {
	
	private final Collection<HsmsMessagePassThroughListener> hsmsLstnrs = new CopyOnWriteArrayList<>();
	private final Collection<HsmsSessionMessagePassThroughBiListener> hsmsBiLstnrs = new CopyOnWriteArrayList<>();
	
	public HsmsGsMessagePassThroughBiObserver() {
		/* Nothing */
	}
	
	public boolean addListener(HsmsMessagePassThroughListener listener) {
		return this.hsmsLstnrs.add(listener);
	}
	
	public boolean removeListener(HsmsMessagePassThroughListener listener) {
		return this.hsmsLstnrs.remove(listener);
	}
	
	public boolean addBiListener(HsmsSessionMessagePassThroughBiListener biListener) {
		return this.hsmsBiLstnrs.add(biListener);
	}
	
	public boolean removeBiListener(HsmsSessionMessagePassThroughBiListener biListener) {
		return this.hsmsBiLstnrs.remove(biListener);
	}
	
	@Override
	public void passThrough(HsmsMessage message, HsmsSession session) {
		
		for ( HsmsMessagePassThroughListener l : this.hsmsLstnrs ) {
			l.passThrough(message);
		}
		
		for ( HsmsSessionMessagePassThroughBiListener l : this.hsmsBiLstnrs ) {
			l.passThrough(message, session);
		}
	}

}
