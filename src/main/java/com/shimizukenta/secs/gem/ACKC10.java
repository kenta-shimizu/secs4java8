package com.shimizukenta.secs.gem;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

/**
 * ACKC10
 * 
 * @author kenta-shimizu
 *
 */
public enum ACKC10 {
	
	/**
	 * UNDEFINED.
	 * 
	 */
	UNDEFINED((byte)0xFF),
	
	/**
	 * AcceptedForDisplay.
	 * 
	 */
	AcceptedForDisplay((byte)0x0),
	
	/**
	 * MessageWillNotBeDisplayed.
	 * 
	 */
	MessageWillNotBeDisplayed((byte)0x1),
	
	/**
	 * TerminalNotAvailable.
	 * 
	 */
	TerminalNotAvailable((byte)0x2),
	
	;
	
	private final byte code;
	private final Secs2 ss;
	
	private ACKC10(byte b) {
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
	 * Returns Secs2 of {@code <B[1] ACK10>}
	 * 
	 * @return Secs2 of {@code <B[1] ACK10>}
	 */
	public Secs2 secs2() {
		return ss;
	}
	
	/**
	 * Returns ACKC10 from byte code.
	 * 
	 * @param b byte code
	 * @return ACK10 if exist, otherwise ACK10.UNDEFINED
	 */
	public static ACKC10 get(byte b) {
		
		for ( ACKC10 v : values() ) {
			if ( v == UNDEFINED ) continue;
			if ( v.code == b ) {
				return v;
			}
		}
		
		return UNDEFINED;
	}
	
	/**
	 * REturns ACKC10 from Secs2.
	 * 
	 * @param value the Secs2.
	 * @return ACKC10
	 * @throws Secs2Exception if parse failed
	 */
	public static ACKC10 get(Secs2 value) throws Secs2Exception {
		byte b = value.getByte(0);
		return get(b);
	}
}
