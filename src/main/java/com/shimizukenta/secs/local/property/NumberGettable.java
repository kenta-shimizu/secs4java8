package com.shimizukenta.secs.local.property;

/**
 * Number value Getter.
 * 
 * @author kenta-shimizu
 *
 * @param <T> Type
 * @see Number
 * @see Gettable
 * 
 */
public interface NumberGettable<T extends Number> extends Gettable<T> {
	
	/**
	 * Returns the value of the specified number as a byte.
	 * 
	 * <p>This implementation returns Number#byteValue().</p>
	 * 
	 * @return the numeric value represented by this object after conversion to type byte.
	 * @see Number#byteValue()
	 */
	public byte byteValue();
	
	/**
	 * Returns the value of the specified number as a short.
	 * 
	 * <p>This implementation returns Number#shortValue().</p>
	 * 
	 * @return the numeric value represented by this object after conversion to type short.
	 * @see Number#shortValue()
	 */
	public short shortValue();
	
	/**
	 * Returns the value of the specified number as a int.
	 * 
	 * <p>This implementation returns Number#intValue().</p>
	 * 
	 * @return the numeric value represented by this object after conversion to type int.
	 * @see Number#intValue()
	 */
	public int intValue();
	
	/**
	 * Returns the value of the specified number as a long.
	 * 
	 * <p>This implementation returns Number#longValue().</p>
	 * 
	 * @return the numeric value represented by this object after conversion to type long.
	 * @see Number#longValue()
	 */
	public long longValue();
	
	/**
	 * Returns the value of the specified number as a float.
	 * 
	 * <p>This implementation returns Number#floatValue().</p>
	 * 
	 * @return the numeric value represented by this object after conversion to type float.
	 * @see Number#floatValue()
	 */
	public float floatValue();
	
	/**
	 * Returns the value of the specified number as a double.
	 * 
	 * <p>This implementation returns Number#doubleValue().</p>
	 * 
	 * @return the numeric value represented by this object after conversion to type double.
	 * @see Number#doubleValue()
	 */
	public double doubleValue();
	
}
