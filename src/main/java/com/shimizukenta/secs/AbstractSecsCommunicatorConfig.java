package com.shimizukenta.secs;

import java.io.Serializable;
import java.util.Objects;

import com.shimizukenta.secs.gem.AbstractGemConfig;
import com.shimizukenta.secs.gem.SimpleGemConfig;

public abstract class AbstractSecsCommunicatorConfig implements Serializable {
	
	private static final long serialVersionUID = -8456991094606676409L;
	
	private final SecsTimeout timeout = new SecsTimeout();
	private final AbstractNumberProperty deviceId = new SimpleNumberProperty(10);
	private final AbstractBooleanProperty isEquip = new SimpleBooleanProperty(false);
	
	private final AbstractGemConfig gem = new SimpleGemConfig();
	
	private final AbstractStringProperty logSubjectHeader = new SimpleStringProperty("");
	
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
