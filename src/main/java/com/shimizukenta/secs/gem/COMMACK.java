package com.shimizukenta.secs.gem;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

/**
 * COMMACK.
 * 
 * @author kenta-shimizu
 *
 */
public enum COMMACK {
	
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
	
	/**
	 * DENIED.
	 * 
	 */
	DENIED((byte)0x1),
	
	;
	
	private final byte code;
	private final Secs2 ss;
	
	private COMMACK(byte b) {
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
	 * Returns COMMACK from byte code.
	 * 
	 * @param b byte code
	 * @return COMMACK
	 */
	public static COMMACK get(byte b) {
		
		for ( COMMACK v : values() ) {
			if ( v == UNDEFINED ) continue;
			if ( v.code == b ) {
				return v;
			}
		}
		
		return UNDEFINED;
	}
	
	/**
	 * Returns COMMACK from Secs2.
	 * 
	 * @param value the Secs2
	 * @return COMMACK
	 * @throws Secs2Exception if parse failed
	 */
	public static COMMACK get(Secs2 value) throws Secs2Exception {
		byte b = value.getByte(0);
		return get(b);
	}
	
}
