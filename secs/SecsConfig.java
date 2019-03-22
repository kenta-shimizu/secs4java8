package secs;

public class SecsConfig {
	
	private final SecsTimeout timeout = new SecsTimeout();
	
	private int deviceId;
	private boolean isEquip;
	
	public SecsConfig() {
		deviceId = 10;
		isEquip = false;
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
	
}
