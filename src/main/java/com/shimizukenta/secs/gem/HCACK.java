package com.shimizukenta.secs.gem;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

/**
 * HCACK.
 * 
 * @author kenta-shimizu
 *
 */
public enum HCACK {
	
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
	 * InvalidCommand.
	 * 
	 */
	InvalidCommand((byte)0x1),
	
	/**
	 * CannotDoNow.
	 * 
	 */
	CannotDoNow((byte)0x2),
	
	/**
	 * ParameterError.
	 * 
	 */
	ParameterError((byte)0x3),
	
	/**
	 * InitiatedForAsynchronousCompletion.
	 * 
	 */
	InitiatedForAsynchronousCompletion((byte)0x4),
	
	/**
	 * RejectedAlreadyInDesiredCondition.
	 * 
	 */
	RejectedAlreadyInDesiredCondition((byte)0x5),
	
	/**
	 * InvalidObject.
	 * 
	 */
	InvalidObject((byte)0x6),
	
	;
	
	private final byte code;
	private final Secs2 ss;
	
	private HCACK(byte b) {
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
	 * Returns HCACK from byte code.
	 * 
	 * @param b byte code
	 * @return HCACK
	 */
	public static HCACK get(byte b) {
		
		for ( HCACK v : values() ) {
			if ( v == UNDEFINED ) continue;
			if ( v.code == b ) {
				return v;
			}
		}
		
		return UNDEFINED;
	}
	
	/**
	 * Returns HCACK from Secs2.
	 * 
	 * @param value the Secs2
	 * @return HCACK
	 * @throws Secs2Exception if parse failed
	 */
	public static HCACK get(Secs2 value) throws Secs2Exception {
		byte b = value.getByte(0);
		return get(b);
	}
	
}
