package com.shimizukenta.secs.hsms.impl;

import java.util.List;

import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessageDeselectStatus;
import com.shimizukenta.secs.hsms.HsmsMessageHeaderByteLengthIllegalArgumentException;
import com.shimizukenta.secs.hsms.HsmsMessageRejectReason;
import com.shimizukenta.secs.hsms.HsmsMessageSelectStatus;
import com.shimizukenta.secs.hsms.HsmsSession;
import com.shimizukenta.secs.impl.SecsMessageBuilder;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2BytesParseException;

public interface HsmsMessageBuilder extends SecsMessageBuilder<AbstractHsmsMessage, HsmsSession> {
	
	public AbstractHsmsMessage buildSelectRequest(HsmsSession session);
	
	public AbstractHsmsMessage buildSelectResponse(HsmsMessage primaryMsg, HsmsMessageSelectStatus status);
	
	public AbstractHsmsMessage buildDeselectRequest(HsmsSession session);
	
	public AbstractHsmsMessage buildDeselectResponse(HsmsMessage primaryMsg, HsmsMessageDeselectStatus status);
	
	public AbstractHsmsMessage buildLinktestRequest(HsmsSession session);
	
	public AbstractHsmsMessage buildLinktestResponse(HsmsMessage primaryMsg);
	
	public AbstractHsmsMessage buildRejectRequest(HsmsMessage referenceMsg, HsmsMessageRejectReason reason);
	
	public AbstractHsmsMessage buildSeparateRequest(HsmsSession session);
	
	
	/**
	 * Builder.
	 * 
	 * @param header10Bytes the header-10-bytes
	 * @return instance
	 */
	public static AbstractHsmsMessage buildMessage(byte[] header10Bytes) {
		return AbstractHsmsMessageBuilder.buildMessage(header10Bytes);
	}
	
	/**
	 * Builder.
	 * 
	 * @param header10Bytes the header-10-bytes
	 * @param body the SECS-II body
	 * @return instance
	 */
	public static AbstractHsmsMessage buildMessage(byte[] header10Bytes, Secs2 body) {
		return AbstractHsmsMessageBuilder.buildMessage(header10Bytes, body);
	}
	
	/**
	 * Build from List of bytes.
	 * 
	 * @param header the header-10-bytes
	 * @param bodies the List of bytes
	 * @return instance
	 * @throws Secs2BytesParseException the SECS-II parse failed
	 * @throws NullPointerException if value is null
	 * @throws HsmsMessageHeaderByteLengthIllegalArgumentException if header.length is NOT equals 10
	 */
	public static AbstractHsmsMessage buildFromBytes(byte[] header10Bytes, List<byte[]> bodies) throws Secs2BytesParseException {
		return AbstractHsmsMessageBuilder.buildFromBytes(header10Bytes, bodies);
	}
	
}
