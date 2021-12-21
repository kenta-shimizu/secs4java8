package com.shimizukenta.secs.hsmsgs;

import java.util.List;

import com.shimizukenta.secs.hsms.AbstractHsmsControlMessage;
import com.shimizukenta.secs.hsms.AbstractHsmsDataMessage;
import com.shimizukenta.secs.hsms.AbstractHsmsMessage;
import com.shimizukenta.secs.hsms.AbstractHsmsMessageBuilder;
import com.shimizukenta.secs.hsms.AbstractHsmsSession;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessageType;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2BytesParseException;
import com.shimizukenta.secs.secs2.Secs2BytesParsers;

public abstract class AbstractHsmsGsMessageBuilder extends AbstractHsmsMessageBuilder implements HsmsGsMessageBuilder {
	
	public AbstractHsmsGsMessageBuilder() {
		super();
	}
	
	@Override
	public AbstractHsmsControlMessage buildSelectRequest(AbstractHsmsSession session) {
		
		byte[] ss = this.getSessionId2Bytes(session);
		byte[] sysbytes = this.getSystem4Bytes(session);
		
		byte[] header = new byte[] {
				ss[0],
				ss[1],
				(byte)0x0,
				(byte)0x0,
				HsmsMessageType.SELECT_REQ.pType(),
				HsmsMessageType.SELECT_REQ.sType(),
				sysbytes[0],
				sysbytes[1],
				sysbytes[2],
				sysbytes[3]
		};
		
		return buildHsmsControlMessage(header, Secs2.empty());
	}
	
	@Override
	public AbstractHsmsControlMessage buildDeselectRequest(AbstractHsmsSession session) {
		
		byte[] ss = this.getSessionId2Bytes(session);
		byte[] sysbytes = this.getSystem4Bytes(session);
		
		byte[] header = new byte[] {
				ss[0],
				ss[1],
				(byte)0x0,
				(byte)0x0,
				HsmsMessageType.DESELECT_REQ.pType(),
				HsmsMessageType.DESELECT_REQ.sType(),
				sysbytes[0],
				sysbytes[1],
				sysbytes[2],
				sysbytes[3]
		};
		
		return buildHsmsControlMessage(header, Secs2.empty());
	}
	
	@Override
	public AbstractHsmsControlMessage buildSeparateRequest(AbstractHsmsSession session) {
		
		byte[] ss = this.getSessionId2Bytes(session);
		byte[] sysbytes = this.getSystem4Bytes(session);
		
		byte[] header = new byte[] {
				ss[0],
				ss[1],
				(byte)0x0,
				(byte)0x0,
				HsmsMessageType.SEPARATE_REQ.pType(),
				HsmsMessageType.SEPARATE_REQ.sType(),
				sysbytes[0],
				sysbytes[1],
				sysbytes[2],
				sysbytes[3]
		};
		
		return buildHsmsControlMessage(header, Secs2.empty());
	}
	
	@Override
	protected AbstractHsmsControlMessage buildHsmsControlMessage(byte[] header, Secs2 body) {
		return AbstractHsmsGsMessageBuilder.newControlMessageInstance(header, body);
	}
	
	@Override
	protected AbstractHsmsDataMessage buildHsmsDataMessage(byte[] header, Secs2 body) {
		return AbstractHsmsGsMessageBuilder.newDataMessageInstance(header, body);
	}
	
	private static AbstractHsmsGsControlMessage newControlMessageInstance(byte[] header, Secs2 body) {
		
		return new AbstractHsmsGsControlMessage(header, body) {
			
			private static final long serialVersionUID = 7548549319511317849L;
		};
	}
	
	private static AbstractHsmsGsDataMessage newDataMessageInstance(byte[] header, Secs2 body) {
		
		return new AbstractHsmsGsDataMessage(header, body) {
			
			private static final long serialVersionUID = 7134587849768341782L;
		};
	}
	
	public static AbstractHsmsMessage build(byte[] header) {
		return AbstractHsmsGsMessageBuilder.build(header, Secs2.empty());
	}
	
	public static AbstractHsmsMessage build(byte[] header, Secs2 body) {
		if ( HsmsMessageType.get(header[4], header[5]) == HsmsMessageType.DATA ) {
			return AbstractHsmsGsMessageBuilder.newDataMessageInstance(header, body);
		} else {
			return AbstractHsmsGsMessageBuilder.newControlMessageInstance(header, body);
		}
	}
	
	public static AbstractHsmsMessage fromBytes(byte[] header, List<byte[]> bodies) throws Secs2BytesParseException {
		return AbstractHsmsGsMessageBuilder.build(header, Secs2BytesParsers.parse(bodies));
	}
	
	public static AbstractHsmsMessage fromMessage(HsmsMessage message) {
		if ( message instanceof AbstractHsmsMessage ) {
			return (AbstractHsmsMessage)message;
		} else {
			return AbstractHsmsGsMessageBuilder.build(message.header10Bytes(), message.secs2());
		}
	}
	
}
