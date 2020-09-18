package com.shimizukenta.secs;

import java.util.Objects;

/**
 * Bytes Array value Getter, Setter, Value-Change-Observer<br />
 * Not accept null.
 * 
 * @author kenta-shimizu
 *
 */
public class ByteArrayProperty extends AbstractProperty<byte[]> {
	
	private static final long serialVersionUID = -73558689082039418L;
	
	public ByteArrayProperty(byte[] initial) {
		super(Objects.requireNonNull(initial));
	}
	
	/**
	 * setter<br />
	 * Not accept null.
	 * 
	 */
	@Override
	public void set(byte[] v) {
		super.set(Objects.requireNonNull(v));
	}
	
	/**
	 * byte value getter
	 * 
	 * @param index
	 * @throws IndexOutOfBoundsException
	 * @return byte-value from (get())[index]
	 */
	public byte get(int index) {
		synchronized ( this ) {
			return (get())[index];
		}
	}

}
