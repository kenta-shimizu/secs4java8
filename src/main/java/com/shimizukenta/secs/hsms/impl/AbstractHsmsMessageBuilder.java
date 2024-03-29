package com.shimizukenta.secs.hsms.impl;

import java.util.List;

import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessageDeselectStatus;
import com.shimizukenta.secs.hsms.HsmsMessageRejectReason;
import com.shimizukenta.secs.hsms.HsmsMessageSelectStatus;
import com.shimizukenta.secs.hsms.HsmsMessageType;
import com.shimizukenta.secs.hsms.HsmsSession;
import com.shimizukenta.secs.impl.AbstractSecsMessageBuilder;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2BytesParseException;
import com.shimizukenta.secs.secs2.impl.Secs2BytesParsers;

public abstract class AbstractHsmsMessageBuilder extends AbstractSecsMessageBuilder<AbstractHsmsMessage, HsmsSession> implements HsmsMessageBuilder {
	
	public AbstractHsmsMessageBuilder() {
		super();
	}
	
	@Override
	protected byte[] device2Bytes(HsmsSession communicator) {
		int n = communicator.sessionId();
		return new byte[] {
				(byte)(n >> 8),
				(byte)n
		};
	}
	
	@Override
	public AbstractHsmsMessage buildSelectResponse(HsmsMessage primaryMsg, HsmsMessageSelectStatus status) {
		
		byte[] bs = primaryMsg.header10Bytes();
		
		byte[] header10Bytes = new byte[] {
				bs[0],
				bs[1],
				(byte)0x0,
				status.statusCode(),
				HsmsMessageType.SELECT_RSP.pType(),
				HsmsMessageType.SELECT_RSP.sType(),
				bs[6],
				bs[7],
				bs[8],
				bs[9]
		};
		
		return buildMessage(header10Bytes);
	}
	
	@Override
	public AbstractHsmsMessage buildDeselectResponse(HsmsMessage primaryMsg, HsmsMessageDeselectStatus status) {
		
		byte[] bs = primaryMsg.header10Bytes();
		
		byte[] header10Bytes = new byte[] {
				bs[0],
				bs[1],
				(byte)0x0,
				status.statusCode(),
				HsmsMessageType.DESELECT_RSP.pType(),
				HsmsMessageType.DESELECT_RSP.sType(),
				bs[6],
				bs[7],
				bs[8],
				bs[9]
		};
		
		return buildMessage(header10Bytes);
	}
	
	@Override
	public AbstractHsmsMessage buildLinktestResponse(HsmsMessage primaryMsg) {
		
		byte[] bs = primaryMsg.header10Bytes();
		
		byte[] header10Bytes = new byte[] {
				bs[0],
				bs[1],
				(byte)0x0,
				(byte)0x0,
				HsmsMessageType.LINKTEST_RSP.pType(),
				HsmsMessageType.LINKTEST_RSP.sType(),
				bs[6],
				bs[7],
				bs[8],
				bs[9]
		};
		
		return buildMessage(header10Bytes);
	}
	
	@Override
	public AbstractHsmsMessage buildRejectRequest(HsmsMessage referenceMsg, HsmsMessageRejectReason reason) {
		
		byte[] bs = referenceMsg.header10Bytes();
		
		byte b2;
		switch ( reason ) {
		case NOT_SUPPORT_TYPE_P: {
			b2 = bs[4];
			break;
		}
		default: {
			b2 = bs[5];
		}
		}
		
		byte[] header10Bytes = new byte[] {
				bs[0],
				bs[1],
				b2,
				reason.reasonCode(),
				HsmsMessageType.REJECT_REQ.pType(),
				HsmsMessageType.REJECT_REQ.sType(),
				bs[6],
				bs[7],
				bs[8],
				bs[9]
		};
		
		return buildMessage(header10Bytes);
	}
	
	@Override
	public AbstractHsmsMessage buildDataMessage(HsmsSession session, int strm, int func, boolean wbit, Secs2 body) {
		
		byte[] sessionId2Bytes = this.device2Bytes(session);
		byte[] sysbytes = this.system4Bytes(session);
		
		byte[] header10Bytes = new byte[] {
				sessionId2Bytes[0],
				sessionId2Bytes[1],
				(byte)(strm & 0x7F),
				(byte)func,
				HsmsMessageType.DATA.pType(),
				HsmsMessageType.DATA.sType(),
				sysbytes[0],
				sysbytes[1],
				sysbytes[2],
				sysbytes[3]
		};
		
		if ( wbit ) {
			header10Bytes[2] |= (byte)0x80;
		}
		
		return buildMessage(header10Bytes, body);
	}
	
	@Override
	public AbstractHsmsMessage buildDataMessage(HsmsSession session, SecsMessage primaryMsg, int strm, int func, boolean wbit, Secs2 body) {
		
		byte[] sessionId2Bytes = this.device2Bytes(session);
		byte[] bs = primaryMsg.header10Bytes();
		
		byte[] header10Bytes = new byte[] {
				sessionId2Bytes[0],
				sessionId2Bytes[1],
				(byte)(strm & 0x7F),
				(byte)func,
				HsmsMessageType.DATA.pType(),
				HsmsMessageType.DATA.sType(),
				bs[6],
				bs[7],
				bs[8],
				bs[9]
		};
		
		if ( wbit ) {
			header10Bytes[2] |= (byte)0x80;
		}
		
		return buildMessage(header10Bytes, body);
	}
	
	
	/**
	 * Builder.
	 * 
	 * @param header10Bytes the header-10-bytes.
	 * @return instance
	 */
	public static AbstractHsmsMessage buildMessage(byte[] header10Bytes) {
		return buildMessage(header10Bytes, Secs2.empty());
	}
	
	/**
	 * Builder.
	 * 
	 * @param header10Bytes the header-10-bytes
	 * @param body the SECS-II body
	 * @return instance
	 */
	public static AbstractHsmsMessage buildMessage(byte[] header10Bytes, Secs2 body) {
		
		return new AbstractHsmsMessage(header10Bytes, body) {
			
			private static final long serialVersionUID = -495106331741472023L;
		};
	}
	
	/**
	 * Build from List of bytes.
	 * 
	 * @param header the header-10-bytes
	 * @param bodies the List of bytes
	 * @return instance
	 * @throws Secs2BytesParseException the SECS-II parse failed
	 */
	public static AbstractHsmsMessage buildFromBytes(byte[] header, List<byte[]> bodies) throws Secs2BytesParseException {
		return buildMessage(header, Secs2BytesParsers.parse(bodies));
	}
	
}
