package com.shimizukenta.secs.hsms.impl;

import java.util.List;

import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessageDeselectStatus;
import com.shimizukenta.secs.hsms.HsmsMessageRejectReason;
import com.shimizukenta.secs.hsms.HsmsMessageSelectStatus;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2BytesParseException;

public interface HsmsMessageBuilder {
	
	public AbstractHsmsMessage buildSelectRequest(AbstractHsmsSession session);
	
	public AbstractHsmsMessage buildSelectResponse(HsmsMessage primaryMsg, HsmsMessageSelectStatus status);
	
	public AbstractHsmsMessage buildDeselectRequest(AbstractHsmsSession session);
	
	public AbstractHsmsMessage buildDeselectResponse(HsmsMessage primaryMsg, HsmsMessageDeselectStatus status);
	
	public AbstractHsmsMessage buildLinktestRequest(AbstractHsmsSession session);
	
	public AbstractHsmsMessage buildLinktestResponse(HsmsMessage primaryMsg);
	
	public AbstractHsmsMessage buildRejectRequest(HsmsMessage referenceMsg, HsmsMessageRejectReason reason);
	
	public AbstractHsmsMessage buildSeparateRequest(AbstractHsmsSession session);
	
	public AbstractHsmsMessage buildDataMessage(AbstractHsmsSession session, int strm, int func, boolean wbit);
	
	public AbstractHsmsMessage buildDataMessage(AbstractHsmsSession session, int strm, int func, boolean wbit, Secs2 body);
	
	public AbstractHsmsMessage buildDataMessage(SecsMessage primaryMsg, int strm, int func, boolean wbit);
	
	public AbstractHsmsMessage buildDataMessage(SecsMessage primaryMsg, int strm, int func, boolean wbit, Secs2 body);
	
	
	public static AbstractHsmsMessage build(byte[] header10Bytes) {
		return AbstractHsmsMessageBuilder.build(header10Bytes, Secs2.empty());
	}
	
	public static AbstractHsmsMessage build(byte[] header10Bytes, Secs2 body) {
		return AbstractHsmsMessageBuilder.build(header10Bytes, body);
	}
	
	public static AbstractHsmsMessage fromBytes(byte[] header, List<byte[]> bodies) throws Secs2BytesParseException {
		return AbstractHsmsMessageBuilder.fromBytes(header, bodies);
	}
	
}
