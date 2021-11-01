package com.shimizukenta.secs.secs1;

import java.time.LocalDateTime;

public final class Secs1NotReceiveNextBlockEnqCircuitControlLog extends AbstractSecs1CircuitControlLog {
	
	private static final long serialVersionUID = 5993834362626837734L;
	
	private final SimpleSecs1MessageBlock prevBlock;
	private final byte recv;
	
	private Secs1NotReceiveNextBlockEnqCircuitControlLog(CharSequence subject, LocalDateTime timestamp, SimpleSecs1MessageBlock block, byte recv) {
		super(subject, timestamp, block);
		this.prevBlock = block;
		this.recv = recv;
	}

	private Secs1NotReceiveNextBlockEnqCircuitControlLog(CharSequence subject, SimpleSecs1MessageBlock block, byte recv) {
		super(subject, block);
		this.prevBlock = block;
		this.recv = recv;
	}
	
	public SimpleSecs1MessageBlock previousMessageBlock() {
		return this.prevBlock;
	}
	
	public byte receiveByte() {
		return this.recv;
	}
	
	private static final String commonSubjectHeader = "SECS1-Circuit-Control Not receive Next-Block-ENQ receive=";
	
	private static String createSubject(byte b) {
		return commonSubjectHeader + String.format("%02X", b);
	}
	
	public static Secs1NotReceiveNextBlockEnqCircuitControlLog newInstance(SimpleSecs1MessageBlock block, byte recv) {
		return new Secs1NotReceiveNextBlockEnqCircuitControlLog(createSubject(recv), block, recv);
	}
	
	public static Secs1NotReceiveNextBlockEnqCircuitControlLog newInstance(SimpleSecs1MessageBlock block, byte recv, LocalDateTime timestamp) {
		return new Secs1NotReceiveNextBlockEnqCircuitControlLog(createSubject(recv), timestamp, block, recv);
	}
	
}
