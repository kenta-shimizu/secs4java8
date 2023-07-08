package com.shimizukenta.secs.secs2.impl;

import com.shimizukenta.secs.secs2.Secs2Builder;

public final class Secs2Builders {

	private Secs2Builders() {
		/* Nothing */
	}
	
	private static class SingletonHolder {
		private static final Secs2Builder inst = new AbstractSecs2Builder() {};
	}
	
	public static Secs2Builder getInstance() {
		return SingletonHolder.inst;
	}
	
}
