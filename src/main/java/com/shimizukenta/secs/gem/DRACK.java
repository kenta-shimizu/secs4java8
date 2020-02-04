package com.shimizukenta.secs.gem;

import com.shimizukenta.secs.secs2.AbstractSecs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

public enum DRACK {
	
	UNDEFINED((byte)0xFF),
	
	OK((byte)0x0),
	OutOfSpace((byte)0x1),
	InvalidFormat((byte)0x2),
	OneOrMoreRptidAlreadyDefined((byte)0x3),
	OneOrMoreInvalidVid((byte)0x4),
	
	;
	
	private final byte code;
	private final AbstractSecs2 ss;
	
	private DRACK(byte b) {
		this.code = b;
		this.ss = AbstractSecs2.binary(b);
	}
	
	public byte code() {
		return code;
	}
	
	public AbstractSecs2 secs2() {
		return ss;
	}
	
	public static DRACK get(byte b) {
		
		for ( DRACK v : values() ) {
			if ( v == UNDEFINED ) continue;
			if ( v.code == b ) {
				return v;
			}
		}
		
		return UNDEFINED;
	}
	
	public static DRACK get(AbstractSecs2 value) throws Secs2Exception {
		byte b = value.getByte(0);
		return get(b);
	}
}
