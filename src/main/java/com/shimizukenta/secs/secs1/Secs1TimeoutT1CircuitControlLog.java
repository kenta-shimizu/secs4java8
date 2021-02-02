package com.shimizukenta.secs.secs1;

import java.time.LocalDateTime;
import java.util.Optional;

public final class Secs1TimeoutT1CircuitControlLog extends AbstractSecs1CircuitControlLog {
	
	private static final long serialVersionUID = 3359274327825025654L;
	
	private final int position;
	
	private Secs1TimeoutT1CircuitControlLog(CharSequence subject, LocalDateTime timestamp, int position) {
		super(subject, timestamp, Integer.valueOf(position));
		this.position = position;
	}
	
	private Secs1TimeoutT1CircuitControlLog(CharSequence subject, int position) {
		super(subject, Integer.valueOf(position));
		this.position = position;
	}
	
	public int position() {
		return position;
	}
	
	@Override
	public Optional<String> optionalValueString() {
		return Optional.empty();
	}
	
	private static final String commonSubjectHeader = "SECS1-Circuit-Control T1-Timeout pos=";
	
	private static String createSubject(int position) {
		return commonSubjectHeader + position;
	}
	
	public static Secs1TimeoutT1CircuitControlLog newInstance(int position) {
		return new Secs1TimeoutT1CircuitControlLog(createSubject(position), position);
	}
	
	public static Secs1TimeoutT1CircuitControlLog newInstance(int position, LocalDateTime timestamp) {
		return new Secs1TimeoutT1CircuitControlLog(createSubject(position), timestamp, position);
	}
	
}
