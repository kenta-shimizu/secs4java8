package secs.hsmsSs;

public enum HsmsSsMessageSelectStatus {
	
	NOT_SELECT_RSP((byte)0xFF),
	UNKNOWN((byte)0xFF),
	
	SUCCESS((byte)0),
	ACTIVED((byte)1),
	NOT_READY((byte)2),
	ALREADY_USED((byte)3),
	
	;
	
	private final byte status;
	
	private HsmsSsMessageSelectStatus(byte status) {
		this.status = status;
	}
	
	public byte statusCode() {
		return status;
	}
	
	public static HsmsSsMessageSelectStatus get(byte b) {
		
		for ( HsmsSsMessageSelectStatus s : values() ) {
			
			if ( s == NOT_SELECT_RSP ) continue;
			if ( s == UNKNOWN ) continue;
			if ( s.status == b ) {
				return s;
			}
		}
		
		return UNKNOWN;
	}
	
	public static HsmsSsMessageSelectStatus get(HsmsSsMessage msg) {
		
		HsmsSsMessageType type = HsmsSsMessageType.get(msg);
		
		if ( type == HsmsSsMessageType.SELECT_RSP ) {
			
			byte[] head = msg.header10Bytes();
			byte b = head[3];
			return get(b);
			
		} else {
			
			return NOT_SELECT_RSP;
		}
	}
	
}
