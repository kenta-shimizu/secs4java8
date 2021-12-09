package com.shimizukenta.secs.hsmsgs;

import com.shimizukenta.secs.hsms.AbstractHsmsControlMessage;
import com.shimizukenta.secs.secs2.Secs2;

public abstract class AbstractHsmsGsControlMessage extends AbstractHsmsControlMessage {
	
	private static final long serialVersionUID = -8930240343771260785L;
	
	public AbstractHsmsGsControlMessage(byte[] header) {
		super(header);
	}
	
	public AbstractHsmsGsControlMessage(byte[] header, Secs2 body) {
		super(header, body);
	}
	
	@Override
	public int sessionId() {
		
		switch ( this.messageType() ) {
		case LINKTEST_REQ:
		case LINKTEST_RSP: {
			
			return -1;
			/* break; */
		}
		default: {
			
			return this.getSessionIdFromHeader();
		}
		}
	}
	
}
