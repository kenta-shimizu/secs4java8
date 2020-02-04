package com.shimizukenta.secs.gem;

import com.shimizukenta.secs.secs2.AbstractSecs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

public enum ACKC7 {
	
	UNDEFINED((byte)0xFF),
	
	Accepted((byte)0x0),
	PermissionNotGranted((byte)0x1),
	LengthError((byte)0x2),
	MatrixOverflow((byte)0x3),
	PpidNotFound((byte)0x4),
	UnsupportedMode((byte)0x5),
	OtherError((byte)0x6),
	
	;
	
	private final byte code;
	private final AbstractSecs2 ss;
	
	private ACKC7(byte b) {
		this.code = b;
		this.ss = AbstractSecs2.binary(b);
	}
	
	public byte code() {
		return code;
	}
	
	public AbstractSecs2 secs2() {
		return ss;
	}
	
	public static ACKC7 get(byte b) {
		
		for ( ACKC7 v : values() ) {
			if ( v == UNDEFINED ) continue;
			if ( v.code == b ) {
				return v;
			}
		}
		
		return UNDEFINED;
	}
	
	public static ACKC7 get(AbstractSecs2 value) throws Secs2Exception {
		byte b = value.getByte(0);
		return get(b);
	}
}
