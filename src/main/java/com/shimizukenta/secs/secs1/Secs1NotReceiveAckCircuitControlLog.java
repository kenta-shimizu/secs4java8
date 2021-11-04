package com.shimizukenta.secs.secs1;

import java.time.LocalDateTime;

public final class Secs1NotReceiveAckCircuitControlLog extends AbstractSecs1CircuitControlLog {
	
	private static final long serialVersionUID = -8457413995945058147L;
	
	private final Secs1MessageBlock block;
	private final byte recv;
	
	private Secs1NotReceiveAckCircuitControlLog(CharSequence subject, LocalDateTime timestamp, Secs1MessageBlock block, byte recv) {
		super(subject, timestamp, block);
		this.block = block;
		this.recv = recv;
	}
	
	private Secs1NotReceiveAckCircuitControlLog(CharSequence subject, Secs1MessageBlock block, byte recv) {
		super(subject, block);
		this.block = block;
		this.recv = recv;
	}
	
	public Secs1MessageBlock messageBlock() {
		return this.block;
	}
	
	public byte receiveByte() {
		return this.recv;
	}
	
	private static final String commonSubjectHeader = "SECS1-Circuit-Control Not receive ACK receive=";
	
	private static String createSubject(byte b) {
		return commonSubjectHeader + String.format("%02X", b);
	}
	
	public static Secs1NotReceiveAckCircuitControlLog newInstance(Secs1MessageBlock block, byte recv) {
		return new Secs1NotReceiveAckCircuitControlLog(createSubject(recv), block, recv);
	}
	
	public static Secs1NotReceiveAckCircuitControlLog newInstance(Secs1MessageBlock block, byte recv, LocalDateTime timestamp) {
		return new Secs1NotReceiveAckCircuitControlLog(createSubject(recv), timestamp, block, recv);
	}
	
}
