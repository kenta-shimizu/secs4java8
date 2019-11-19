package secs;

import secs.gem.GemConfig;

public class SecsCommunicatorConfig {
	
	private final SecsTimeout timeout = new SecsTimeout();
	
	private int deviceId;
	private boolean isEquip;
	private GemConfig gem;
	
	public SecsCommunicatorConfig() {
		deviceId = 10;
		isEquip = false;
		gem = new GemConfig();
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
	
}
