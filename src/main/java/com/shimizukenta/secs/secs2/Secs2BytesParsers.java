package com.shimizukenta.secs.secs2;

import java.util.List;

public final class Secs2BytesParsers {
	
	private Secs2BytesParsers() {
		/* Nothing */
	}
	
	private static final class SingletonHolder {
		private static final Secs2BytesParser inst = new AbstractSecs2BytesParser() {};
	}
	
	public static Secs2BytesParser getInstance() {
		return SingletonHolder.inst;
	}
	
	public static Secs2 parse(List<byte[]> bss) throws Secs2BytesParseException {
		return getInstance().parse(bss);
	}
	
}
