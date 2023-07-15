package com.shimizukenta.secs.gem;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

/**
 * ACKC5.
 * 
 * @author kenta-shimizu
 *
 */
public enum ACKC5 {
	
	/**
	 * UNDEFINED.
	 * 
	 */
	UNDEFINED((byte)0xFF),
	
	/**
	 * OK.
	 * 
	 */
	OK((byte)0x0),
	
	;
	
	private final byte code;
	private final Secs2 ss;
	
	private ACKC5(byte b) {
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
	 * Returns Secs2 of {@code <B[1] b>}.
	 * 
	 * @return Secs2 of {@code <B[1] b>}
	 */
	public Secs2 secs2() {
		return ss;
	}
	
	/**
	 * Returns ACKC5 from byte code.
	 * 
	 * @param b byte code
	 * @return ACKC5
	 */
	public static ACKC5 get(byte b) {
		
		for ( ACKC5 v : values() ) {
			if ( v == UNDEFINED ) continue;
			if ( v.code == b ) {
				return v;
			}
		}
		
		return UNDEFINED;
	}
	
	/**
	 * Returns ACKC5 from Secs2.
	 * 
	 * @param value the Secs2
	 * @return ACKC5
	 * @throws Secs2Exception if parse failed
	 */
	public static ACKC5 get(Secs2 value) throws Secs2Exception {
		byte b = value.getByte(0);
		return get(b);
	}
}
