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
 * To get detail-information, {@link #value()}<br />
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
	String subject();

	/**
	 * Returns Log timestamp.
	 * 
	 * @return timestamp
	 */
	LocalDateTime timestamp();

	/**
	 * Returns Log detail-information.
	 * 
	 * @return value if exist
	 */
	Optional<Object> value();

}