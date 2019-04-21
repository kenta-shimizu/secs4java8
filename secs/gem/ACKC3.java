package secs.gem;

import secs.secs2.Secs2;
import secs.secs2.Secs2Exception;

public enum ACKC3 {
	
	UNDEFINED((byte)0xFF),
	
	OK((byte)0x0),
	
	;
	
	private final byte code;
	private final Secs2 ss;
	
	private ACKC3(byte b) {
		this.code = b;
		this.ss = Secs2.binary(b);
	}
	
	public byte code() {
		return code;
	}
	
	public Secs2 secs2() {
		return ss;
	}
	
	public static ACKC3 get(byte b) {
		
		for ( ACKC3 v : values() ) {
			if ( v == UNDEFINED ) continue;
			if ( v.code == b ) {
				return v;
			}
		}
		
		return UNDEFINED;
	}
	
	public static ACKC3 get(Secs2 value) throws Secs2Exception {
		byte b = value.getByte(0);
		return get(b);
	}
}
