package com.shimizukenta.secs.secs1;

import com.shimizukenta.secs.AbstractSecsCommunicatorConfig;
import com.shimizukenta.secs.BooleanProperty;
import com.shimizukenta.secs.NumberProperty;
import com.shimizukenta.secs.ReadOnlyBooleanProperty;
import com.shimizukenta.secs.ReadOnlyNumberProperty;

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
	
	private final NumberProperty deviceId = NumberProperty.newInstance(10);
	private final BooleanProperty isMaster = BooleanProperty.newInstance(true);
	private final NumberProperty retry = NumberProperty.newInstance(3);
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
	 * Device-ID getter
	 * 
	 * @return device-id
	 */
	public ReadOnlyNumberProperty deviceId() {
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
	 * Master Mode getter.
	 * 
	 * @return {@code true} if Master Mode
	 */
	public ReadOnlyBooleanProperty isMaster() {
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
	 * Retry getter.
	 * 
	 * @return retry-count
	 */
	public ReadOnlyNumberProperty retry() {
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
	 * isCheckMessageBlockDeviceId getter.
	 * 
	 * @return property
	 */
	public ReadOnlyBooleanProperty isCheckMessageBlockDeviceId() {
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
