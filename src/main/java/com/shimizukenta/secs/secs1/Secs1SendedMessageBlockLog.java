package com.shimizukenta.secs.secs1;

import java.time.LocalDateTime;

import com.shimizukenta.secs.AbstractSecsLog;

public class Secs1SendedMessageBlockLog extends AbstractSecsLog {
	
	private static final long serialVersionUID = -469332911349987647L;
	
	private static final String commonSubject = "Sended SECS1-Message-Block";
	
	private final Secs1MessageBlock block;
	
	public Secs1SendedMessageBlockLog(Secs1MessageBlock block, LocalDateTime timestamp) {
		super(commonSubject, timestamp, block);
		this.block = block;
	}
	
	public Secs1SendedMessageBlockLog(Secs1MessageBlock block) {
		super(commonSubject, block);
		this.block = block;
	}
	
	public Secs1MessageBlock messageBlock() {
		return this.block;
	}
	
}
