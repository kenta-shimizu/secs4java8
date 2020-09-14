package com.shimizukenta.secs;

import java.io.Serializable;
import java.util.Objects;

import com.shimizukenta.secs.gem.GemConfig;

public abstract class AbstractSecsCommunicatorConfig implements Serializable {
	
	private static final long serialVersionUID = -8456991094606676409L;
	
	private final SecsTimeout timeout = new SecsTimeout();
	private final IntegerProperty deviceId = new IntegerProperty(10);
	private final BooleanProperty isEquip = new BooleanProperty(false);
	private GemConfig gem;
	
	private final StringProperty logSubjectHeader = new StringProperty("");
	
	public AbstractSecsCommunicatorConfig() {
		gem = new GemConfig();
	}
	
	public void deviceId(int id) {
		this.deviceId.set(id);
	}
	
	public IntegerProperty deviceId() {
		return deviceId;
	}
	
	public void isEquip(boolean f) {
		this.isEquip.set(f);
	}
	
	public BooleanProperty isEquip() {
		return isEquip;
	}
	
	public SecsTimeout timeout() {
		return timeout;
	}
	
	public GemConfig gem() {
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
	
	public StringProperty logSubjectHeader() {
		return logSubjectHeader;
	}
	
}
