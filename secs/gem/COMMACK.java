package secs.gem;

import secs.secs2.Secs2;
import secs.secs2.Secs2Exception;

public enum COMMACK {

	UNDEFINED((byte)0xFF),
	
	OK((byte)0x0),
	DENIED((byte)0x1),
	
	;
	
	private final byte code;
	
	private COMMACK(byte b) {
		this.code = b;
	}
	
	public byte code() {
		return code;
	}
	
	public static COMMACK get(byte b) {
		
		for ( COMMACK v : values() ) {
			if ( v == UNDEFINED ) continue;
			if ( v.code == b ) {
				return v;
			}
		}
		
		return UNDEFINED;
	}
	
	public static COMMACK get(Secs2 s1f14secs2) throws Secs2Exception {
		byte b = s1f14secs2.getByte(0, 0);
		return get(b);
	}
}
