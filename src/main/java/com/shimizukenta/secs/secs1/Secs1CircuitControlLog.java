package com.shimizukenta.secs.secs1;

import java.time.LocalDateTime;

import com.shimizukenta.secs.AbstractSecsLog;

public class Secs1CircuitControlLog extends AbstractSecsLog {
	
	private static final long serialVersionUID = -8516994973196921648L;
	
	public Secs1CircuitControlLog(CharSequence subject, LocalDateTime timestamp, Secs1MessageBlock block) {
		super(subject, timestamp, block);
	}

	public Secs1CircuitControlLog(CharSequence subject, Secs1MessageBlock block) {
		super(subject, block);
	}

	public Secs1CircuitControlLog(CharSequence subject, LocalDateTime timestamp) {
		super(subject, timestamp);
	}

	public Secs1CircuitControlLog(CharSequence subject) {
		super(subject);
	}
	
}
