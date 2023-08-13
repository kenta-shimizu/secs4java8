package com.shimizukenta.secs.secs1;

import java.util.List;
import java.util.Objects;

import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.secs1.impl.Secs1MessageBuilder;
import com.shimizukenta.secs.secs2.Secs2;

/**
 * SECS-I Message.
 * 
 * @author kenta-shimizu
 *
 */
public interface Secs1Message extends SecsMessage {
	
	/**
	 * Returns Message R-Bit.
	 * 
	 * @return true if has r-bit
	 */
	public boolean rbit();
	
	/**
	 * Returns Message blocks.
	 * 
	 * @return List of Message blocks
	 */
	public List<Secs1MessageBlock> toBlocks();
	
	/**
	 * Returns SECS-I Message, HEAD-ONLY Message builder.
	 * 
	 * @param header10Bytes the HEAD-10-bytes.
	 * @return SECS-I Message
	 * @throws NullPointerException if input null
	 * @throws IllegalArgumentException if byte length is not 10
	 */
	public static Secs1Message of(byte[] header10Bytes) {
		
		Objects.requireNonNull(header10Bytes);
		
		int len = header10Bytes.length;
		if ( len != 10 ) {
			throw new IllegalArgumentException("header10Bytes.length != 10");
		}
		
		return Secs1Message.of(header10Bytes, Secs2.empty());
	}
	
	/**
	 * Returns SECS-I Message.
	 * 
	 * @param header10Bytes the HEAD-10-bytes.
	 * @param body the body SECS-II
	 * @return SECS-I Message
	 * @throws Secs1TooBigMessageBodyException if SECS-II body is too big
	 * @throws NullPointerException if input null
	 * @throws IllegalArgumentException if byte length is not 10
	 */
	public static Secs1Message of(byte[] header10Bytes, Secs2 body) {
		
		Objects.requireNonNull(header10Bytes);
		Objects.requireNonNull(body);
		
		int len = header10Bytes.length;
		if ( len != 10 ) {
			throw new IllegalArgumentException("header10Bytes.length != 10");
		}
		
		try {
			return Secs1MessageBuilder.build(header10Bytes, body);
		}
		catch ( Secs1TooBigSendMessageException e ) {
			throw new Secs1TooBigMessageBodyException();
		}
	}
	
}
