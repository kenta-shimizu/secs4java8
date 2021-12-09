package com.shimizukenta.secs.hsmsgs;

import com.shimizukenta.secs.hsms.AbstractHsmsDataMessage;
import com.shimizukenta.secs.secs2.Secs2;

public abstract class AbstractHsmsGsDataMessage extends AbstractHsmsDataMessage {
	
	private static final long serialVersionUID = 1729869916863780151L;
	
	public AbstractHsmsGsDataMessage(byte[] header, Secs2 body) {
		super(header, body);
	}

	public AbstractHsmsGsDataMessage(byte[] header) {
		super(header);
	}

}
