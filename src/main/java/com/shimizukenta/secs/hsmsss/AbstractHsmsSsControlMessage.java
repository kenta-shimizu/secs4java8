package com.shimizukenta.secs.hsmsss;

import com.shimizukenta.secs.hsms.AbstractHsmsControlMessage;
import com.shimizukenta.secs.secs2.Secs2;

public abstract class AbstractHsmsSsControlMessage extends AbstractHsmsControlMessage {
	
	private static final long serialVersionUID = -2422124720891020047L;
	
	public AbstractHsmsSsControlMessage(byte[] header) {
		super(header);
	}
	
	public AbstractHsmsSsControlMessage(byte[] header, Secs2 body) {
		super(header, body);
	}
	
	@Override
	public int sessionId() {
		return -1;
	}
	
}
