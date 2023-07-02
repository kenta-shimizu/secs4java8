package com.shimizukenta.secs.local.property;

/**
 * Boolean value Getter.
 * 
 * @author kenta-shimizu
 * @see Boolean
 * @see Gettable
 */
public interface BooleanGettable extends Gettable<Boolean> {
	
	/**
	 * Returns the value of this Boolean object as a boolean primitive.
	 * 
	 * @return the primitive boolean value of this object.
	 * @see Boolean#booleanValue()
	 */
	public boolean booleanValue();
	
}
