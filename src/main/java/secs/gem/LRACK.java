package secs.gem;

import secs.secs2.Secs2;
import secs.secs2.Secs2Exception;

public enum LRACK {
	
	UNDEFINED((byte)0xFF),
	
	OK((byte)0x0),
	OutOfSpace((byte)0x1),
	InvalidFormat((byte)0x2),
	OneOrMoreCeidLinksAlreadyDefined((byte)0x3),
	OneOrMoreCeidInvalid((byte)0x4),
	OneOrMoreRptidInvalid((byte)0x5),
	
	;
	
	private final byte code;
	private final Secs2 ss;
	
	private LRACK(byte b) {
		this.code = b;
		this.ss = Secs2.binary(b);
	}
	
	public byte code() {
		return code;
	}
	
	public Secs2 secs2() {
		return ss;
	}
	
	public static LRACK get(byte b) {
		
		for ( LRACK v : values() ) {
			if ( v == UNDEFINED ) continue;
			if ( v.code == b ) {
				return v;
			}
		}
		
		return UNDEFINED;
	}
	
	public static LRACK get(Secs2 value) throws Secs2Exception {
		byte b = value.getByte(0);
		return get(b);
	}
}
