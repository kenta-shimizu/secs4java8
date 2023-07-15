package com.shimizukenta.secs.gem;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

/**
 * CMDA.
 * 
 * @author kenta-shimizu
 *
 */
public enum CMDA {
	
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
	 * CommandDoesNotExist.
	 * 
	 */
	CommandDoesNotExist((byte)0x1),
	
	/**
	 * NotNow.
	 * 
	 */
	NotNow((byte)0x2),
	
	;
	
	private final byte code;
	private final Secs2 ss;
	
	private CMDA(byte b) {
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
	 * Returns CMDA from byte code.
	 * 
	 * @param b byte code
	 * @return CMDA
	 */
	public static CMDA get(byte b) {
		
		for ( CMDA v : values() ) {
			if ( v == UNDEFINED ) continue;
			if ( v.code == b ) {
				return v;
			}
		}
		
		return UNDEFINED;
	}
	
	/**
	 * Returns CMDA from Secs2.
	 * 
	 * @param value the Secs2
	 * @return CMDA
	 * @throws Secs2Exception if parse failed
	 */
	public static CMDA get(Secs2 value) throws Secs2Exception {
		byte b = value.getByte(0);
		return get(b);
	}
}
