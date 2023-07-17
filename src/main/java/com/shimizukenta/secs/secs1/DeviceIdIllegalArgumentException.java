package com.shimizukenta.secs.secs1;

/**
 * Device-ID Illegal Argument Exception.
 * 
 * @author kenta-shimizu
 *
 */
public class DeviceIdIllegalArgumentException extends IllegalArgumentException {
	
	private static final long serialVersionUID = 2493901322725284193L;
	
	/**
	 * Constructor.
	 * 
	 * @param deviceId SECS-I config Device-ID
	 */
	public DeviceIdIllegalArgumentException(int deviceId) {
		super("Device-ID is in 0 - 32767, id=" + deviceId);
	}
	
}
