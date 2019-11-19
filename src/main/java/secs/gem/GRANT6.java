package secs.gem;

import secs.secs2.Secs2;
import secs.secs2.Secs2Exception;

public enum GRANT6 {
	
	UNDEFINED((byte)0xFF),
	
	OK((byte)0x0),
	BUSY((byte)0x1),
	NotInterested((byte)0x2),

	;
	
	private final byte code;
	private final Secs2 ss;
	
	private GRANT6(byte b) {
		this.code = b;
		this.ss = Secs2.binary(b);
	}
	
	public byte code() {
		return code;
	}
	
	public Secs2 secs2() {
		return ss;
	}
	
	public static GRANT6 get(byte b) {
		
		for ( GRANT6 v : values() ) {
			if ( v == UNDEFINED ) continue;
			if ( v.code == b ) {
				return v;
			}
		}
		
		return UNDEFINED;
	}
	
	public static GRANT6 get(Secs2 value) throws Secs2Exception {
		byte b = value.getByte(0);
		return get(b);
	}
}
