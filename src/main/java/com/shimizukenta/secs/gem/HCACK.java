package com.shimizukenta.secs.gem;

import com.shimizukenta.secs.secs2.AbstractSecs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

public enum HCACK {
	
	UNDEFINED((byte)0xFF),
	
	OK((byte)0x0),
	InvalidCommand((byte)0x1),
	CannotDoNow((byte)0x2),
	ParameterError((byte)0x3),
	InitiatedForAsynchronousCompletion((byte)0x4),
	RejectedAlreadyInDesiredCondition((byte)0x5),
	InvalidObject((byte)0x6),
	
	;
	
	private final byte code;
	private final AbstractSecs2 ss;
	
	private HCACK(byte b) {
		this.code = b;
		this.ss = AbstractSecs2.binary(b);
	}
	
	public byte code() {
		return code;
	}
	
	public AbstractSecs2 secs2() {
		return ss;
	}
	
	public static HCACK get(byte b) {
		
		for ( HCACK v : values() ) {
			if ( v == UNDEFINED ) continue;
			if ( v.code == b ) {
				return v;
			}
		}
		
		return UNDEFINED;
	}
	
	public static HCACK get(AbstractSecs2 value) throws Secs2Exception {
		byte b = value.getByte(0);
		return get(b);
	}
}
