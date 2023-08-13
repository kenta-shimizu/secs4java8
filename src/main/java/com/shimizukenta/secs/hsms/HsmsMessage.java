package com.shimizukenta.secs.hsms;

import java.util.Objects;

import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.hsms.impl.HsmsMessageBuilder;
import com.shimizukenta.secs.secs2.Secs2;

/**
 * HSMS Message.
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsMessage extends SecsMessage {
	
	/**
	 * Returns HSMS Message Type.
	 * 
	 * @return HSMS Message Type
	 */
	public HsmsMessageType messageType();
	
	/**
	 * Return true if Message is Data type, otherwise false.
	 * 
	 * @return true if Message is Data type, otherwise false.
	 */
	public boolean isDataMessage();
	
	/**
	 * Returns p-Type byte code.
	 * 
	 * @return p-Type byte code
	 */
	public byte pType();
	
	/**
	 * Returns s-Type byte code.
	 * 
	 * @return s-Type byte code
	 */
	public byte sType();
	
	
	/**
	 * Returns HSMS Message (HEAD-ONLY Message).
	 * 
	 * @param header10Bytes HEADER-10-bytes
	 * @return HSMS Message
	 * @throws NullPointerException if input null
	 * @throws IllegalArgumentException if byte length is not 10
	 */
	public static HsmsMessage of(byte[] header10Bytes) {
		
		Objects.requireNonNull(header10Bytes);
		
		int len = header10Bytes.length;
		if ( len != 10 ) {
			throw new IllegalArgumentException("header10Bytes.length != 10");
		}
		
		return HsmsMessageBuilder.build(header10Bytes);
	}
	
	/**
	 * Returns HSMS Message.
	 * 
	 * @param header10Bytes HEADER-10-bytes
	 * @param body SECS-II body
	 * @return HSMS Message
	 * @throws NullPointerException if input null
	 * @throws IllegalArgumentException if byte length is not 10
	 */
	public static HsmsMessage of(byte[] header10Bytes, Secs2 body) {
		
		Objects.requireNonNull(header10Bytes);
		Objects.requireNonNull(body);
		
		int len = header10Bytes.length;
		if ( len != 10 ) {
			throw new IllegalArgumentException("header10Bytes.length != 10");
		}
		
		return HsmsMessageBuilder.build(header10Bytes, body);
	}
	
}
