package com.shimizukenta.secs;

/**
 * Byte Array value Getter, Setter, Value-Change-Observer
 * 
 * <p>
 * Not accept null.
 * </p>
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
