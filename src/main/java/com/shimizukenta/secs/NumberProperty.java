package com.shimizukenta.secs;

import java.util.Objects;

/**
 * Number value Getter, Setter, Value-Change-Observer<br />
 * Not accept null.
 * 
 * @author kenta-shimizu
 *
 */
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
	
	/**
	 * setter<br />
	 * Not Accept null.
	 * 
	 */
	@Override
	public void set(Number v) {
		synchronized ( this ) {
			super.set(Objects.requireNonNull(v));
		}
	}
	
	/**
	 * int setter
	 * 
	 * @param v
	 */
	public void set(int v) {
		synchronized ( this ) {
			set(Integer.valueOf(v));
		}
	}
	
	/**
	 * long setter
	 * 
	 * @param v
	 */
	public void set(long v) {
		synchronized ( this ) {
			set(Long.valueOf(v));
		}
	}
	
	/**
	 * float setter
	 * 
	 * @param v
	 */
	public void set(float v) {
		synchronized ( this ) {
			set(Float.valueOf(v));
		}
	}
	
	/**
	 * double setter
	 * 
	 * @param v
	 */
	public void set(double v) {
		synchronized ( this ) {
			set(Double.valueOf(v));
		}
	}
	
	/**
	 * int getter
	 * 
	 * @return int-value from get().intValue()
	 */
	public int intValue() {
		synchronized ( this ) {
			return get().intValue();
		}
	}
	
	/**
	 * long getter
	 * 
	 * @return int-value from get().longValue()
	 */
	public long longValue() {
		synchronized ( this ) {
			return get().longValue();
		}
	}
	
	/**
	 * float getter
	 * 
	 * @return int-value from get().floatValue()
	 */
	public float floatValue() {
		synchronized ( this ) {
			return get().floatValue();
		}
	}
	
	/**
	 * double getter
	 * 
	 * @return int-value from get().doubleValue()
	 */
	public double doubleValue() {
		synchronized ( this ) {
			return get().doubleValue();
		}
	}
	
}
