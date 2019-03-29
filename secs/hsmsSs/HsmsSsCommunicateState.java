package secs.hsmsSs;

public enum HsmsSsCommunicateState {
	
	NOT_CONNECTED(false),
	CONNECTED(false),
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
