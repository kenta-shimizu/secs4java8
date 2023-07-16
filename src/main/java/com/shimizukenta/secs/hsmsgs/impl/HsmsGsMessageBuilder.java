package com.shimizukenta.secs.hsmsgs.impl;

import java.util.List;

import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.impl.AbstractHsmsMessage;
import com.shimizukenta.secs.hsms.impl.HsmsMessageBuilder;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2BytesParseException;

public interface HsmsGsMessageBuilder extends HsmsMessageBuilder {
	
	public static AbstractHsmsMessage build(byte[] header) {
		return AbstractHsmsGsMessageBuilder.build(header);
	}
	
	public static AbstractHsmsMessage build(byte[] header, Secs2 body) {
		return AbstractHsmsGsMessageBuilder.build(header, body);
	}
	
	public static AbstractHsmsMessage fromMessage(HsmsMessage message) {
		return AbstractHsmsGsMessageBuilder.fromMessage(message);
	}
	
	public static AbstractHsmsMessage fromBytes(byte[] header, List<byte[]> bodies) throws Secs2BytesParseException {
		return AbstractHsmsGsMessageBuilder.fromBytes(header,bodies);
	}
	
}
