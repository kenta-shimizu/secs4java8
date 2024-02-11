package com.shimizukenta.secs;

/**
 * SecsCommunicaotor config value getter.
 * 
 * @author kenta-shimizu
 *
 */
public interface SecsCommunicatorConfigValueGettable {

	/**
	 * Is equipment getter.
	 * 
	 * @return {@code true} if communicator is equipment.
	 */
	public boolean isEquip();
	
	/**
	 * Returns Communicator DEVICE-ID.
	 * 
	 * <p>
	 * Returns -1 if not support DEVICE-ID communicator.<br />
	 * </p>
	 * 
	 * @return DEVICE-ID
	 */
	public int deviceId();
	
	/**
	 * Returns Communicator SESSION-ID.
	 * 
	 * <p>
	 * Returns {@code -1} if not support SESSION-ID communicator.<br />
	 * </p>
	 * 
	 * @return SESSION-ID
	 */
	public int sessionId();
	
}
