package com.shimizukenta.secs.hsmsss.impl;

import com.shimizukenta.secs.hsms.impl.AbstractHsmsDataMessage;
import com.shimizukenta.secs.secs2.Secs2;

public abstract class AbstractHsmsSsDataMessage extends AbstractHsmsDataMessage {
	
	private static final long serialVersionUID = -7771560085653492974L;
	
	public AbstractHsmsSsDataMessage(byte[] header, Secs2 body) {
		super(header, body);
	}
	
	public AbstractHsmsSsDataMessage(byte[] header) {
		super(header);
	}
	
}
