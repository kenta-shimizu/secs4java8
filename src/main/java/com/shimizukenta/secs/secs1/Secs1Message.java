package com.shimizukenta.secs.secs1;

import java.util.List;

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
	 */
	public static Secs1Message of(byte[] header10Bytes) {
		return Secs1Message.of(header10Bytes, Secs2.empty());
	}
	
	/**
	 * Returns SECS-I Message.
	 * 
	 * @param header10Bytes the HEAD-10-bytes.
	 * @param body the body SECS-II
	 * @return SECS-I Message
	 * @throws Secs1TooBigMessageBodyException if SECS-II body is too big
	 */
	public static Secs1Message of(byte[] header10Bytes, Secs2 body) {
		try {
			return Secs1MessageBuilder.build(header10Bytes, body);
		}
		catch ( Secs1TooBigSendMessageException e ) {
			throw new Secs1TooBigMessageBodyException();
		}
	}
	
}
