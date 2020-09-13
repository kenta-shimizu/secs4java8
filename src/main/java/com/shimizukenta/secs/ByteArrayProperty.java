package com.shimizukenta.secs;

import java.util.Objects;

public class ByteArrayProperty extends AbstractProperty<byte[]> {
	
	private static final long serialVersionUID = -73558689082039418L;
	
	public ByteArrayProperty(byte[] initial) {
		super(Objects.requireNonNull(initial));
	}
	
	@Override
	public void set(byte[] v) {
		super.set(Objects.requireNonNull(v));
	}
	
	public byte get(int index) {
		synchronized ( this ) {
			return (get())[index];
		}
	}

}
