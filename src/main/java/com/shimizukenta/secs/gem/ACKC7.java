package com.shimizukenta.secs.gem;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

/**
 * ACKC7.
 * 
 * @author kenta-shimizu
 *
 */
public enum ACKC7 {
	
	/**
	 * UNDEFINED.
	 * 
	 */
	UNDEFINED((byte)0xFF),
	
	/**
	 * Accepted.
	 * 
	 */
	Accepted((byte)0x0),
	
	/**
	 * PermissionNotGranted.
	 * 
	 */
	PermissionNotGranted((byte)0x1),
	
	/**
	 * LengthError.
	 * 
	 */
	LengthError((byte)0x2),
	
	/**
	 * MatrixOverflow.
	 * 
	 */
	MatrixOverflow((byte)0x3),
	
	/**
	 * PpidNotFound.
	 * 
	 */
	PpidNotFound((byte)0x4),
	
	/**
	 * UnsupportedMode.
	 * 
	 */
	UnsupportedMode((byte)0x5),
	
	/**
	 * OtherError.
	 */
	OtherError((byte)0x6),
	
	;
	
	private final byte code;
	private final Secs2 ss;
	
	private ACKC7(byte b) {
		this.code = b;
		this.ss = Secs2.binary(b);
	}
	
	/**
	 * Returns byte code.
	 * 
	 * @return byte code
	 */
	public byte code() {
		return code;
	}
	
	/**
	 * Returns Secs2.
	 * 
	 * @return Secs2
	 */
	public Secs2 secs2() {
		return ss;
	}
	
	/**
	 * Returns ACKC7 from byte code.
	 * 
	 * @param b byte code
	 * @return ACKC7
	 */
	public static ACKC7 get(byte b) {
		
		for ( ACKC7 v : values() ) {
			if ( v == UNDEFINED ) continue;
			if ( v.code == b ) {
				return v;
			}
		}
		
		return UNDEFINED;
	}
	
	/**
	 * Returns ACKC7 from Secs2.
	 * 
	 * @param value the Secs2
	 * @return ACKC7
	 * @throws Secs2Exception if parse failed
	 */
	public static ACKC7 get(Secs2 value) throws Secs2Exception {
		byte b = value.getByte(0);
		return get(b);
	}
}
