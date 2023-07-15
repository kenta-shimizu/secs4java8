package com.shimizukenta.secs.gem;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

/**
 * GRANT6.
 * 
 * @author kenta-shimizu
 *
 */
public enum GRANT6 {
	
	/**
	 * UNDEFINED>
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
	 * NotInterested.
	 * 
	 */
	NotInterested((byte)0x2),

	;
	
	private final byte code;
	private final Secs2 ss;
	
	private GRANT6(byte b) {
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
	 * Returns GRANT6 from byte code.
	 * 
	 * @param b byte code
	 * @return GRANT6
	 */
	public static GRANT6 get(byte b) {
		
		for ( GRANT6 v : values() ) {
			if ( v == UNDEFINED ) continue;
			if ( v.code == b ) {
				return v;
			}
		}
		
		return UNDEFINED;
	}
	
	/**
	 * Returns GRANT6 from Secs2.
	 * 
	 * @param value the Secs2
	 * @return GRANT6
	 * @throws Secs2Exception if parse failed
	 */
	public static GRANT6 get(Secs2 value) throws Secs2Exception {
		byte b = value.getByte(0);
		return get(b);
	}
}
