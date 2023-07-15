package com.shimizukenta.secs.gem;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

/**
 * GRANT.
 * 
 * @author kenta-shimizu
 *
 */
public enum GRANT {
	
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
	 * BUSY.
	 * 
	 */
	BUSY((byte)0x1),
	
	/**
	 * InsufficientSpace.
	 * 
	 */
	InsufficientSpace((byte)0x2),
	
	/**
	 * DuplicateDataId.
	 * 
	 */
	DuplicateDataId((byte)0x3),
	
	;
	
	private final byte code;
	private final Secs2 ss;
	
	private GRANT(byte b) {
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
	 * Returns GRANT from byte code.
	 * 
	 * @param b byte code
	 * @return GRANT
	 */
	public static GRANT get(byte b) {
		
		for ( GRANT v : values() ) {
			if ( v == UNDEFINED ) continue;
			if ( v.code == b ) {
				return v;
			}
		}
		
		return UNDEFINED;
	}
	
	/**
	 * Returns GRANT from Secs2.
	 * 
	 * @param value the Secs2
	 * @return GRANT
	 * @throws Secs2Exception if parse failed
	 */
	public static GRANT get(Secs2 value) throws Secs2Exception {
		byte b = value.getByte(0);
		return get(b);
	}
}
