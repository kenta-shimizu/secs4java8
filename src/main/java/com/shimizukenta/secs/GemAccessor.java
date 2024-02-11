package com.shimizukenta.secs;

import com.shimizukenta.secs.gem.Gem;

/**
 * GEM Accessor.
 * 
 * @author kenta-shimizu
 *
 */
public interface GemAccessor extends SecsCommunicatorConfigValueGettable, SecsMessageSendable {
	
	/**
	 * Returns GEM-interface.
	 * 
	 * @return GEM-interface-instance
	 */
	public Gem gem();
	
}
