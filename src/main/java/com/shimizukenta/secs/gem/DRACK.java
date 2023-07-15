package com.shimizukenta.secs.gem;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

/**
 * DRACK.
 * 
 * @author kenta-shimizu
 *
 */
public enum DRACK {
	
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
	 * OutOfSpace.
	 * 
	 */
	OutOfSpace((byte)0x1),
	
	/**
	 * InvalidFormat.
	 * 
	 */
	InvalidFormat((byte)0x2),
	
	/**
	 * OneOrMoreRptidAlreadyDefined.
	 * 
	 */
	OneOrMoreRptidAlreadyDefined((byte)0x3),
	
	/**
	 * OneOrMoreInvalidVid.
	 * 
	 */
	OneOrMoreInvalidVid((byte)0x4),
	
	;
	
	private final byte code;
	private final Secs2 ss;
	
	private DRACK(byte b) {
		this.code = b;
		this.ss = Secs2.binary(b);
	}
	
	/**
	 * Returns byte cdde.
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
	 * Returns DRACK from byte code.
	 * 
	 * @param b byte code
	 * @return DRACK
	 */
	public static DRACK get(byte b) {
		
		for ( DRACK v : values() ) {
			if ( v == UNDEFINED ) continue;
			if ( v.code == b ) {
				return v;
			}
		}
		
		return UNDEFINED;
	}
	
	/**
	 * Returns DRACK from Secs2.
	 * 
	 * @param value the Secs2
	 * @return DRACK
	 * @throws Secs2Exception if parse failed
	 */
	public static DRACK get(Secs2 value) throws Secs2Exception {
		byte b = value.getByte(0);
		return get(b);
	}
}
