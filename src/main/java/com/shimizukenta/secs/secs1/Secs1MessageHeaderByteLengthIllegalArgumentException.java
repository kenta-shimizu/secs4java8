package com.shimizukenta.secs.secs1;

/**
 * SECS-I-Message Header-10-bytes length is NOT equals 10 IllegalArgumentException.
 * 
 * @author kenta-shimizu
 *
 */
public class Secs1MessageHeaderByteLengthIllegalArgumentException extends IllegalArgumentException {
	
	private static final long serialVersionUID = 6647997872983860528L;
	
	/**
	 * Constructor.
	 * 
	 */
	public Secs1MessageHeaderByteLengthIllegalArgumentException() {
		super();
	}
	
}
