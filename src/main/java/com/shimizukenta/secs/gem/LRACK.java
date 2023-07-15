package com.shimizukenta.secs.gem;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

/**
 * LRACK.
 * 
 * @author kenta-shimizu
 *
 */
public enum LRACK {
	
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
	 * OneOrMoreCeidLinksAlreadyDefined.
	 * 
	 */
	OneOrMoreCeidLinksAlreadyDefined((byte)0x3),
	
	/**
	 * OneOrMoreCeidInvalid.
	 * 
	 */
	OneOrMoreCeidInvalid((byte)0x4),
	
	/**
	 * OneOrMoreRptidInvalid.
	 * 
	 */
	OneOrMoreRptidInvalid((byte)0x5),
	
	;
	
	private final byte code;
	private final Secs2 ss;
	
	private LRACK(byte b) {
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
	 * Returns LRACK from byte code.
	 * 
	 * @param b byte code
	 * @return LRACK
	 */
	public static LRACK get(byte b) {
		
		for ( LRACK v : values() ) {
			if ( v == UNDEFINED ) continue;
			if ( v.code == b ) {
				return v;
			}
		}
		
		return UNDEFINED;
	}
	
	/**
	 * Returns LRACK from Secs2.
	 * 
	 * @param value the Secs2
	 * @return LRACK
	 * @throws Secs2Exception if parse failed
	 */
	public static LRACK get(Secs2 value) throws Secs2Exception {
		byte b = value.getByte(0);
		return get(b);
	}
}
