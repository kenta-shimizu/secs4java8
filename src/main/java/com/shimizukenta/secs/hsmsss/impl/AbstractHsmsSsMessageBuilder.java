package com.shimizukenta.secs.hsmsss.impl;

import com.shimizukenta.secs.hsms.HsmsMessageType;
import com.shimizukenta.secs.hsms.HsmsSession;
import com.shimizukenta.secs.hsms.impl.AbstractHsmsMessage;
import com.shimizukenta.secs.hsms.impl.AbstractHsmsMessageBuilder;

public abstract class AbstractHsmsSsMessageBuilder extends AbstractHsmsMessageBuilder implements HsmsSsMessageBuilder {
	
	public AbstractHsmsSsMessageBuilder() {
		super();
	}
	
	@Override
	public AbstractHsmsMessage buildSelectRequest(HsmsSession session) {
		
		byte[] sysbytes = this.system4Bytes(session);
		
		byte[] header10Bytes = new byte[] {
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
		
		return buildMessage(header10Bytes);
	}
	
	@Override
	public AbstractHsmsMessage buildDeselectRequest(HsmsSession session) {
		
		byte[] sysbytes = this.system4Bytes(session);
		
		byte[] header10Bytes = new byte[] {
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
		
		return buildMessage(header10Bytes);
	}
	
	@Override
	public AbstractHsmsMessage buildLinktestRequest(HsmsSession session) {
		
		byte[] sysbytes = this.system4Bytes(session);
		
		byte[] header10Bytes = new byte[] {
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
		
		return buildMessage(header10Bytes);
	}
	
	@Override
	public AbstractHsmsMessage buildSeparateRequest(HsmsSession session) {
		
		byte[] sysbytes = this.system4Bytes(session);
		
		byte[] header10Bytes = new byte[] {
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
		
		return buildMessage(header10Bytes);
	}
	
}
