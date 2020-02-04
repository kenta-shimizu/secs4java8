package com.shimizukenta.secs.gem;

import com.shimizukenta.secs.secs2.AbstractSecs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

public enum CMDA {
	
	UNDEFINED((byte)0xFF),
	
	OK((byte)0x0),
	CommandDoesNotExist((byte)0x1),
	NotNow((byte)0x2),
	
	;
	
	private final byte code;
	private final AbstractSecs2 ss;
	
	private CMDA(byte b) {
		this.code = b;
		this.ss = AbstractSecs2.binary(b);
	}
	
	public byte code() {
		return code;
	}
	
	public AbstractSecs2 secs2() {
		return ss;
	}
	
	public static CMDA get(byte b) {
		
		for ( CMDA v : values() ) {
			if ( v == UNDEFINED ) continue;
			if ( v.code == b ) {
				return v;
			}
		}
		
		return UNDEFINED;
	}
	
	public static CMDA get(AbstractSecs2 value) throws Secs2Exception {
		byte b = value.getByte(0);
		return get(b);
	}
}
