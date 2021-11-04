package com.shimizukenta.secs.secs1;

import java.time.LocalDateTime;

public final class Secs1TimeoutT2AckCircuitControlLog extends AbstractSecs1CircuitControlLog {
	
	private static final long serialVersionUID = 4963145015095648066L;
	
	private final Secs1MessageBlock block;
	
	private Secs1TimeoutT2AckCircuitControlLog(CharSequence subject, LocalDateTime timestamp, Secs1MessageBlock block) {
		super(subject, timestamp);
		this.block = block;
	}
	
	private Secs1TimeoutT2AckCircuitControlLog(CharSequence subject, Secs1MessageBlock block) {
		super(subject, block);
		this.block = block;
	}
	
	public Secs1MessageBlock messageBlock() {
		return this.block;
	}
	
	private static final String commonSubject = "SECS1-Circuit-Control T2-Timeout ACK";
	
	public static Secs1TimeoutT2AckCircuitControlLog newInstance(Secs1MessageBlock block) {
		return new Secs1TimeoutT2AckCircuitControlLog(commonSubject, block);
	}
	
	public static Secs1TimeoutT2AckCircuitControlLog newInstance(Secs1MessageBlock block, LocalDateTime timestamp) {
		return new Secs1TimeoutT2AckCircuitControlLog(commonSubject, timestamp, block);
	}
	
}
