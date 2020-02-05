package com.shimizukenta.secs.gem;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

public enum ACKC10 {
	
	UNDEFINED((byte)0xFF),
	
	AcceptedForDisplay((byte)0x0),
	MessageWillNotBeDisplayed((byte)0x1),
	TerminalNotAvailable((byte)0x2),
	
	;
	
	private final byte code;
	private final Secs2 ss;
	
	private ACKC10(byte b) {
		this.code = b;
		this.ss = Secs2.binary(b);
	}
	
	public byte code() {
		return code;
	}
	
	public Secs2 secs2() {
		return ss;
	}
	
	public static ACKC10 get(byte b) {
		
		for ( ACKC10 v : values() ) {
			if ( v == UNDEFINED ) continue;
			if ( v.code == b ) {
				return v;
			}
		}
		
		return UNDEFINED;
	}
	
	public static ACKC10 get(Secs2 value) throws Secs2Exception {
		byte b = value.getByte(0);
		return get(b);
	}
}
