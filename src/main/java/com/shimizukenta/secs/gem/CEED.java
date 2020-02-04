package com.shimizukenta.secs.gem;

import com.shimizukenta.secs.secs2.AbstractSecs2;

public enum CEED {
	
	ENABLE(AbstractSecs2.bool(true)),
	DISABLE(AbstractSecs2.bool(false)),
	
	;
	
	private final AbstractSecs2 v;
	private CEED(AbstractSecs2 v) {
		this.v = v;
	}
	
	public AbstractSecs2 secs2() {
		return v;
	}
}
