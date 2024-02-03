package com.shimizukenta.secs.hsmsgs.impl;

import com.shimizukenta.secs.hsms.HsmsMessageType;
import com.shimizukenta.secs.hsms.HsmsSession;
import com.shimizukenta.secs.hsms.impl.AbstractHsmsMessage;
import com.shimizukenta.secs.hsms.impl.AbstractHsmsMessageBuilder;

public abstract class AbstractHsmsGsMessageBuilder extends AbstractHsmsMessageBuilder {
	
	public AbstractHsmsGsMessageBuilder() {
		super();
	}
	
	@Override
	public AbstractHsmsMessage buildSelectRequest(HsmsSession session) {
		
		byte[] ss = this.device2Bytes(session);
		byte[] sysbytes = this.system4Bytes(session);
		
		byte[] header10Bytes = new byte[] {
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
		
		return buildMessage(header10Bytes);
	}
	
	@Override
	public AbstractHsmsMessage buildDeselectRequest(HsmsSession session) {
		
		byte[] ss = this.device2Bytes(session);
		byte[] sysbytes = this.system4Bytes(session);
		
		byte[] header10Bytes = new byte[] {
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
		
		return buildMessage(header10Bytes);
	}
	
	@Override
	public AbstractHsmsMessage buildSeparateRequest(HsmsSession session) {
		
		byte[] ss = this.device2Bytes(session);
		byte[] sysbytes = this.system4Bytes(session);
		
		byte[] header10Bytes = new byte[] {
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
		
		return buildMessage(header10Bytes);
	}
	
}
