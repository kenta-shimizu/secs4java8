package com.shimizukenta.secs.hsms;

import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.secs2.Secs2;

public interface HsmsMessageBuilder {
	
	public AbstractHsmsControlMessage buildSelectRequest(AbstractHsmsSession session);
	
	public AbstractHsmsControlMessage buildSelectResponse(HsmsMessage primaryMsg, HsmsMessageSelectStatus status);
	
	public AbstractHsmsControlMessage buildDeselectRequest(AbstractHsmsSession session);
	
	public AbstractHsmsControlMessage buildDeselectResponse(HsmsMessage primaryMsg, HsmsMessageDeselectStatus status);
	
	public AbstractHsmsControlMessage buildLinktestRequest(AbstractHsmsSession session);
	
	public AbstractHsmsControlMessage buildLinktestResponse(HsmsMessage primaryMsg);
	
	public AbstractHsmsControlMessage buildRejectRequest(HsmsMessage referenceMsg, HsmsMessageRejectReason reason);
	
	public AbstractHsmsControlMessage buildSeparateRequest(AbstractHsmsSession session);
	
	public AbstractHsmsDataMessage buildDataMessage(AbstractHsmsSession session, int strm, int func, boolean wbit);
	
	public AbstractHsmsDataMessage buildDataMessage(AbstractHsmsSession session, int strm, int func, boolean wbit, Secs2 body);
	
	public AbstractHsmsDataMessage buildDataMessage(SecsMessage primaryMsg, int strm, int func, boolean wbit);
	
	public AbstractHsmsDataMessage buildDataMessage(SecsMessage primaryMsg, int strm, int func, boolean wbit, Secs2 body);
	
}
