package com.shimizukenta.secs.secs1;

import com.shimizukenta.secs.SecsLog;

/**
 * This is interface of Secs1MessageBlock log.
 * 
 * @author kenta-shimizu
 *
 */
public interface Secs1MessageBlockPassThroughLog extends SecsLog {
	
	/**
	 * Returns Secs1MessageBlock.
	 * 
	 * @return Secs1MessageBlock
	 */
	public Secs1MessageBlock getSecs1MessageBlock();
}
