package com.shimizukenta.secs.secs2.impl;

import java.util.List;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2BytesParseException;

public interface Secs2BytesParser {
	
	public Secs2 parse(List<byte[]> bss) throws Secs2BytesParseException;
	
	public static Secs2BytesParser getInstance() {
		return Secs2BytesParsers.getInstance();
	}
	
}
