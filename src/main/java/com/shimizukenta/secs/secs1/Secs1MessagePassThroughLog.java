package com.shimizukenta.secs.secs1;

import com.shimizukenta.secs.SecsMessagePassThroughLog;

/**
 * This interface is extend with Pass-Through Secs1Message.
 * 
 * <p>
 * To get Pass-Through Secs1Message, {@link #getSecs1Message()}.<br />
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public interface Secs1MessagePassThroughLog extends SecsMessagePassThroughLog {
	
	/**
	 * Returns Pass-through Secs1Message.
	 * 
	 * @return Pass-through Secs1Message
	 */
	public Secs1Message getSecs1Message();
	
}
