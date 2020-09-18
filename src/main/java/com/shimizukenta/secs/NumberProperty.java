package com.shimizukenta.secs;

import java.util.Objects;

public class NumberProperty extends AbstractProperty<Number> {
	
	private static final long serialVersionUID = 4334577505200956328L;
	
	public NumberProperty(Number initial) {
		super(Objects.requireNonNull(initial));
	}
	
	public NumberProperty(int initial) {
		super(Integer.valueOf(initial));
	}
	
	public NumberProperty(long initial) {
		super(Long.valueOf(initial));
	}
	
	public NumberProperty(float initial) {
		super(Float.valueOf(initial));
	}
	
	public NumberProperty(double initial) {
		super(Double.valueOf(initial));
	}
	
	@Override
	public void set(Number v) {
		synchronized ( this ) {
			super.set(Objects.requireNonNull(v));
		}
	}
	
	public void set(int v) {
		synchronized ( this ) {
			set(Integer.valueOf(v));
		}
	}
	
	public void set(long v) {
		synchronized ( this ) {
			set(Long.valueOf(v));
		}
	}
	
	public void set(float v) {
		synchronized ( this ) {
			set(Float.valueOf(v));
		}
	}
	
	public void set(double v) {
		synchronized ( this ) {
			set(Double.valueOf(v));
		}
	}
	
	public int intValue() {
		synchronized ( this ) {
			return get().intValue();
		}
	}
	
	public long longValue() {
		synchronized ( this ) {
			return get().longValue();
		}
	}
	
	public float floatValue() {
		synchronized ( this ) {
			return get().floatValue();
		}
	}
	
	public double doubleValue() {
		synchronized ( this ) {
			return get().doubleValue();
		}
	}
}
