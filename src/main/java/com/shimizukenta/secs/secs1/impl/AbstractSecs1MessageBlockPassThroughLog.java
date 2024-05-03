package com.shimizukenta.secs.secs1.impl;

import com.shimizukenta.secs.impl.AbstractSecsLog;
import com.shimizukenta.secs.secs1.Secs1MessageBlock;
import com.shimizukenta.secs.secs1.Secs1MessageBlockPassThroughLog;

public abstract class AbstractSecs1MessageBlockPassThroughLog extends AbstractSecsLog implements Secs1MessageBlockPassThroughLog {
	
	private static final long serialVersionUID = 5236757589131445699L;
	
	private final Secs1MessageBlock block;
	
	public AbstractSecs1MessageBlockPassThroughLog(CharSequence subject, Secs1MessageBlock block) {
		super(subject, block);
		this.block = block;
	}
	
	@Override
	public Secs1MessageBlock getSecs1MessageBlock() {
		return this.block;
	}
	
	private static AbstractSecs1MessageBlockPassThroughLog buildInstance(CharSequence subject, Secs1MessageBlock block) {
		return new AbstractSecs1MessageBlockPassThroughLog(subject, block) {

			private static final long serialVersionUID = -7689234996798651541L;
		};
	}
	
	public static AbstractSecs1MessageBlockPassThroughLog buildTrySend(Secs1MessageBlock block) {
		return AbstractSecs1MessageBlockPassThroughLog.buildInstance("Try-Send SECS1-Message-Block", block);
	}
	
	public static AbstractSecs1MessageBlockPassThroughLog buildSended(Secs1MessageBlock block) {
		return AbstractSecs1MessageBlockPassThroughLog.buildInstance("Sended SECS1-Message-Block", block);
	}
	
	public static AbstractSecs1MessageBlockPassThroughLog buildReceive(Secs1MessageBlock block) {
		return AbstractSecs1MessageBlockPassThroughLog.buildInstance("Receive SECS1-Message-Block", block);
	}
	
}
