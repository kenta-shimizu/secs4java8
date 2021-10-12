package com.shimizukenta.secs.hsms;

public enum HsmsMessageDeselectStatus {

	NOT_DESELECT_RSP((byte)0xFF),
	UNKNOWN((byte)0xFF),
	
	SUCCESS((byte)0),
	NO_SELECTED((byte)1),
	FAILED((byte)2),
	
	;
	
	private final byte status;
	
	private HsmsMessageDeselectStatus(byte status) {
		this.status = status;
	}
	
	public byte statusCode() {
		return status;
	}
	
	public static HsmsMessageDeselectStatus get(byte b) {
		
		for ( HsmsMessageDeselectStatus s : values() ) {
			
			if ( s == NOT_DESELECT_RSP ) continue;
			if ( s == UNKNOWN ) continue;
			if ( s.status == b ) {
				return s;
			}
		}
		
		return UNKNOWN;
	}
	
	public static HsmsMessageDeselectStatus get(HsmsMessage msg) {
		
		HsmsMessageType type = HsmsMessageType.get(msg);
		
		if ( type == HsmsMessageType.DESELECT_RSP ) {
			
			byte[] head = msg.header10Bytes();
			byte b = head[3];
			return get(b);
			
		} else {
			
			return NOT_DESELECT_RSP;
		}
	}

}
