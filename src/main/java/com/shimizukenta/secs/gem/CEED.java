package com.shimizukenta.secs.gem;

import com.shimizukenta.secs.secs2.Secs2;

public enum CEED {
	
	ENABLE(Secs2.bool(true)),
	DISABLE(Secs2.bool(false)),
	
	;
	
	private final Secs2 v;
	private CEED(Secs2 v) {
		this.v = v;
	}
	
	public Secs2 secs2() {
		return v;
	}
}
