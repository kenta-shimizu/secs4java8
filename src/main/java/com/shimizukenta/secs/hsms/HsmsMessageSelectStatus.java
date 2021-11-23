package com.shimizukenta.secs.hsms;

public enum HsmsMessageSelectStatus {
	
	NOT_SELECT_RSP((byte)0xFF),
	UNKNOWN((byte)0xFF),
	
	SUCCESS((byte)0),
	ACTIVED((byte)1),
	NOT_READY((byte)2),
	ALREADY_USED((byte)3),
	ENTITY_UNKNOWN((byte)4),
	ENTITY_ALREADY_USED((byte)5),
	ENTITY_ACTIVED((byte)6),
	
	;
	
	private final byte status;
	
	private HsmsMessageSelectStatus(byte status) {
		this.status = status;
	}
	
	public byte statusCode() {
		return status;
	}
	
	public static HsmsMessageSelectStatus get(byte b) {
		
		for ( HsmsMessageSelectStatus s : values() ) {
			
			if ( s == NOT_SELECT_RSP ) continue;
			if ( s == UNKNOWN ) continue;
			if ( s.status == b ) {
				return s;
			}
		}
		
		return UNKNOWN;
	}
	
	public static HsmsMessageSelectStatus get(HsmsMessage msg) {
		
		HsmsMessageType type = HsmsMessageType.get(msg);
		
		if ( type == HsmsMessageType.SELECT_RSP ) {
			
			byte[] head = msg.header10Bytes();
			byte b = head[3];
			return get(b);
			
		} else {
			
			return NOT_SELECT_RSP;
		}
	}
	
}
