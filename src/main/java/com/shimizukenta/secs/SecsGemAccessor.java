package com.shimizukenta.secs;

import com.shimizukenta.secs.gem.Gem;

/**
 * SECS GEM Accessor.
 * 
 * @author kenta-shimizu
 *
 */
public interface SecsGemAccessor extends SecsCommunicatorConfigValueGettable, SecsMessageSendable {
	
	/**
	 * Returns GEM-interface.
	 * 
	 * @return GEM-interface-instance
	 */
	public Gem gem();
	
}
