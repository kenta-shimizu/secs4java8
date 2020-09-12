package com.shimizukenta.secs;

public class BooleanProperty extends AbstractProperty<Boolean> {
	
	private static final long serialVersionUID = 3179529862036582480L;
	
	public BooleanProperty(Boolean initial) {
		super(Boolean.valueOf(initial));
	}
	
	public boolean booleanValue() {
		return get().booleanValue();
	}
	
	public void waitUntilTrue() throws InterruptedException {
		waitUntil(Boolean.TRUE);
	}
	
	public void waitUntilFalse() throws InterruptedException {
		waitUntil(Boolean.FALSE);
	}
}
