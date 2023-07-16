package com.shimizukenta.secs.hsmsss.impl;

import java.util.List;

import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.impl.AbstractHsmsMessage;
import com.shimizukenta.secs.hsms.impl.HsmsMessageBuilder;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2BytesParseException;

public interface HsmsSsMessageBuilder extends HsmsMessageBuilder {
	
	public static AbstractHsmsMessage build(byte[] header) {
		return AbstractHsmsSsMessageBuilder.build(header);
	}
	
	public static AbstractHsmsMessage build(byte[] header, Secs2 body) {
		return AbstractHsmsSsMessageBuilder.build(header, body);
	}
	
	public static AbstractHsmsMessage fromBytes(byte[] header, List<byte[]> bodies) throws Secs2BytesParseException {
		return AbstractHsmsSsMessageBuilder.fromBytes(header, bodies);
	}
	
	public static AbstractHsmsMessage fromMessage(HsmsMessage message) {
		return AbstractHsmsSsMessageBuilder.fromMessage(message);
	}
	
}
