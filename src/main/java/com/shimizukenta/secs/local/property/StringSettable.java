package com.shimizukenta.secs.local.property;

/**
 * String value setter.
 * 
 * @author kenta-shimizu
 * @see Settable
 *
 */
public interface StringSettable extends Settable<String> {
	
	/**
	 * String value setter.
	 * 
	 * <p>
	 * if set value is null, set empty string("").<br />
	 * </p>
	 * 
	 * @param value the CharSequence
	 */
	public void set(CharSequence value);
	
	/**
	 * Empty String setter.
	 * 
	 */
	default public void set() {
		this.set("");
	}
	
}
