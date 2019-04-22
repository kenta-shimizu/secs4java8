package secs.gem;

import secs.secs2.Secs2;
import secs.secs2.Secs2Exception;

public enum GRANT {
	
	UNDEFINED((byte)0xFF),
	
	OK((byte)0x0),
	BUSY((byte)0x1),
	INsufficientSpace((byte)0x2),
	DuplicateDataId((byte)0x3),
	
	;
	
	private final byte code;
	private final Secs2 ss;
	
	private GRANT(byte b) {
		this.code = b;
		this.ss = Secs2.binary(b);
	}
	
	public byte code() {
		return code;
	}
	
	public Secs2 secs2() {
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
	
	public static GRANT get(Secs2 value) throws Secs2Exception {
		byte b = value.getByte(0);
		return get(b);
	}
}
