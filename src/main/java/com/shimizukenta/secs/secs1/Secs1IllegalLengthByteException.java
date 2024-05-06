package com.shimizukenta.secs.secs1;

/**
 * Secs1IllegalLengthByteException.
 * 
 * @author kenta-shimizu
 *
 */
public class Secs1IllegalLengthByteException extends Secs1Exception {
	
	private static final long serialVersionUID = -6680007447463657332L;
	
	/**
	 * Constructor.
	 * 
	 * @param length the length byte size
	 */
	public Secs1IllegalLengthByteException(int length) {
		super("length=" + length);
	}
	
}
