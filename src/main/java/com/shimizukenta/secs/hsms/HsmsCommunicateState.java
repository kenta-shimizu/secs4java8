package com.shimizukenta.secs.hsms;

/**
 * HSMS Communicate State.
 * 
 * @author kenta-shimizu
 *
 */
public enum HsmsCommunicateState {
	
	/**
	 * NOT_CONNECTED.
	 * 
	 */
	NOT_CONNECTED(false),
	
	/**
	 * NOT_SELECTED.
	 * 
	 */
	NOT_SELECTED(false),
	
	/**
	 * SELECTED.
	 * 
	 */
	SELECTED(true),
	;
	
	private boolean communicatable;
	
	private HsmsCommunicateState(boolean f) {
		this.communicatable = f;
	}
	
	/**
	 * Returns communicatable.
	 * 
	 * @return communicatable
	 */
	public boolean communicatable() {
		return communicatable;
	}
	
}
