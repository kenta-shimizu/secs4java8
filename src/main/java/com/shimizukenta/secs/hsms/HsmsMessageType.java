package com.shimizukenta.secs.hsms;

/**
 * HSMS Message Type.
 * 
 * @author kenta-shimizu
 *
 */
public enum HsmsMessageType {
	
	/**
	 * UNDEFINED.
	 * 
	 */
	UNDEFINED( (byte)0x80, (byte)0x80 ),
	
	/**
	 * DATA.
	 * 
	 * <p>
	 * P: 0x00<br />
	 * S: 0x00<br />
	 * </p>
	 */
	DATA( (byte)0, (byte)0 ),
	
	/**
	 * SELECT_REQ.
	 * 
	 * <p>
	 * P: 0x00<br />
	 * S: 0x01<br />
	 * </p>
	 */
	SELECT_REQ( (byte)0, (byte)1 ),
	
	/**
	 * SELECT_RSP.
	 * 
	 * <p>
	 * P: 0x00<br />
	 * S: 0x02<br />
	 * </p>
	 */
	SELECT_RSP( (byte)0, (byte)2 ),
	
	/**
	 * DESELECT_REQ.
	 * 
	 * <p>
	 * P: 0x00<br />
	 * S: 0x03<br />
	 * </p>
	 */
	DESELECT_REQ( (byte)0, (byte)3 ),
	
	/**
	 * DESELECT_RSP.
	 * 
	 * <p>
	 * P: 0x00<br />
	 * S: 0x04<br />
	 * </p>
	 */
	DESELECT_RSP( (byte)0, (byte)4 ),
	
	/**
	 * LINKTEST_REQ.
	 * 
	 * <p>
	 * P: 0x00<br />
	 * S: 0x05<br />
	 * </p>
	 */
	LINKTEST_REQ( (byte)0, (byte)5 ),
	
	/**
	 * LINKTEST_RSP.
	 * 
	 * <p>
	 * P: 0x00<br />
	 * S: 0x06<br />
	 * </p>
	 */
	LINKTEST_RSP( (byte)0, (byte)6 ),
	
	/**
	 * REJECT_REQ.
	 * 
	 * <p>
	 * P: 0x00<br />
	 * S: 0x07<br />
	 * </p>
	 */
	REJECT_REQ( (byte)0, (byte)7 ),
	
	/**
	 * SEPARATE_REQ.
	 * 
	 * <p>
	 * P: 0x00<br />
	 * S: 0x09<br />
	 * </p>
	 */
	SEPARATE_REQ( (byte)0, (byte)9 ),
	
	;
	
	private final byte p;
	private final byte s;
	
	private HsmsMessageType(byte p, byte s) {
		this.p = p;
		this.s = s;
	}
	
	/**
	 * Returns p-Type byte code.
	 * 
	 * @return p-Type byte code
	 */
	public byte pType() {
		return p;
	}
	
	/**
	 * Returns s-Type byte code.
	 * 
	 * @return s-Type byte code
	 */
	public byte sType() {
		return s;
	}
	
	/**
	 * Returns HSMS Message Type.
	 * 
	 * @param p p-Type byte code
	 * @param s s-Type byte code
	 * @return HSMS Message Type
	 */
	public static HsmsMessageType get(byte p, byte s) {
		
		for ( HsmsMessageType t : values() ) {
			if ( t != UNDEFINED && t.p == p && t.s == s ) {
				return t;
			}
		}
		
		return UNDEFINED;
	}
	
	/**
	 * Returns HSMS Message Type from HSMS Message.
	 * 
	 * @param msg HSMS Message
	 * @return HSMS Message Type
	 */
	public static HsmsMessageType get(HsmsMessage msg) {
		
		byte[] head = msg.header10Bytes();
		byte p = head[4];
		byte s = head[5];
		
		return get(p, s);
	}
	
	/**
	 * Returns true if support p-Type, otherwise false
	 * 
	 * @param p byte code
	 * @return true if support p-Type, otherwise false
	 */
	public static boolean supportPType(byte p) {
		
		for ( HsmsMessageType t : values() ) {
			if ( t != UNDEFINED && t.p == p ) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Returns true if support p-Type Message, otherwise false.
	 * 
	 * @param msg the HSMS message
	 * @return true if support p-Type Message, otherwise false
	 */
	public static boolean supportPType(HsmsMessage msg) {
		byte[] head = msg.header10Bytes();
		byte p = head[4];
		return supportPType(p);
	}
	
	/**
	 * Returns true if support s-Type, otherwise false
	 * 
	 * @param s byte code
	 * @return true if support s-Type, otherwise false
	 */
	public static boolean supportSType(byte s) {
		
		for ( HsmsMessageType t : values() ) {
			if ( t != UNDEFINED && t.s == s ) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Returns true if support s-Type Message, otherwise false.
	 * 
	 * @param msg the HSMS message
	 * @return true if support s-Type Message, otherwise false
	 */
	public static boolean supportSType(HsmsMessage msg) {
		byte[] head = msg.header10Bytes();
		byte s = head[5];
		return supportPType(s);
	}
	
}
