package com.shimizukenta.secs.secs1;

import com.shimizukenta.secs.impl.AbstractSecsCommunicatorConfig;
import com.shimizukenta.secs.local.property.BooleanProperty;
import com.shimizukenta.secs.local.property.IntegerProperty;

/**
 * This class is config of SECS-I-Communicator.
 * 
 * <ul>
 * <li>To set Device-ID, {@link #deviceId(int)}</i>
 * <li>To set Master-Mode, {@link #isMaster(boolean)}</li>
 * <li>To set Retry, {@link #retry(int)}</li>
 * </ul>
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractSecs1CommunicatorConfig extends AbstractSecsCommunicatorConfig {
	
	private static final long serialVersionUID = 4897063498275749609L;
	
	private final IntegerProperty deviceId = IntegerProperty.newInstance(10);
	private final BooleanProperty isMaster = BooleanProperty.newInstance(true);
	private final IntegerProperty retry = IntegerProperty.newInstance(3);
	private final BooleanProperty isCheckMessageBlockDeviceId = BooleanProperty.newInstance(true);
	
	public AbstractSecs1CommunicatorConfig() {
		super();
	}
	
	/**
	 * Device-ID setter.
	 * 
	 * @param id Device-ID
	 */
	public void deviceId(int id) {
		if ( id < 0 || id > 0x7FFF ) {
			throw new DeviceIdIllegalArgumentException(id);
		}
		this.deviceId.set(id);
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
	 * @param isMaster
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
	 * Check Message-Block Device-ID, Not receive block if settrue.
	 * 
	 * @param doCheck
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
	
	private static final class DeviceIdIllegalArgumentException extends IllegalArgumentException {
		
		private static final long serialVersionUID = 6679136614355645926L;
		
		public DeviceIdIllegalArgumentException(int id) {
			super("Device-ID is in 0 - 32767, id=" + id);
		}
	}
	
	private static final class RetryCountIllegalArgumentException extends IllegalArgumentException {
		
		private static final long serialVersionUID = 2911974034453457076L;
		
		public RetryCountIllegalArgumentException(int retryCount) {
			super("retry is >= 0, count=" + retryCount);
		}
	}
	
}
