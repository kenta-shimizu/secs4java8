package com.shimizukenta.secs.gem;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

/**
 * ONLACK.
 * 
 * @author kenta-shimizu
 *
 */
public enum ONLACK {
	
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
	 * Retused.
	 * 
	 */
	Refused((byte)0x1),
	
	/**
	 * AlreadyOnline.
	 * 
	 */
	AlreadyOnline((byte)0x2),
	
	;
	
	private final byte code;
	private final Secs2 ss;
	
	private ONLACK(byte b) {
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
	 * Returns ONLACK from byte code.
	 * 
	 * @param b byte code
	 * @return ONLACK
	 */
	public static ONLACK get(byte b) {
		
		for ( ONLACK v : values() ) {
			if (v == UNDEFINED) continue;
			if (v.code == b) {
				return v;
			}
		}
		
		return UNDEFINED;
	}
	
	/**
	 * Returns ONLACK from Secs2.
	 * 
	 * @param value the Secs2
	 * @return ONLACK
	 * @throws Secs2Exception if parse failed
	 */
	public static ONLACK get(Secs2 value) throws Secs2Exception {
		byte b = value.getByte(0);
		return get(b);
	}
}
