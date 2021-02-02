package com.shimizukenta.secs;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public abstract class AbstractSecsThrowableLog extends AbstractSecsLog implements SecsThrowableLog {
	
	private static final long serialVersionUID = -7109145044702257924L;
	
	private static final String commonSubject = "Throwable";
	private final Throwable cause;
	
	private String cacheToSubject;
	private String cacheToStringValue;
	
	public AbstractSecsThrowableLog(Throwable cause) {
		super(commonSubject, Objects.requireNonNull(cause));
		this.cause = cause;
		this.cacheToSubject = null;
		this.cacheToStringValue = null;
	}
	
	public AbstractSecsThrowableLog(Throwable cause, LocalDateTime timestamp) {
		super(commonSubject, timestamp, Objects.requireNonNull(cause));
		this.cause = cause;
		this.cacheToSubject = null;
		this.cacheToStringValue = null;
	}
	
	@Override
	public Throwable getCause() {
		return this.cause;
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
	public Optional<String> optionalValueString() {
		
		synchronized ( this ) {
			
			if ( this.cacheToStringValue == null ) {
				
				try (
						StringWriter sw = new StringWriter();
						) {
					
					try (
							PrintWriter pw = new PrintWriter(sw);
							) {
						
						this.cause.printStackTrace(pw);
						pw.flush();
						
						this.cacheToStringValue = sw.toString();
					}
				}
				catch ( IOException e ) {
					this.cacheToStringValue = "THROWABLE-PARSE-FAILED";
				}
			}
			
			return Optional.of(this.cacheToStringValue);
		}
	}
	
}
