package com.shimizukenta.secs;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class SecsThrowableLog extends AbstractSecsLog {
	
	private static final long serialVersionUID = -7109145044702257924L;
	
	private static final String commonSubject = "Throwable";
	private final Throwable cause;
	
	private String cacheToSubject;
	
	public SecsThrowableLog(Throwable cause) {
		super(commonSubject, Objects.requireNonNull(cause));
		this.cause = cause;
		this.cacheToSubject = null;
	}
	
	public SecsThrowableLog(Throwable cause, LocalDateTime timestamp) {
		super(commonSubject, timestamp, Objects.requireNonNull(cause));
		this.cause = cause;
		this.cacheToSubject = null;
	}
	
	@Override
	public String subject() {
		synchronized ( this ) {
			if ( this.cacheToSubject == null ) {
				this.cacheToSubject = cause.getClass().getSimpleName();
			}
			return this.cacheToSubject;
		}
	}
	
	@Override
	protected Optional<String> toStringValue() {
		
		try (
				StringWriter sw = new StringWriter();
				) {
			
			try (
					PrintWriter pw = new PrintWriter(sw);
					) {
				
				this.cause.printStackTrace(pw);
				pw.flush();
				
				return Optional.of(sw.toString());
			}
		}
		catch ( IOException e ) {
			return Optional.of("THROWABLE-PARSE-FAILED");
		}
	}
	
}
