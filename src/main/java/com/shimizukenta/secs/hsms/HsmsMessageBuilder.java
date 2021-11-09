package com.shimizukenta.secs.hsms;

import java.util.List;

import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2BytesParseException;

public interface HsmsMessageBuilder {
	
	public AbstractHsmsMessage buildSelectRequest();
	
	public AbstractHsmsMessage buildSelectResponse(HsmsMessage primaryMsg, HsmsMessageSelectStatus status);
	
	public AbstractHsmsMessage buildDeselectRequest();
	
	public AbstractHsmsMessage buildDeselectResponse(HsmsMessage primaryMsg, HsmsMessageDeselectStatus status);
	
	public AbstractHsmsMessage buildLinktestRequest();
	
	public AbstractHsmsMessage buildLinktestResponse(HsmsMessage primaryMsg);
	
	public AbstractHsmsMessage buildRejectRequest(HsmsMessage referenceMsg, HsmsMessageRejectReason reason);
	
	public AbstractHsmsMessage buildSeparateRequest();
	
	public AbstractHsmsMessage buildDataMessage(int strm, int func, boolean wbit);
	
	public AbstractHsmsMessage buildDataMessage(int strm, int func, boolean wbit, Secs2 body);
	
	public AbstractHsmsMessage buildDataMessage(SecsMessage primaryMsg, int strm, int func, boolean wbit);
	
	public AbstractHsmsMessage buildDataMessage(SecsMessage primaryMsg, int strm, int func, boolean wbit, Secs2 body);
	
	public AbstractHsmsMessage fromBytes(byte[] header, List<byte[]> bodies) throws Secs2BytesParseException;
	
}
