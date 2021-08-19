package com.shimizukenta.secs.hsmsgs;

public enum HsmsGsMessageSelectStatus {
	
	NOT_SELECT_RSP((byte)0xFF),
	UNKNOWN((byte)0xFF),
	
	SUCCESS((byte)0),
	ACTIVED((byte)1),
	NOT_READY((byte)2),
	ALREADY_USED((byte)3),
	
	//TODO
	
	;
	
	private final byte status;
	
	private HsmsGsMessageSelectStatus(byte status) {
		this.status = status;
	}
	
	public byte statusCode() {
		return status;
	}
	
	public static HsmsGsMessageSelectStatus get(byte b) {
		
		for ( HsmsGsMessageSelectStatus s : values() ) {
			
			if ( s == NOT_SELECT_RSP ) continue;
			if ( s == UNKNOWN ) continue;
			if ( s.status == b ) {
				return s;
			}
		}
		
		return UNKNOWN;
	}
	
	public static HsmsGsMessageSelectStatus get(HsmsGsMessage msg) {
		
		HsmsGsMessageType type = HsmsGsMessageType.get(msg);
		
		if ( type == HsmsGsMessageType.SELECT_RSP ) {
			
			byte[] head = msg.header10Bytes();
			byte b = head[3];
			return get(b);
			
		} else {
			
			return NOT_SELECT_RSP;
		}
	}
	
}
