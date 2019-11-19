package secs.gem;

import secs.secs2.Secs2;
import secs.secs2.Secs2Exception;

public enum ONLACK {
	
	UNDEFINED((byte)0xFF),
	
	OK((byte)0x0),
	Refused((byte)0x1),
	AlreadyOnline((byte)0x2),
	
	;
	
	private final byte code;
	private final Secs2 ss;
	
	private ONLACK(byte b) {
		this.code = b;
		this.ss = Secs2.binary(b);
	}
	
	public byte code() {
		return code;
	}
	
	public Secs2 secs2() {
		return ss;
	}
	
	public static ONLACK get(byte b) {
		
		for ( ONLACK v : values() ) {
			if (v == UNDEFINED) continue;
			if (v.code == b) {
				return v;
			}
		}
		
		return UNDEFINED;
	}
	
	public static ONLACK get(Secs2 value) throws Secs2Exception {
		byte b = value.getByte(0);
		return get(b);
	}
}
