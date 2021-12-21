package com.shimizukenta.secs.secs2;

import java.util.List;

public interface Secs2BytesParser {
	
	public Secs2 parse(List<byte[]> bss) throws Secs2BytesParseException;
	
	public static Secs2BytesParser getInstance() {
		return Secs2BytesParsers.getInstance();
	}
	
}
