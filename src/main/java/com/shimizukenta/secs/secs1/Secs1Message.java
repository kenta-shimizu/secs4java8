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
	 * Returns true if All Secs1Blocks is valid.
	 * 
	 * @return true if All Secs1Blocks is valid.
	 */
	public boolean isValidBlocks();
	
	/**
	 * Returns SECS-I Message, HEAD-ONLY Message builder.
	 * 
	 * @param header10Bytes the HEAD-10-bytes.
	 * @return SECS-I Message instance
	 * @throws NullPointerException if input null
	 * @throws Secs1MessageHeaderByteLengthIllegalArgumentException if byte length is NOT equals 10
	 */
	public static Secs1Message of(byte[] header10Bytes) {
		return Secs1Message.of(header10Bytes, Secs2.empty());
	}
	
	/**
	 * Returns SECS-I Message.
	 * 
	 * @param header10Bytes the HEAD-10-bytes.
	 * @param body the body SECS-II
	 * @return SECS-I Message instance
	 * @throws Secs1TooBigMessageBodyException if SECS-II body is too big
	 * @throws NullPointerException if input null
	 * @throws Secs1MessageHeaderByteLengthIllegalArgumentException if byte length is NOT　equals 10
	 * @throws Secs1TooBigMessageBodyException if body is too big
	 */
	public static Secs1Message of(byte[] header10Bytes, Secs2 body) {
		
		Objects.requireNonNull(header10Bytes);
		Objects.requireNonNull(body);
		
		if (header10Bytes.length != 10) {
			throw new Secs1MessageHeaderByteLengthIllegalArgumentException();
		}
		
		return Secs1MessageBuilder.buildDataMessage(header10Bytes, body);
	}
	
	/**
	 * Returns Secs-I Message.
	 * 
	 * @param blocks the Secs-I Message blocks
	 * @return Secs-I Message instance
	 * @throws NullPointerException if blocks is null.
	 * @throws Secs1MessageEmptyBlockListIllegalArgumentException if blocks is empty
	 */
	public static Secs1Message of(List<? extends Secs1MessageBlock> blocks) {
		
		Objects.requireNonNull(blocks);
		
		if (blocks.isEmpty()) {
			throw new Secs1MessageEmptyBlockListIllegalArgumentException();
		}
		
		return Secs1MessageBuilder.buildFromBlocks(blocks);
	}
	
}
