package com.shimizukenta.secs;

import com.shimizukenta.secs.SecsCommunicator;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

/**
 * SecsLog contains subject, timestamp, defail-information.
 * 
 * <p>
 * This instance is received from {@link SecsCommunicator#addSecsLogListener(SecsLogListener)}<br />
 * </p>
 * <p>
 * To get subject, {@link #subject()}<br />
 * To get {@link LocalDateTime} timestamp, {@link #timestamp()}<br />
 * To get defail-information, {@link #value()}<br />
 * </p>
 * <p>
 * {@link #toString()} is overrided to pretty-printing.<br />
 * </p>
 * <p>
 * Instances of this class are immutable.<br />
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public class SecsLog implements Serializable {
	
	private static final long serialVersionUID = 3912865343300189344L;

	private final String subject;
	private final LocalDateTime timestamp;
	private final Object value;
	
	public SecsLog(CharSequence subject, LocalDateTime timestamp, Object value) {
		this.subject = subject.toString();
		this.timestamp = timestamp;
		this.value = value;
	}
	
	public SecsLog(CharSequence subject, Object value) {
		this(subject, LocalDateTime.now(), value);
	}
	
	public SecsLog(CharSequence subject, LocalDateTime timestamp) {
		this(subject, timestamp, null);
	}
	
	public SecsLog(CharSequence subject) {
		this(subject, LocalDateTime.now(), null);
	}
	
	public SecsLog(Throwable t) {
		this(createThrowableSubject(t), LocalDateTime.now(), t);
	}
	
	public static String createThrowableSubject(Throwable t) {
		return Objects.requireNonNull(t).getClass().getSimpleName();
	}
	
	/**
	 * Log subject getter.
	 * 
	 * @return subject-string
	 */
	public String subject() {
		return this.subject;
	}
	
	/**
	 * Log timestamp getter.
	 * 
	 * @return timestamp
	 */
	public LocalDateTime timestamp() {
		return this.timestamp;
	}
	
	/**
	 * Log detail-information getter.
	 * 
	 * @return value if exist
	 */
	public Optional<Object> value() {
		return this.value == null ? Optional.empty() : Optional.of(this.value);
	}
	
	
	private static final String BR = System.lineSeparator();
	private static final String SPACE = "\t";
	private static DateTimeFormatter DATETIME = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder(toStringTimestamp())
				.append(SPACE)
				.append(subject());
		
		toStringValue().ifPresent(v -> {
			sb.append(BR).append(v);
		});
		
		return sb.toString();
	}
	
	private String toStringTimestamp() {
		return timestamp.format(DATETIME);
	}
	
	private Optional<String> toStringValue() {
		
		return value().map(o -> {
			
			if ( o instanceof Throwable ) {
				
				try (
						StringWriter sw = new StringWriter();
						) {
					
					try (
							PrintWriter pw = new PrintWriter(sw);
							) {
						
						((Throwable) o).printStackTrace(pw);
						pw.flush();
						
						return sw.toString();
					}
				}
				catch ( IOException e ) {
					return "THROWABLE-PARSE-FAILED";
				}
				
			} else {
				
				return o.toString();
			}
			
		});
	}
	
}
