package com.shimizukenta.secs.gem;

import com.shimizukenta.secs.secs2.AbstractSecs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

public enum GRANT {
	
	UNDEFINED((byte)0xFF),
	
	OK((byte)0x0),
	BUSY((byte)0x1),
	InsufficientSpace((byte)0x2),
	DuplicateDataId((byte)0x3),
	
	;
	
	private final byte code;
	private final AbstractSecs2 ss;
	
	private GRANT(byte b) {
		this.code = b;
		this.ss = AbstractSecs2.binary(b);
	}
	
	public byte code() {
		return code;
	}
	
	public AbstractSecs2 secs2() {
		return ss;
	}
	
	public static GRANT get(byte b) {
		
		for ( GRANT v : values() ) {
			if ( v == UNDEFINED ) continue;
			if ( v.code == b ) {
				return v;
			}
		}
		
		return UNDEFINED;
	}
	
	public static GRANT get(AbstractSecs2 value) throws Secs2Exception {
		byte b = value.getByte(0);
		return get(b);
	}
}
