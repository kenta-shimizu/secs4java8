package com.shimizukenta.secs.hsms;

public enum HsmsMessageType {
	
	UNDEFINED( (byte)0x80, (byte)0x80 ),
	
	DATA( (byte)0, (byte)0 ),
	SELECT_REQ( (byte)0, (byte)1 ),
	SELECT_RSP( (byte)0, (byte)2 ),
	DESELECT_REQ( (byte)0, (byte)3 ),
	DESELECT_RSP( (byte)0, (byte)4 ),
	LINKTEST_REQ( (byte)0, (byte)5 ),
	LINKTEST_RSP( (byte)0, (byte)6 ),
	REJECT_REQ( (byte)0, (byte)7 ),
	SEPARATE_REQ( (byte)0, (byte)9 ),
	
	;
	
	private final byte p;
	private final byte s;
	
	private HsmsMessageType(byte p, byte s) {
		this.p = p;
		this.s = s;
	}

	public byte pType() {
		return p;
	}
	
	public byte sType() {
		return s;
	}
	
	public static HsmsMessageType get(byte p, byte s) {
		
		for ( HsmsMessageType t : values() ) {
			if ( t != UNDEFINED && t.p == p && t.s == s ) {
				return t;
			}
		}
		
		return UNDEFINED;
	}
	
	public static HsmsMessageType get(HsmsMessage msg) {
		
		byte[] head = msg.header10Bytes();
		byte p = head[4];
		byte s = head[5];
		
		return get(p, s);
	}
	
	public static boolean supportPType(byte p) {
		
		for ( HsmsMessageType t : values() ) {
			if ( t != UNDEFINED && t.p == p ) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean supportPType(HsmsMessage msg) {
		byte[] head = msg.header10Bytes();
		byte p = head[4];
		return supportPType(p);
	}
	
	public static boolean supportSType(byte s) {
		
		for ( HsmsMessageType t : values() ) {
			if ( t != UNDEFINED && t.s == s ) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean supportSType(HsmsMessage msg) {
		byte[] head = msg.header10Bytes();
		byte s = head[5];
		return supportPType(s);
	}
	
}
