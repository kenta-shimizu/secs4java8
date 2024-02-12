package com.shimizukenta.secs.hsmsgs.impl;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

import com.shimizukenta.secs.GemAccessor;
import com.shimizukenta.secs.SecsCommunicatableStateChangeBiListener;

public class HsmsGsSecsCommunicatableBiObserver implements SecsCommunicatableStateChangeBiListener {
	
	/**
	 * Constructor.
	 * 
	 */
	public HsmsGsSecsCommunicatableBiObserver() {
		/* Nothing */
	}
	
	private Collection<SecsCommunicatableStateChangeBiListener> biListener = new CopyOnWriteArrayList<>();
	
	/**
	 * Add listener.
	 * 
	 * @param biListener the state change listener
	 * @return true if add success
	 */
	public boolean addListener(SecsCommunicatableStateChangeBiListener biListener) {
		return this.biListener.add(biListener);
	}
	
	/**
	 * Remove listener.
	 * 
	 * @param biListener the state change listener.
	 * @return true if remove success
	 */
	public boolean removeListener(SecsCommunicatableStateChangeBiListener biListener) {
		return this.biListener.remove(biListener);
	}

	@Override
	public void changed(boolean communicatable, GemAccessor communicator) {
		
		for ( SecsCommunicatableStateChangeBiListener l : this.biListener ) {
			l.changed(communicatable, communicator);
		}
	}
	
}
