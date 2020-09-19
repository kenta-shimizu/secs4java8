package com.shimizukenta.secs;

import java.util.Objects;

/**
 * Number value Getter, Setter, Value-Change-Observer<br />
 * Not accept null.
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractNumberProperty extends AbstractProperty<Number>
		implements ReadOnlyNumberProperty, WritableNumberValue {
	
	private static final long serialVersionUID = 7735781891914639639L;
	
	public AbstractNumberProperty(Number initial) {
		super(Objects.requireNonNull(initial));
	}
	
	public AbstractNumberProperty(int initial) {
		super(Integer.valueOf(initial));
	}
	
	public AbstractNumberProperty(long initial) {
		super(Long.valueOf(initial));
	}
	
	public AbstractNumberProperty(float initial) {
		super(Float.valueOf(initial));
	}
	
	public AbstractNumberProperty(double initial) {
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
