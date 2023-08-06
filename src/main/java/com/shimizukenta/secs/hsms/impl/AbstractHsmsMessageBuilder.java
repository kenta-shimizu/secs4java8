package com.shimizukenta.secs.hsms.impl;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessageDeselectStatus;
import com.shimizukenta.secs.hsms.HsmsMessageRejectReason;
import com.shimizukenta.secs.hsms.HsmsMessageSelectStatus;
import com.shimizukenta.secs.hsms.HsmsMessageType;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2BytesParseException;
import com.shimizukenta.secs.secs2.impl.Secs2BytesParsers;

public abstract class AbstractHsmsMessageBuilder implements HsmsMessageBuilder {
	
	public AbstractHsmsMessageBuilder() {
		/* Nothing */
	}
	
	public static AbstractHsmsMessage build(byte[] header10Bytes) {
		return build(header10Bytes, Secs2.empty());
	}
	
	public static AbstractHsmsMessage build(byte[] header10Bytes, Secs2 body) {
		
		return new AbstractHsmsMessage(header10Bytes, body) {
			
			private static final long serialVersionUID = -495106331741472023L;
		};
	}
	
	public static AbstractHsmsMessage fromBytes(byte[] header, List<byte[]> bodies) throws Secs2BytesParseException {
		return build(header, Secs2BytesParsers.parse(bodies));
	}
	
	private final AtomicInteger autoNum = new AtomicInteger(0);
	
	protected byte[] getAutoNumber2Bytes() {
		int n = autoNum.incrementAndGet();
		return new byte[] {
				(byte)(n >> 8),
				(byte)n
		};
	}
	
	protected byte[] getSessionId2Bytes(AbstractHsmsSession session) {
		int n = session.sessionId();
		return new byte[] {
				(byte)(n >> 8),
				(byte)n
		};
	}
	
	protected byte[] getSystem4Bytes(AbstractHsmsSession session) {
		
		byte[] aa = this.getAutoNumber2Bytes();
		
		if ( session.isEquip() ) {
			
			byte[] ss = this.getSessionId2Bytes(session);
			
			return new byte[] {
					ss[0],
					ss[1],
					aa[0],
					aa[1]
			};
			
		} else {
			
			return new byte[] {
					(byte)0x0,
					(byte)0x0,
					aa[0],
					aa[1]
			};
		}
	}
	
	@Override
	public AbstractHsmsMessage buildSelectResponse(HsmsMessage primaryMsg, HsmsMessageSelectStatus status) {
		
		byte[] bs = primaryMsg.header10Bytes();
		
		byte[] header = new byte[] {
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
		
		return HsmsMessageBuilder.build(header);
	}
	
	@Override
	public AbstractHsmsMessage buildDeselectResponse(HsmsMessage primaryMsg, HsmsMessageDeselectStatus status) {
		
		byte[] bs = primaryMsg.header10Bytes();
		
		byte[] header = new byte[] {
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
		
		return HsmsMessageBuilder.build(header);
	}
	
	@Override
	public AbstractHsmsMessage buildLinktestRequest(AbstractHsmsSession session) {
		
		byte[] sysbytes = this.getSystem4Bytes(session);
		
		byte[] header = new byte[] {
				(byte)0xFF,
				(byte)0xFF,
				(byte)0x0,
				(byte)0x0,
				HsmsMessageType.LINKTEST_REQ.pType(),
				HsmsMessageType.LINKTEST_REQ.sType(),
				sysbytes[0],
				sysbytes[1],
				sysbytes[2],
				sysbytes[3]
		};
		
		return HsmsMessageBuilder.build(header);
	}
	
	@Override
	public AbstractHsmsMessage buildLinktestResponse(HsmsMessage primaryMsg) {
		
		byte[] bs = primaryMsg.header10Bytes();
		
		byte[] header = new byte[] {
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
		
		return HsmsMessageBuilder.build(header);
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
		
		byte[] header = new byte[] {
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
		
		return HsmsMessageBuilder.build(header);
	}
	
	@Override
	public AbstractHsmsMessage buildDataMessage(AbstractHsmsSession session, int strm, int func, boolean wbit) {
		return buildDataMessage(session, strm, func, wbit, Secs2.empty());
	}
	
	@Override
	public AbstractHsmsMessage buildDataMessage(AbstractHsmsSession session, int strm, int func, boolean wbit, Secs2 body) {
		
		byte[] sessionId2Bytes = this.getSessionId2Bytes(session);
		byte[] sysbytes = this.getSystem4Bytes(session);
		
		byte[] header = new byte[] {
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
			header[2] |= (byte)0x80;
		}
		
		return HsmsMessageBuilder.build(header, body);
	}
	
	@Override
	public AbstractHsmsMessage buildDataMessage(SecsMessage primaryMsg, int strm, int func, boolean wbit) {
		return buildDataMessage(primaryMsg, strm, func, wbit, Secs2.empty());
	}
	
	@Override
	public AbstractHsmsMessage buildDataMessage(SecsMessage primaryMsg, int strm, int func, boolean wbit, Secs2 body) {
		
		byte[] bs = primaryMsg.header10Bytes();
		
		byte[] header = new byte[] {
				bs[0],
				bs[1],
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
			header[2] |= (byte)0x80;
		}
		
		return HsmsMessageBuilder.build(header, body);
	}
	
}
