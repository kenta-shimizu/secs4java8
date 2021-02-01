package com.shimizukenta.secs;

import java.util.Optional;

/**
 * This interface implements Reference-Message of SecsWaitReplyMessageException.
 * 
 * <p>
 * To get Reference-SECS-Message, {@link #referenceSecsMessage()}.<br />
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public interface SecsWaitReplyMessageExceptionLog extends SecsThrowableLog {
	
	/**
	 * Returns Reference-SECS-Message if exist.
	 * 
	 * @return Reference-SECS-Message if exist
	 */
	public Optional<SecsMessage> referenceSecsMessage();
	
}
