package com.shimizukenta.secs;

public class IntegerProperty extends AbstractProperty<Integer> {
	
	private static final long serialVersionUID = 4334577505200956328L;
	
	public IntegerProperty(int initial) {
		super(Integer.valueOf(initial));
	}
	
	public int intValue() {
		synchronized ( this ) {
			return get().intValue();
		}
	}

}
