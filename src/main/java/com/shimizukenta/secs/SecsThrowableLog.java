package com.shimizukenta.secs;

/**
 * This interface is extend with Throwable.
 * 
 * <p>
 * To get Throwable cause, {@link #getCause()}.<br />
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public interface SecsThrowableLog extends SecsLog {
	
	/**
	 * Returns cause.
	 * 
	 * @return cause
	 */
	public Throwable getCause();
}
