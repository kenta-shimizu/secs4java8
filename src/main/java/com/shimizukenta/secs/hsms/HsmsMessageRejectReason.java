package com.shimizukenta.secs.hsms;

/**
 * HSMS Message Reject Reason.
 * 
 * @author kenta-shimizu
 *
 */
public enum HsmsMessageRejectReason {
	
	/**
	 * NOT_REJECT_REQ.
	 * 
	 */
	NOT_REJECT_REQ((byte)0xFF),
	
	/**
	 * UNKNOWN.
	 * 
	 */
	UNKNOWN((byte)0xFF),
	
	/**
	 * NOT_SUPPORT_TYPE_S.
	 * 
	 * <p>
	 * REASON: 0x01<br />
	 * </p>
	 */
	NOT_SUPPORT_TYPE_S((byte)1),
	
	/**
	 * NOT_SUPPORT_TYPE_P.
	 * 
	 * <p>
	 * REASON: 0x02<br />
	 * </p>
	 */
	NOT_SUPPORT_TYPE_P((byte)2),
	
	/**
	 * TRANSACTION_NOT_OPEN.
	 * 
	 * <p>
	 * REASON: 0x03<br />
	 * </p>
	 */
	TRANSACTION_NOT_OPEN((byte)3),
	
	/**
	 * NOT_SELECTED.
	 * 
	 * <p>
	 * REASON: 0x04<br />
	 * </p>
	 */
	NOT_SELECTED((byte)4),
	
	;
	
	private final byte reason;
	
	private HsmsMessageRejectReason(byte reason) {
		this.reason = reason;
	}
	
	/**
	 * Returns reason byte code.
	 * 
	 * @return reason byte code
	 */
	public byte reasonCode() {
		return reason;
	}
	
	/**
	 * Returns HSMS Reject Reason from byte code.
	 * 
	 * @param b byte code
	 * @return HSMS Reject reason
	 */
	public static HsmsMessageRejectReason get(byte b) {
		
		for ( HsmsMessageRejectReason r : values() ) {
			if ( r == UNKNOWN ) continue;
			if ( r.reason == b ) {
				return r;
			}
		}
		
		return UNKNOWN;
	}
	
	/**
	 * Returns HSMS Reject reason from HSMS Message.
	 * 
	 * @param msg the HSMS Message
	 * @return HSMS Reject reason
	 */
	public static HsmsMessageRejectReason get(HsmsMessage msg) {
		
		HsmsMessageType type = HsmsMessageType.get(msg);
		
		if ( type == HsmsMessageType.REJECT_REQ ) {
			
			byte[] head = msg.header10Bytes();
			byte b = head[3];
			return get(b);
			
		} else {
			
			return HsmsMessageRejectReason.NOT_REJECT_REQ;
		}
	}
	
}
