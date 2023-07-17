package com.shimizukenta.secs.secs1;

import java.util.List;

import com.shimizukenta.secs.SecsMessage;

/**
 * SECS-I Message.
 * 
 * @author kenta-shimizu
 *
 */
public interface Secs1Message extends SecsMessage {
	
	/**
	 * Returns Message R-Bit.
	 * 
	 * @return true if has r-bit
	 */
	public boolean rbit();
	
	/**
	 * Returns Message blocks.
	 * 
	 * @return List of Message blocks
	 */
	public List<Secs1MessageBlock> toBlocks();
	
}
