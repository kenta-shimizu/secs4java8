package com.shimizukenta.secs.hsms;

import com.shimizukenta.secs.SecsMessagePassThroughLog;

/**
 * This interface is extend with Pass-Through HsmsMessage.
 * 
 * <p>
 * To get Pass-Through HsmsMessage, {@link #getHsmsMessage()}.<br />
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsMessagePassThroughLog extends SecsMessagePassThroughLog {
	
	/**
	 * Returns Pass-through HsmsMessage.
	 * 
	 * @return Pass-through HsmsMessage.
	 */
	public HsmsMessage getHsmsMessage();
}
