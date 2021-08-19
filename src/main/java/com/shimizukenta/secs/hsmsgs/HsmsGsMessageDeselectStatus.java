package com.shimizukenta.secs.hsmsgs;

public enum HsmsGsMessageDeselectStatus {

	NOT_DESELECT_RSP((byte)0xFF),
	UNKNOWN((byte)0xFF),
	
	SUCCESS((byte)0),
	NO_SELECTED((byte)1),
	FAILED((byte)2),
	
	;
	
	private final byte status;
	
	private HsmsGsMessageDeselectStatus(byte status) {
		this.status = status;
	}
	
	public byte statusCode() {
		return status;
	}
	
	public static HsmsGsMessageDeselectStatus get(byte b) {
		
		for ( HsmsGsMessageDeselectStatus s : values() ) {
			
			if ( s == NOT_DESELECT_RSP ) continue;
			if ( s == UNKNOWN ) continue;
			if ( s.status == b ) {
				return s;
			}
		}
		
		return UNKNOWN;
	}
	
	public static HsmsGsMessageDeselectStatus get(HsmsGsMessage msg) {
		
		HsmsGsMessageType type = HsmsGsMessageType.get(msg);
		
		if ( type == HsmsGsMessageType.DESELECT_RSP ) {
			
			byte[] head = msg.header10Bytes();
			byte b = head[3];
			return get(b);
			
		} else {
			
			return NOT_DESELECT_RSP;
		}
	}

}
