package com.shimizukenta.secs;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractSecsLog implements SecsLog, Serializable {
	
	private static final long serialVersionUID = -6818838740323979681L;
	
	private final String subject;
	private final LocalDateTime timestamp;
	private final Object value;
	
	protected String subjectHeader;
	
	private String cacheToString;
	
	public AbstractSecsLog(CharSequence subject, LocalDateTime timestamp, Object value) {
		this.subject = subject.toString();
		this.timestamp = timestamp;
		this.value = value;
		
		this.subjectHeader = "";
		
		this.cacheToString = null;
	}
	
	public AbstractSecsLog(CharSequence subject, Object value) {
		this(subject, LocalDateTime.now(), value);
	}
	
	public AbstractSecsLog(CharSequence subject, LocalDateTime timestamp) {
		this(subject, timestamp, null);
	}
	
	public AbstractSecsLog(CharSequence subject) {
		this(subject, LocalDateTime.now(), null);
	}
	
	@Override
	public String subject() {
		return this.subject;
	}
	
	@Override
	public LocalDateTime timestamp() {
		return this.timestamp;
	}
	
	@Override
	public Optional<Object> value() {
		return this.value == null ? Optional.empty() : Optional.of(this.value);
	}
	
	protected void subjectHeader(String header) {
		synchronized ( this ) {
			this.subjectHeader = header;
		}
	}
	
	
	private static final String BR = System.lineSeparator();
	private static final String SPACE = "\t";
	private static DateTimeFormatter DATETIME = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
	
	@Override
	public String toString() {
		
		synchronized ( this ) {
			
			if ( this.cacheToString == null ) {
				
				StringBuilder sb = new StringBuilder(toStringTimestamp())
						.append(SPACE)
						.append(this.subjectHeader)
						.append(subject());
				
				toStringValue().ifPresent(v -> {
					sb.append(BR).append(v);
				});
				
				this.cacheToString = sb.toString();
			}
			
			return this.cacheToString;
		}
	}
	
	protected String toStringTimestamp() {
		return timestamp.format(DATETIME);
	}
	
	protected Optional<String> toStringValue() {
		return value().map(Object::toString);
	}
	
}
