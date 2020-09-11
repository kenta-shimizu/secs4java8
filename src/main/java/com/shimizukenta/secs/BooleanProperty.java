package com.shimizukenta.secs;

public class BooleanProperty extends AbstractProperty<Boolean> {

	public BooleanProperty(Boolean initial) {
		super(Boolean.valueOf(initial));
	}
	
	public boolean booleanValue() {
		synchronized ( this ) {
			return get().booleanValue();
		}
	}
}
