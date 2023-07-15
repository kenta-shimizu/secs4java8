package com.shimizukenta.secs.gem;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

/**
 * ERACK.
 * 
 * @author kenta-shimizu
 *
 */
public enum ERACK {
	
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
	
	private ERACK(byte b) {
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
	 * Returns ERACK from byte code.
	 * 
	 * @param b byte code
	 * @return ERACK
	 */
	public static ERACK get(byte b) {
		
		for ( ERACK v : values() ) {
			if ( v == UNDEFINED ) continue;
			if ( v.code == b ) {
				return v;
			}
		}
		
		return UNDEFINED;
	}
	
	/**
	 * Returns ERACK from from Secs2.
	 * 
	 * @param value the Secs2
	 * @return ERACK
	 * @throws Secs2Exception if parse failed
	 */
	public static ERACK get(Secs2 value) throws Secs2Exception {
		byte b = value.getByte(0);
		return get(b);
	}
}
