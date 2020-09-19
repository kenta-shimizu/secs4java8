package com.shimizukenta.secs;

/**
 * Bytes Array value Getter, Setter, Value-Change-Observer<br />
 * Not accept null.
 * 
 * @author kenta-shimizu
 *
 */
public class SimpleByteArrayProperty extends AbstractByteArrayProperty {
	
	private static final long serialVersionUID = -7719597875551235200L;
	
	public SimpleByteArrayProperty(byte[] initial) {
		super(initial);
	}
	
}
