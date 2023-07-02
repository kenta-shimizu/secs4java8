package com.shimizukenta.secs.local.property;

/**
 * String value getter.
 * 
 * @author shimizukenta
 * @see Gettable
 * @see CharSequence
 * @see Comparable
 *
 */
public interface StringGettable extends Gettable<String>, CharSequence, Comparable<StringGettable> {
	
	@Override
	default public int length() {
		return this.toString().length();
	}

	@Override
	default public char charAt(int index) {
		return this.toString().charAt(index);
	}

	@Override
	default public CharSequence subSequence(int start, int end) {
		return this.toString().subSequence(start, end);
	}
	
	@Override
	default public int compareTo(StringGettable o) {
		return this.toString().compareTo(o.toString());
	}
	
}
