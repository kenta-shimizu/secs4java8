package com.shimizukenta.secs.hsmsgs;

public enum HsmsGsCommunicateState {
	
	NOT_CONNECTED(false),
	NOT_SELECTED(false),
	SELECTED(true),
	;
	
	private boolean communicatable;
	
	private HsmsGsCommunicateState(boolean f) {
		this.communicatable = f;
	}
	
	public boolean communicatable() {
		return communicatable;
	}
	
}
