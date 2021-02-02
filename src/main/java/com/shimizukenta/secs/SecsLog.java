package com.shimizukenta.secs;

import java.time.LocalDateTime;
import java.util.Optional;


/**
 * This intarface contains subject, timestamp, detail-information.
 * 
 * <p>
 * This interface is received from {@link SecsCommunicator#addSecsLogListener(SecsLogListener)}<br />
 * </p>
 * <p>
 * To get subject, {@link #subject()}<br />
 * To get {@link LocalDateTime} timestamp, {@link #timestamp()}<br />
 * To get detail-information Object, {@link #value()}<br />
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public interface SecsLog {

	/**
	 * Returns Log subject.
	 * 
	 * @return subject-string
	 */
	public String subject();

	/**
	 * Returns Log timestamp.
	 * 
	 * @return timestamp
	 */
	public LocalDateTime timestamp();

	/**
	 * Returns Log detail-information Object.
	 * 
	 * @return value if exist
	 */
	public Optional<Object> value();
	
	/**
	 * Returns subject-header.
	 * 
	 * @return subject-header
	 */
	public String subjectHeader();
	
	/**
	 * Returns value-String
	 * 
	 * @return value-String if exist
	 */
	public Optional<String> optionalValueString();
	
}