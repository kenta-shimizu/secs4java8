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
	 * <p>
	 * STATUS: 0x00<br />
	 * </p>
	 */
	SUCCESS((byte)0),
	
	/**
	 * ACTIVED.
	 * 
	 * <p>
	 * STATUS: 0x01<br />
	 * </p>
	 */
	ACTIVED((byte)1),
	
	/**
	 * NOT_READY.
	 * 
	 * <p>
	 * STATUS: 0x02<br />
	 * </p>
	 */
	NOT_READY((byte)2),
	
	/**
	 * ALREADY_USED.
	 * 
	 * <p>
	 * STATUS: 0x03<br />
	 * </p>
	 */
	ALREADY_USED((byte)3),
	
	/**
	 * ENTITY_UNKNOWN.
	 * 
	 * <p>
	 * STATUS: 0x04<br />
	 * </p>
	 */
	ENTITY_UNKNOWN((byte)4),
	
	/**
	 * ENTITY_ALREADY_USED.
	 * 
	 * <p>
	 * STATUS: 0x05<br />
	 * </p>
	 */
	ENTITY_ALREADY_USED((byte)5),
	
	/**
	 * ENTITY_ACTIVED.
	 * 
	 * <p>
	 * STATUS: 0x06<br />
	 * </p>
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
