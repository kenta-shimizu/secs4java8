package com.shimizukenta.secs.secs1;

import java.time.LocalDateTime;

public final class Secs1TimeoutT2LengthByteCircuitColtrolLog extends AbstractSecs1CircuitControlLog {
	
	private static final long serialVersionUID = 8042282283823555669L;
	
	private Secs1TimeoutT2LengthByteCircuitColtrolLog(CharSequence subject, LocalDateTime timestamp) {
		super(subject, timestamp);
	}
	
	private Secs1TimeoutT2LengthByteCircuitColtrolLog(CharSequence subject) {
		super(subject);
	}
	
	private static final String commonSubject = "SECS1-Circuit-Control T2-Timeout Length-Byte";
	
	public static Secs1TimeoutT2LengthByteCircuitColtrolLog newInstance() {
		return new Secs1TimeoutT2LengthByteCircuitColtrolLog(commonSubject);
	}
	
	public static Secs1TimeoutT2LengthByteCircuitColtrolLog newInstance(LocalDateTime timestamp) {
		return new Secs1TimeoutT2LengthByteCircuitColtrolLog(commonSubject, timestamp);
	}
	
}
