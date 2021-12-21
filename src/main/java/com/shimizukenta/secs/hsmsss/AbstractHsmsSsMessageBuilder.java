package com.shimizukenta.secs.hsmsss;

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

public abstract class AbstractHsmsSsMessageBuilder extends AbstractHsmsMessageBuilder implements HsmsSsMessageBuilder {
	
	public AbstractHsmsSsMessageBuilder() {
		super();
	}
	
	@Override
	public AbstractHsmsControlMessage buildSelectRequest(AbstractHsmsSession session) {
		
		byte[] sysbytes = this.getSystem4Bytes(session);
		
		byte[] header = new byte[] {
				(byte)0xFF,
				(byte)0xFF,
				(byte)0x0,
				(byte)0x0,
				HsmsMessageType.SELECT_REQ.pType(),
				HsmsMessageType.SELECT_REQ.sType(),
				sysbytes[0],
				sysbytes[1],
				sysbytes[2],
				sysbytes[3]
		};
		
		return this.buildHsmsControlMessage(header, Secs2.empty());
	}
	
	@Override
	public AbstractHsmsControlMessage buildDeselectRequest(AbstractHsmsSession session) {
		
		byte[] sysbytes = this.getSystem4Bytes(session);
		
		byte[] header = new byte[] {
				(byte)0xFF,
				(byte)0xFF,
				(byte)0x0,
				(byte)0x0,
				HsmsMessageType.DESELECT_REQ.pType(),
				HsmsMessageType.DESELECT_REQ.sType(),
				sysbytes[0],
				sysbytes[1],
				sysbytes[2],
				sysbytes[3]
		};
		
		return this.buildHsmsControlMessage(header, Secs2.empty());
	}
	
	@Override
	public AbstractHsmsControlMessage buildSeparateRequest(AbstractHsmsSession session) {
		
		byte[] sysbytes = this.getSystem4Bytes(session);
		
		byte[] header = new byte[] {
				(byte)0xFF,
				(byte)0xFF,
				(byte)0x0,
				(byte)0x0,
				HsmsMessageType.SEPARATE_REQ.pType(),
				HsmsMessageType.SEPARATE_REQ.sType(),
				sysbytes[0],
				sysbytes[1],
				sysbytes[2],
				sysbytes[3]
		};
		
		return this.buildHsmsControlMessage(header, Secs2.empty());
	}
	
	@Override
	protected AbstractHsmsControlMessage buildHsmsControlMessage(byte[] header, Secs2 body) {
		return AbstractHsmsSsMessageBuilder.newControlMessageInstance(header, body);
	}
	
	@Override
	protected AbstractHsmsDataMessage buildHsmsDataMessage(byte[] header, Secs2 body) {
		return AbstractHsmsSsMessageBuilder.newDataMessageInstance(header, body);
	}
	
	private static AbstractHsmsSsControlMessage newControlMessageInstance(byte[] header, Secs2 body) {
		
		return new AbstractHsmsSsControlMessage(header, body) {
			
			private static final long serialVersionUID = 8789730617277515223L;
		};
	}
	
	private static AbstractHsmsSsDataMessage newDataMessageInstance(byte[] header, Secs2 body) {
		
		return new AbstractHsmsSsDataMessage(header, body) {
			
			private static final long serialVersionUID = -2216223528422169461L;
		};
	}
	
	public static AbstractHsmsMessage build(byte[] header) {
		return AbstractHsmsSsMessageBuilder.build(header, Secs2.empty());
	}
	
	public static AbstractHsmsMessage build(byte[] header, Secs2 body) {
		if ( HsmsMessageType.get(header[4], header[5]) == HsmsMessageType.DATA ) {
			return AbstractHsmsSsMessageBuilder.newDataMessageInstance(header, body);
		} else {
			return AbstractHsmsSsMessageBuilder.newControlMessageInstance(header, body);
		}
	}
	
	public static AbstractHsmsMessage fromBytes(byte[] header, List<byte[]> bodies) throws Secs2BytesParseException {
		return AbstractHsmsSsMessageBuilder.build(header, Secs2BytesParsers.parse(bodies));
	}
	
	public static AbstractHsmsMessage fromMessage(HsmsMessage message) {
		if ( message instanceof AbstractHsmsMessage ) {
			return (AbstractHsmsMessage)message;
		} else {
			return AbstractHsmsSsMessageBuilder.build(message.header10Bytes(), message.secs2());
		}
	}
	
}
