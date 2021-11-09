package com.shimizukenta.secs.hsmsss;

import com.shimizukenta.secs.hsms.AbstractHsmsControlMessage;
import com.shimizukenta.secs.hsms.AbstractHsmsMessage;
import com.shimizukenta.secs.hsms.AbstractHsmsMessageBuilder;
import com.shimizukenta.secs.hsms.AbstractHsmsSession;
import com.shimizukenta.secs.hsms.HsmsMessageType;
import com.shimizukenta.secs.secs2.Secs2;

public abstract class AbstractHsmsSsMessageBuilder extends AbstractHsmsMessageBuilder {
	
	public AbstractHsmsSsMessageBuilder(AbstractHsmsSession session) {
		super(session);
	}
	
	@Override
	public AbstractHsmsMessage buildSelectRequest() {
		
		byte[] sysbytes = this.getSystem4Bytes();
		
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
		
		return buildHsmsControlMessage(header, Secs2.empty());
	}
	
	@Override
	public AbstractHsmsMessage buildDeselectRequest() {
		
		byte[] sysbytes = this.getSystem4Bytes();
		
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
		
		return buildHsmsControlMessage(header, Secs2.empty());
	}
	
	@Override
	public AbstractHsmsMessage buildSeparateRequest() {
		
		byte[] sysbytes = this.getSystem4Bytes();
		
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
		
		return buildHsmsControlMessage(header, Secs2.empty());
	}
	
	@Override
	protected AbstractHsmsControlMessage buildHsmsControlMessage(byte[] header, Secs2 body) {
		
		return new AbstractHsmsSsControlMessage(header, body) {
			
			private static final long serialVersionUID = 6205976443506795494L;
		};
	}
	
}
