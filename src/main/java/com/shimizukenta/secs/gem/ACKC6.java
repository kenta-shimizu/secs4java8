package com.shimizukenta.secs.gem;

import com.shimizukenta.secs.secs2.AbstractSecs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

public enum ACKC6 {
	
	UNDEFINED((byte)0xFF),
	
	OK((byte)0x0),
	
	;
	
	private final byte code;
	private final AbstractSecs2 ss;
	
	private ACKC6(byte b) {
		this.code = b;
		this.ss = AbstractSecs2.binary(b);
	}
	
	public byte code() {
		return code;
	}
	
	public AbstractSecs2 secs2() {
		return ss;
	}
	
	public static ACKC6 get(byte b) {
		
		for ( ACKC6 v : values() ) {
			if ( v == UNDEFINED ) continue;
			if ( v.code == b ) {
				return v;
			}
		}
		
		return UNDEFINED;
	}
	
	public static ACKC6 get(AbstractSecs2 value) throws Secs2Exception {
		byte b = value.getByte(0);
		return get(b);
	}
}
