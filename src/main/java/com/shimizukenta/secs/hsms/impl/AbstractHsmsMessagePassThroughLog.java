package com.shimizukenta.secs.hsms.impl;

import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsMessagePassThroughLog;
import com.shimizukenta.secs.impl.AbstractSecsMessagePassThroughLog;

public abstract class AbstractHsmsMessagePassThroughLog extends AbstractSecsMessagePassThroughLog implements HsmsMessagePassThroughLog {
	
	private static final long serialVersionUID = 4158894689256082666L;
	
	private final HsmsMessage msg;
	
	public AbstractHsmsMessagePassThroughLog(CharSequence subject, HsmsMessage message) {
		super(subject, message);
		this.msg = message;
	}
	
	@Override
	public HsmsMessage getHsmsMessage() {
		return this.msg;
	}
	
	private static AbstractHsmsMessagePassThroughLog buildInstance(CharSequence subject, HsmsMessage message) {
		
		return new AbstractHsmsMessagePassThroughLog(subject, message) {
			
			private static final long serialVersionUID = -9100022613125458769L;
		};
	}
	
	public static AbstractHsmsMessagePassThroughLog buildTrySend(HsmsMessage message) {
		return buildInstance("Try-Send HSMS-Message", message);
	}
	
	public static AbstractHsmsMessagePassThroughLog buildSended(HsmsMessage message) {
		return buildInstance("Sended HSMS-Message", message);
	}

	public static AbstractHsmsMessagePassThroughLog buildReceive(HsmsMessage message) {
		return buildInstance("Receive HSMS-Message", message);
	}
	
}
