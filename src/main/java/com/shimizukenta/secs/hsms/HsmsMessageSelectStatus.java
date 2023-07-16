package com.shimizukenta.secs.hsms;

/**
 * HSMS Message Select status.
 * 
 * @author kenta-shimizu
 *
 */
public enum HsmsMessageSelectStatus {
	
	/**
	 * NOT_SELECT_RSP.
	 * 
	 */
	NOT_SELECT_RSP((byte)0xFF),
	
	/**
	 * UNKNOWN.
	 * 
	 */
	UNKNOWN((byte)0xFF),
	
	/**
	 * SUCCESS.
	 * 
	 */
	SUCCESS((byte)0),
	
	/**
	 * ACTIVED.
	 * 
	 */
	ACTIVED((byte)1),
	
	/**
	 * NOT_READY.
	 */
	NOT_READY((byte)2),
	
	/**
	 * ALREADY_USED.
	 */
	ALREADY_USED((byte)3),
	
	/**
	 * ENTITY_UNKNOWN.
	 * 
	 */
	ENTITY_UNKNOWN((byte)4),
	
	/**
	 * ENTITY_ALREADY_USED.
	 * 
	 */
	ENTITY_ALREADY_USED((byte)5),
	
	/**
	 * ENTITY_ACTIVED.
	 * 
	 */
	ENTITY_ACTIVED((byte)6),
	
	;
	
	private final byte status;
	
	private HsmsMessageSelectStatus(byte status) {
		this.status = status;
	}
	
	/**
	 * Returns Status byte code.
	 * 
	 * @return Status byte code
	 */
	public byte statusCode() {
		return status;
	}
	
	/**
	 * Returns HSMS Message Select Status from byte code.
	 * 
	 * @param b byte code
	 * @return HSMS Message Select Status
	 */
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
	
	/**
	 * Returns HSMS Message Select Status from HSMS Message.
	 * 
	 * @param msg HSMS Message
	 * @return HSMS Message Select status
	 */
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
