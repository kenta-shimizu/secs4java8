package com.shimizukenta.secs.hsms;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2BytesParseException;
import com.shimizukenta.secs.secs2.Secs2BytesParser;

public abstract class AbstractHsmsMessageBuilder implements HsmsMessageBuilder {
	
	private final byte[] sessionId2Bytes;
	private final Supplier<byte[]> system4BytesSupplier;
	
	public AbstractHsmsMessageBuilder(AbstractHsmsSession session) {
		
		int n = session.sessionId();
		
		this.sessionId2Bytes = new byte[] {
				(byte)(n >> 8),
				(byte)n
		};
		
		if ( session.isEquip() ) {
			
			this.system4BytesSupplier = () -> {
				
				byte[] aa = this.getAutoNumber2Bytes();
				
				return new byte[] {
						sessionId2Bytes[0],
						sessionId2Bytes[1],
						aa[0],
						aa[1]
				};
			};
			
		} else {
			
			this.system4BytesSupplier = () -> {
				
				byte[] aa = this.getAutoNumber2Bytes();
				
				return new byte[] {
						(byte)0x0,
						(byte)0x0,
						aa[0],
						aa[1]
				};
			};
		}
	}
	
	private final AtomicInteger autoNum = new AtomicInteger(0);
	
	protected byte[] getAutoNumber2Bytes() {
		int n = autoNum.incrementAndGet();
		return new byte[] {
				(byte)(n >> 8),
				(byte)n
		};
	}
	
	protected byte[] getSessionId2Bytes() {
		return this.sessionId2Bytes;
	}
	
	protected byte[] getSystem4Bytes() {
		return this.system4BytesSupplier.get();
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
		
		return this.buildHsmsControlMessage(header, Secs2.empty());
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
		
		return this.buildHsmsControlMessage(header, Secs2.empty());
	}
	
	@Override
	public AbstractHsmsMessage buildLinktestRequest() {
		
		byte[] sysbytes = this.getSystem4Bytes();
		
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
		
		return this.buildHsmsControlMessage(header, Secs2.empty());
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
		
		return this.buildHsmsControlMessage(header, Secs2.empty());
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
		
		return this.buildHsmsControlMessage(header, Secs2.empty());
	}
	
	@Override
	public AbstractHsmsMessage buildDataMessage(int strm, int func, boolean wbit) {
		return buildDataMessage(strm, func, wbit, Secs2.empty());
	}
	
	@Override
	public AbstractHsmsMessage buildDataMessage(int strm, int func, boolean wbit, Secs2 body) {
		
		byte[] sysbytes = this.getSystem4Bytes();
		
		byte[] header = new byte[] {
				this.sessionId2Bytes[0],
				this.sessionId2Bytes[1],
				(byte)(strm & 0x7F),
				(byte)func,
				HsmsMessageType.DATA.pType(),
				HsmsMessageType.DATA.sType(),
				sysbytes[0],
				sysbytes[1],
				sysbytes[2],
				sysbytes[3]
		};
		
		return this.buildHsmsDataMessage(header, body);
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
		
		return this.buildHsmsDataMessage(header, body);
	}
	
	@Override
	public AbstractHsmsMessage fromBytes(byte[] header, List<byte[]> bodies) throws Secs2BytesParseException {
		
		Secs2 body = Secs2BytesParser.getInstance().parse(bodies);
		
		if ( HsmsMessageType.get(header[4], header[5]) == HsmsMessageType.DATA ) {
			return buildHsmsDataMessage(header, body);
		} else {
			return buildHsmsControlMessage(header, body);
		}
	}
	
	protected AbstractHsmsDataMessage buildHsmsDataMessage(byte[] header, Secs2 body) {
		
		return new AbstractHsmsDataMessage(header, body) {
			
			private static final long serialVersionUID = 3392473904493672566L;
		};
	}
	
	abstract protected AbstractHsmsControlMessage buildHsmsControlMessage(byte[] header, Secs2 body);
	
}
