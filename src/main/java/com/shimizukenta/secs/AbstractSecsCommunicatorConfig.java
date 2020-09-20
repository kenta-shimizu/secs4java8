package com.shimizukenta.secs;

import java.io.Serializable;
import java.util.Objects;

import com.shimizukenta.secs.gem.AbstractGemConfig;

/**
 * This abstract class is implementation of communicate config.<br />
 * To set device-id, {@link #deviceId(int)}<br />
 * To set is-equip, {@link #isEquip(boolean)}<br />
 * To set timeouts, {@link #timeout()}<br />
 * To set gem, {@link #gem()}<br />
 * To set log-subject-header, {@link #logSubjectHeader(CharSequence)}
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractSecsCommunicatorConfig implements Serializable {
	
	private static final long serialVersionUID = -8456991094606676409L;
	
	private final SecsTimeout timeout = new SecsTimeout();
	private final NumberProperty deviceId = NumberProperty.newInstance(10);
	private final BooleanProperty isEquip = BooleanProperty.newInstance(false);
	
	private final AbstractGemConfig gem = new AbstractGemConfig() {
		private static final long serialVersionUID = -3386783271396322749L;
	};
	
	private final StringProperty logSubjectHeader = StringProperty.newInstance("");
	
	public AbstractSecsCommunicatorConfig() {
		/* Nothing */
	}
	
	/**
	 * Device-ID setter.
	 * 
	 * @param id
	 */
	public void deviceId(int id) {
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
	 * Set true if equipment.
	 * 
	 * @param is-equip
	 */
	public void isEquip(boolean f) {
		this.isEquip.set(f);
	}
	
	/**
	 * getter
	 * 
	 * @return true if equipment
	 */
	public ReadOnlyBooleanProperty isEquip() {
		return isEquip;
	}
	
	/**
	 * SecsTimeout getter
	 * 
	 * @return SecsTimeout
	 */
	public SecsTimeout timeout() {
		return timeout;
	}
	
	/**
	 * AbstractGemConfig getter.
	 * 
	 * @return AbstractGemConfig
	 */
	public AbstractGemConfig gem() {
		return gem;
	}
	
	/**
	 * if setted, insert header to subject of SecsLog
	 * 
	 * @param header of SecsLog
	 */
	public void logSubjectHeader(CharSequence header) {
		this.logSubjectHeader.set(Objects.requireNonNull(header).toString());
	}
	
	/**
	 * Log-Subject-Header getter
	 * 
	 * @return log-subject-header
	 */
	public ReadOnlyProperty<String> logSubjectHeader() {
		return logSubjectHeader;
	}
	
}
