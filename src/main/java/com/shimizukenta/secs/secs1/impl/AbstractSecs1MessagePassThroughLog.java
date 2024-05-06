package com.shimizukenta.secs.secs1.impl;

import com.shimizukenta.secs.impl.AbstractSecsMessagePassThroughLog;
import com.shimizukenta.secs.secs1.Secs1Message;
import com.shimizukenta.secs.secs1.Secs1MessagePassThroughLog;

public abstract class AbstractSecs1MessagePassThroughLog extends AbstractSecsMessagePassThroughLog implements Secs1MessagePassThroughLog {
	
	private static final long serialVersionUID = -956613482070851565L;
	
	private final Secs1Message msg;
	
	public AbstractSecs1MessagePassThroughLog(CharSequence subject, Secs1Message message) {
		super(subject, message);
		this.msg = message;
	}

	@Override
	public Secs1Message getSecs1Message() {
		return this.msg;
	}
	
	private static AbstractSecs1MessagePassThroughLog buildInstance(CharSequence subject, Secs1Message message) {
		
		return new AbstractSecs1MessagePassThroughLog(subject, message) {

			private static final long serialVersionUID = 6045808445434074900L;
		};
	}
	
	public static AbstractSecs1MessagePassThroughLog buildTrySend(Secs1Message message) {
		return buildInstance("Try-Send SECS1-Message", message);
	}
	
	public static AbstractSecs1MessagePassThroughLog buildSended(Secs1Message message) {
		return buildInstance("Sended SECS1-Message", message);
	}
	
	public static AbstractSecs1MessagePassThroughLog buildReceive(Secs1Message message) {
		return buildInstance("Receive SECS1-Message", message);
	}
	
}
