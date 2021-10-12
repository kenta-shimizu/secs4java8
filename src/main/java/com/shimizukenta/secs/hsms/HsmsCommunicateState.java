package com.shimizukenta.secs.hsms;

public enum HsmsCommunicateState {
	
	NOT_CONNECTED(false),
	NOT_SELECTED(false),
	SELECTED(true),
	;
	
	private boolean communicatable;
	
	private HsmsCommunicateState(boolean f) {
		this.communicatable = f;
	}
	
	public boolean communicatable() {
		return communicatable;
	}
	
}
