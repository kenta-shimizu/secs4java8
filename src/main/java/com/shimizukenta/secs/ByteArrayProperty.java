package com.shimizukenta.secs;

/**
 * Byte Array value Getter, Setter, Value-Change-Observer<br />
 * Not accept null.
 * 
 * @author kenta-shimizu
 *
 */
public interface ByteArrayProperty extends Property<byte[]> {
	
	public static ByteArrayProperty newInstance(byte[] initial) {
		
		return new AbstractByteArrayProperty(initial) {
			private static final long serialVersionUID = -1499639983991564408L;
		};
	}
}
