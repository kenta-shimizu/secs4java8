package com.shimizukenta.secs.secs1;

import java.time.LocalDateTime;

public final class Secs1NotReceiveNextBlockEnqCircuitControlLog extends AbstractSecs1CircuitControlLog {
	
	private static final long serialVersionUID = -5755187611477567224L;
	
	private final Secs1MessageBlock prevBlock;
	private final byte recv;
	
	private Secs1NotReceiveNextBlockEnqCircuitControlLog(CharSequence subject, LocalDateTime timestamp, Secs1MessageBlock block, byte recv) {
		super(subject, timestamp, block);
		this.prevBlock = block;
		this.recv = recv;
	}
	
	private Secs1NotReceiveNextBlockEnqCircuitControlLog(CharSequence subject, Secs1MessageBlock block, byte recv) {
		super(subject, block);
		this.prevBlock = block;
		this.recv = recv;
	}
	
	public Secs1MessageBlock previousMessageBlock() {
		return this.prevBlock;
	}
	
	public byte receiveByte() {
		return this.recv;
	}
	
	private static final String commonSubjectHeader = "SECS1-Circuit-Control Not receive Next-Block-ENQ receive=";
	
	private static String createSubject(byte b) {
		return commonSubjectHeader + String.format("%02X", b);
	}
	
	public static Secs1NotReceiveNextBlockEnqCircuitControlLog newInstance(Secs1MessageBlock block, byte recv) {
		return new Secs1NotReceiveNextBlockEnqCircuitControlLog(createSubject(recv), block, recv);
	}
	
	public static Secs1NotReceiveNextBlockEnqCircuitControlLog newInstance(Secs1MessageBlock block, byte recv, LocalDateTime timestamp) {
		return new Secs1NotReceiveNextBlockEnqCircuitControlLog(createSubject(recv), timestamp, block, recv);
	}
	
}
