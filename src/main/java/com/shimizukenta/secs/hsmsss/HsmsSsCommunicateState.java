package com.shimizukenta.secs.hsmsss;

public enum HsmsSsCommunicateState {
	
	NOT_CONNECTED(false),
	NOT_SELECTED(false),
	SELECTED(true),
	;
	
	private boolean communicatable;
	
	private HsmsSsCommunicateState(boolean f) {
		this.communicatable = f;
	}
	
	public boolean communicatable() {
		return communicatable;
	}
	
}
