package com.shimizukenta.secs;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

import com.shimizukenta.secs.gem.GemConfig;

public abstract class AbstractSecsCommunicatorConfig implements Serializable {
	
	private static final long serialVersionUID = 5944298569476814051L;
	
	private final SecsTimeout timeout = new SecsTimeout();
	
	private int deviceId;
	private boolean isEquip;
	private GemConfig gem;
	
	private String logSubjectHeader;
	
	public AbstractSecsCommunicatorConfig() {
		deviceId = 10;
		isEquip = false;
		gem = new GemConfig();
		logSubjectHeader = "";
	}
	
	public void deviceId(int id) {
		synchronized ( this ) {
			this.deviceId = id;
		}
	}
	
	public int deviceId() {
		synchronized ( this ) {
			return deviceId;
		}
	}
	
	public void isEquip(boolean f) {
		synchronized ( this ) {
			this.isEquip = f;
		}
	}
	
	public boolean isEquip() {
		synchronized ( this ) {
			return isEquip;
		}
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
	public void logSubectHeader(CharSequence header) {
		synchronized ( this ) {
			this.logSubjectHeader = Objects.requireNonNull(header).toString();
		}
	}
	
	public Optional<String> logSubjectHeader() {
		synchronized ( this ) {
			return logSubjectHeader.isEmpty() ? Optional.empty() : Optional.of(logSubjectHeader);
		}
	}
	
}
