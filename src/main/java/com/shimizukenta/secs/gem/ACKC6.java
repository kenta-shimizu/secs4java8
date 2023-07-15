package com.shimizukenta.secs.gem;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

/**
 * ACKC6.
 * 
 * @author kenta-shimizu
 *
 */
public enum ACKC6 {
	
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
	
	private ACKC6(byte b) {
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
	 * Returns ACKC6 from byte code.
	 * 
	 * @param b byte code
	 * @return ACKC6
	 */
	public static ACKC6 get(byte b) {
		
		for ( ACKC6 v : values() ) {
			if ( v == UNDEFINED ) continue;
			if ( v.code == b ) {
				return v;
			}
		}
		
		return UNDEFINED;
	}
	
	/**
	 * Returns ACKC6 from Secs2.
	 * 
	 * @param value the Secs2
	 * @return ACKC6
	 * @throws Secs2Exception if parse failed
	 */
	public static ACKC6 get(Secs2 value) throws Secs2Exception {
		byte b = value.getByte(0);
		return get(b);
	}
}
