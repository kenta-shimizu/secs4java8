package com.shimizukenta.secs.gem;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

/**
 * OFLACK.
 * 
 * @author kenta-shimizu
 *
 */
public enum OFLACK {
	
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
	
	private OFLACK(byte b) {
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
	 * Returns Secs2
	 * 
	 * @return Secs2
	 */
	public Secs2 secs2() {
		return ss;
	}
	
	/**
	 * Returns OFLACK from byte code.
	 * 
	 * @param b byte code.
	 * @return OFLACK
	 */
	public static OFLACK get(byte b) {
		
		for ( OFLACK v : values() ) {
			if (v == UNDEFINED) continue;
			if (v.code == b) {
				return v;
			}
		}
		
		return UNDEFINED;
	}
	
	/**
	 * Returns OFLACK from Secs2.
	 * 
	 * @param value the Secs2
	 * @return OFLACK
	 * @throws Secs2Exception if parse failed
	 */
	public static OFLACK get(Secs2 value) throws Secs2Exception {
		byte b = value.getByte(0);
		return get(b);
	}
	
}
