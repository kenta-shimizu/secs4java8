package com.shimizukenta.secs.secs1;

import java.time.LocalDateTime;

import com.shimizukenta.secs.AbstractSecsLog;

public class Secs1TrySendMessageBlockLog extends AbstractSecsLog {
	
	private static final long serialVersionUID = 8170410360421962375L;
	
	private static final String commonSubject = "Try-Send SECS1-Message-Block";
	
	private final Secs1MessageBlock block;
	
	public Secs1TrySendMessageBlockLog(Secs1MessageBlock block, LocalDateTime timestamp) {
		super(commonSubject, timestamp, block);
		this.block = block;
	}
	
	public Secs1TrySendMessageBlockLog(Secs1MessageBlock block) {
		super(commonSubject, block);
		this.block = block;
	}
	
	public Secs1MessageBlock messageBlock() {
		return this.block;
	}
	
}
