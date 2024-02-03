package com.shimizukenta.secs.hsms;

/**
 * HSMS DESELECT staus.
 * 
 * @author kenta-shimizu
 *
 */
public enum HsmsMessageDeselectStatus {
	
	/**
	 * NOT_DESELECT_RSP.
	 * 
	 */
	NOT_DESELECT_RSP((byte)0xFF),
	
	/**
	 * UNKNOWN.
	 * 
	 */
	UNKNOWN((byte)0xFF),
	
	/**
	 * SUCCRSS.
	 * 
	 * <p>
	 * STATUS: 0x00<br />
	 * </p>
	 */
	SUCCESS((byte)0),
	
	/**
	 * NO_SELECTED.
	 * 
	 * <p>
	 * STATUS: 0x01<br />
	 * </p>
	 */
	NO_SELECTED((byte)1),
	
	/**
	 * FAILED.
	 * 
	 * <p>
	 * STATUS: 0x02<br />
	 * </p>
	 */
	FAILED((byte)2),
	
	;
	
	private final byte status;
	
	private HsmsMessageDeselectStatus(byte status) {
		this.status = status;
	}
	
	/**
	 * Returns byte code.
	 * 
	 * @return byte code
	 */
	public byte statusCode() {
		return status;
	}
	
	/**
	 * Returns DESELECT status from byte code.
	 * 
	 * @param b byte code
	 * @return DESELECT status
	 */
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
	
	/**
	 * Returns DESELECT status from HSMS Message.
	 * 
	 * @param msg the HSMS Message
	 * @return DESELECT status
	 */
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
