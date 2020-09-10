package com.shimizukenta.secs;

import java.io.Serializable;
import java.util.Objects;

import com.shimizukenta.secs.gem.GemConfig;

public abstract class AbstractSecsCommunicatorConfig implements Serializable {
	
	private static final long serialVersionUID = -8456991094606676409L;

	private class IntegerProperty extends AbstractProperty<Integer> {

		public IntegerProperty(int initial) {
			super(Integer.valueOf(initial));
		}
	}
	
	private class BooleanProperty extends AbstractProperty<Boolean> {

		public BooleanProperty(Boolean initial) {
			super(Boolean.valueOf(initial));
		}
	}
	
	private class StringProperty extends AbstractProperty<String> {

		public StringProperty(String initial) {
			super(initial);
		}
	}
	
	
	private final SecsTimeout timeout = new SecsTimeout();
	private final Property<Integer> deviceId = new IntegerProperty(10);
	private final Property<Boolean> isEquip = new BooleanProperty(false);
	private GemConfig gem;
	private final Property<String> logSubjectHeader = new StringProperty("");
	
	public AbstractSecsCommunicatorConfig() {
		gem = new GemConfig();
	}
	
	public void deviceId(int id) {
		this.deviceId.set(id);
	}
	
	public Property<Integer> deviceId() {
		return deviceId;
	}
	
	public void isEquip(boolean f) {
		this.isEquip.set(f);
	}
	
	public Property<Boolean> isEquip() {
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
	
//	public Optional<String> logSubjectHeader() {
//		synchronized ( this ) {
//			return logSubjectHeader.isEmpty() ? Optional.empty() : Optional.of(logSubjectHeader);
//		}
//	}
	
	public Property<String> logSubjectHeader() {
		return logSubjectHeader;
	}
	
}
