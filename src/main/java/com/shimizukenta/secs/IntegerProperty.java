package com.shimizukenta.secs;

public class IntegerProperty extends AbstractProperty<Integer> {

	public IntegerProperty(int initial) {
		super(Integer.valueOf(initial));
	}
	
	public int intValue() {
		synchronized ( this ) {
			return get().intValue();
		}
	}

}
