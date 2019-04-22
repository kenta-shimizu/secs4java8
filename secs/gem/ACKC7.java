package secs.gem;

import secs.secs2.Secs2;
import secs.secs2.Secs2Exception;

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
	private final Secs2 ss;
	
	private ACKC7(byte b) {
		this.code = b;
		this.ss = Secs2.binary(b);
	}
	
	public byte code() {
		return code;
	}
	
	public Secs2 secs2() {
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
	
	public static ACKC7 get(Secs2 value) throws Secs2Exception {
		byte b = value.getByte(0);
		return get(b);
	}
}
