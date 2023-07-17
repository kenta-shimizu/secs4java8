package com.shimizukenta.secs.secs1;

import com.shimizukenta.secs.AbstractSecsCommunicatorConfig;
import com.shimizukenta.secs.local.property.BooleanProperty;
import com.shimizukenta.secs.local.property.IntegerProperty;

/**
 * This class is config of SECS-I-Communicator.
 * 
 * <ul>
 * <li>To set Device-ID, {@link #deviceId(int)}</li>
 * <li>To set Master-Mode, {@link #isMaster(boolean)}</li>
 * <li>To set Retry, {@link #retry(int)}</li>
 * </ul>
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractSecs1CommunicatorConfig extends AbstractSecsCommunicatorConfig {
	
	private static final long serialVersionUID = 4897063498275749609L;
	
	/**
	 * Device-ID.
	 * 
	 */
	private final IntegerProperty deviceId = IntegerProperty.newInstance(10);
	
	/**
	 * Is-Master.
	 * 
	 */
	private final BooleanProperty isMaster = BooleanProperty.newInstance(true);
	
	/**
	 * Retry.
	 * 
	 */
	private final IntegerProperty retry = IntegerProperty.newInstance(3);
	
	/**
	 * Is check Message-Block Device-ID.
	 * 
	 */
	private final BooleanProperty isCheckMessageBlockDeviceId = BooleanProperty.newInstance(true);
	
	/**
	 * Constructor.
	 * 
	 */
	public AbstractSecs1CommunicatorConfig() {
		super();
	}
	
	/**
	 * Device-ID setter.
	 * 
	 * @param deviceId Device-ID
	 */
	public void deviceId(int deviceId) {
		if ( deviceId < 0 || deviceId > 0x7FFF ) {
			throw new DeviceIdIllegalArgumentException(deviceId);
		}
		this.deviceId.set(deviceId);
	}
	
	/**
	 * Returns Device-ID property.
	 * 
	 * @return device-id property
	 */
	public IntegerProperty deviceId() {
		return deviceId;
	}

	/**
	 * Set {@code true} if Master Mode.
	 * 
	 * @param isMaster set true if Master-Mode, otherwise false
	 */
	public void isMaster(boolean isMaster) {
		this.isMaster.set(isMaster);
	}
	
	/**
	 * Returns Master Mode getter.
	 * 
	 * @return {@code true} if Master Mode property
	 */
	public BooleanProperty isMaster() {
		return isMaster;
	}
	
	/**
	 * Rety setter.
	 * 
	 * @param retryCount retry-count-value is {@code >= 0}
	 */
	public void retry(int retryCount) {
		if ( retryCount < 0 ) {
			throw new RetryCountIllegalArgumentException(retryCount);
		}
		this.retry.set(retryCount);
	}
	
	/**
	 * Returns Retry property.
	 * 
	 * @return retry-count-property
	 */
	public IntegerProperty retry() {
		return retry;
	}
	
	/**
	 * Check Message-Block Device-ID, Not receive block if set true.
	 * 
	 * @param doCheck set true if check SECS-I Message Block Device-ID
	 */
	public void isCheckMessageBlockDeviceId(boolean doCheck) {
		this.isCheckMessageBlockDeviceId.set(doCheck);
	}
	
	/**
	 * Returns isCheckMessageBlockDeviceId property.
	 * 
	 * @return property
	 */
	public BooleanProperty isCheckMessageBlockDeviceId() {
		return this.isCheckMessageBlockDeviceId;
	}
	
}
