package com.shimizukenta.secs.hsmsgs.impl;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

import com.shimizukenta.secs.hsms.HsmsCommunicateState;
import com.shimizukenta.secs.hsms.HsmsSession;
import com.shimizukenta.secs.hsms.HsmsSessionCommunicateStateChangeBiListener;

public class HsmsGsHsmsCommunicateStateBiObserver implements HsmsSessionCommunicateStateChangeBiListener {

	/**
	 * Constructor.
	 * 
	 */
	public HsmsGsHsmsCommunicateStateBiObserver() {
		/* Nothing */
	}
	
	final Collection<HsmsSessionCommunicateStateChangeBiListener> biLstnrs = new CopyOnWriteArrayList<>();
	
	/**
	 * Add Listener.
	 * 
	 * @param biListener the HSMS-Communicate state change listener
	 * @return true if add success
	 */
	public boolean addListener(HsmsSessionCommunicateStateChangeBiListener biListener) {
		return this.biLstnrs.add(biListener);
	}
	
	/**
	 * Remove listener.
	 * 
	 * @param biListener the HSMS-Communicate state change listener
	 * @return true if remove success
	 */
	public boolean removeListener(HsmsSessionCommunicateStateChangeBiListener biListener) {
		return this.biLstnrs.remove(biListener);
	}
	
	@Override
	public void changed(HsmsCommunicateState state, HsmsSession session) {
		
		for (HsmsSessionCommunicateStateChangeBiListener l : this.biLstnrs) {
			l.changed(state, session);
		}
	}
	
}
