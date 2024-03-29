package com.shimizukenta.secs.hsmsgs.impl;

import com.shimizukenta.secs.hsms.HsmsMessageType;
import com.shimizukenta.secs.hsms.HsmsSession;
import com.shimizukenta.secs.hsms.impl.AbstractHsmsMessage;
import com.shimizukenta.secs.hsms.impl.AbstractHsmsMessageBuilder;
import com.shimizukenta.secs.hsmsgs.HsmsGsCommunicatorConfig;

public abstract class AbstractHsmsGsMessageBuilder extends AbstractHsmsMessageBuilder implements HsmsGsMessageBuilder {
	
	private final HsmsGsCommunicatorConfig config;
	
	public AbstractHsmsGsMessageBuilder(HsmsGsCommunicatorConfig config) {
		super();
		this.config = config;
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
	public AbstractHsmsMessage buildLinktestRequest() {
		
		int autoNum = this.getAutoNumber();
		byte[] sysbytes = new byte[4];
		if (this.config.isEquip().booleanValue()) {
			sysbytes[0] = (byte)0xFF;
			sysbytes[1] = (byte)0xFF;
		} else {
			sysbytes[0] = (byte)0x00;
			sysbytes[1] = (byte)0x00;
		}
		sysbytes[2] = (byte)(autoNum >> 8);
		sysbytes[3] = (byte)autoNum;
		
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
