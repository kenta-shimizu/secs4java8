package com.shimizukenta.secs.hsmsgs;

public enum HsmsGsMessageRejectReason {
	
	NOT_REJECT_REQ((byte)0xFF),
	UNKNOWN((byte)0xFF),
	
	NOT_SUPPORT_TYPE_S((byte)1),
	NOT_SUPPORT_TYPE_P((byte)2),
	TRANSACTION_NOT_OPEN((byte)3),
	NOT_SELECTED((byte)4),
	
	;
	
	private final byte reason;
	
	private HsmsGsMessageRejectReason(byte reason) {
		this.reason = reason;
	}
	
	public byte reasonCode() {
		return reason;
	}
	
	public static HsmsGsMessageRejectReason get(byte b) {
		
		for ( HsmsGsMessageRejectReason r : values() ) {
			if ( r == UNKNOWN ) continue;
			if ( r.reason == b ) {
				return r;
			}
		}
		
		return UNKNOWN;
	}
	
	public static HsmsGsMessageRejectReason get(HsmsGsMessage msg) {
		
		HsmsGsMessageType type = HsmsGsMessageType.get(msg);
		
		if ( type == HsmsGsMessageType.REJECT_REQ ) {
			
			byte[] head = msg.header10Bytes();
			byte b = head[3];
			return get(b);
			
		} else {
			
			return HsmsGsMessageRejectReason.NOT_REJECT_REQ;
		}
	}
	
}
