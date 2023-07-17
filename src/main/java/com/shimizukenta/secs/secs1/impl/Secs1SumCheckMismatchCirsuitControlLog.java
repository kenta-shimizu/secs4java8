package com.shimizukenta.secs.secs1.impl;

import java.time.LocalDateTime;

public final class Secs1SumCheckMismatchCirsuitControlLog extends AbstractSecs1CircuitControlLog {
	
	private static final long serialVersionUID = 7501853603924670922L;

	private Secs1SumCheckMismatchCirsuitControlLog(CharSequence subject, LocalDateTime timestamp) {
		super(subject, timestamp);
	}

	private Secs1SumCheckMismatchCirsuitControlLog(CharSequence subject) {
		super(subject);
	}
	
	private static final String commonSubject = "SECS1-Circuit-Control Sum-Check mismatch";
	
	public static Secs1SumCheckMismatchCirsuitControlLog newInstance() {
		return new Secs1SumCheckMismatchCirsuitControlLog(commonSubject);
	}
	
	public static Secs1SumCheckMismatchCirsuitControlLog newInstance(LocalDateTime timestamp) {
		return new Secs1SumCheckMismatchCirsuitControlLog(commonSubject, timestamp);
	}
	
}
