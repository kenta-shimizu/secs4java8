package com.shimizukenta.secs.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;
import java.util.Optional;

import com.shimizukenta.secs.SecsThrowableLog;

public abstract class AbstractSecsThrowableLog extends AbstractSecsLog implements SecsThrowableLog {
	
	private static final long serialVersionUID = -7109145044702257924L;
	
	private final Object sync = new Object();
	
	private final Throwable cause;
	
	private String cacheToStringValue;
	
	public AbstractSecsThrowableLog(Throwable cause) {
		super(cause.getClass().getSimpleName(), Objects.requireNonNull(cause));
		this.cause = cause;
		this.cacheToStringValue = null;
	}
	
	@Override
	public Throwable getCause() {
		return this.cause;
	}
	
	@Override
	public Optional<String> optionalValueString() {
		
		synchronized (this.sync) {
			
			if (this.cacheToStringValue == null) {
				
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
				catch (IOException e) {
					this.cacheToStringValue = "THROWABLE-PARSE-FAILED";
				}
			}
			
			return Optional.of(this.cacheToStringValue);
		}
	}
	
}
