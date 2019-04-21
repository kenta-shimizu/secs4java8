package secs.gem;

import secs.secs2.Secs2;
import secs.secs2.Secs2Exception;

public enum OFLACK {

	UNDEFINED((byte)0xFF),
	
	OK((byte)0x0),
	
	;
	
	private final byte code;
	
	private OFLACK(byte b) {
		this.code = b;
	}
	
	public byte code() {
		return code;
	}
	
	public static OFLACK get(byte b) {
		
		for ( OFLACK v : values() ) {
			if (v == UNDEFINED) continue;
			if (v.code == b) {
				return v;
			}
		}
		
		return UNDEFINED;
	}
	
	public static OFLACK get(Secs2 value) throws Secs2Exception {
		byte b = value.getByte(0);
		return get(b);
	}
	
}
