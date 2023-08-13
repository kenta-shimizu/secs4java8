package com.shimizukenta.secs.secs1;

import java.util.Objects;

import com.shimizukenta.secs.secs1.impl.Secs1s;

/**
 * SECS-I Message Block.
 * 
 * @author kenta-shimizu
 *
 */
public interface Secs1MessageBlock {
	
	/**
	 * Returns Block Device-ID number.
	 * 
	 * @return Device-ID
	 */
	public int deviceId();
	
	/**
	 * Returns Block E-Bit.
	 * 
	 * @return true if has e-bit
	 */
	public boolean ebit();
	
	/**
	 * Returns Block number.
	 * 
	 * @return block number
	 */
	public int blockNumber();
	
	/**
	 * Return Block is First.
	 * 
	 * @return true if block-number is ZERO or ONE
	 */
	public boolean isFirstBlock();
	
	/**
	 * Returns Length-byte number.
	 * 
	 * @return Length-byte number
	 */
	public int length();
	
	/**
	 * Returns Block bytes.
	 * 
	 * @return Block bytes
	 */
	public byte[] getBytes();
	
	/**
	 * Returns Check SUM result.
	 * 
	 * @return true if check-sum is valid
	 */
	public boolean checkSum();
	
	/**
	 * Returns Block system bytes is equals.
	 * 
	 * @param otherBlock the Other SECS-I Message Block
	 * @return true if system bytes is equals
	 */
	public boolean equalsSystemBytes(Secs1MessageBlock otherBlock);
	
	/**
	 * Returns nextBlock is next block.
	 * 
	 * @param nextBlock the next SECS-I Message Block
	 * @return true if nextBlock is next block.
	 */
	public boolean isNextBlock(Secs1MessageBlock nextBlock);
	
	/**
	 * Returns Secs1MessageBlock.
	 * 
	 * @param bs block bytes.
	 * @return Secs1MessageBlock
	 * @throws NullPointerException if input null
	 * @throws IllegalArgumentException if byte length Illegal
	 */
	public static Secs1MessageBlock of(byte[] bs) {
		
		Objects.requireNonNull(bs);
		
		int len = bs.length;
		if (len < 13 || len > 257) {
			throw new IllegalArgumentException("byte length is >=13 && <= 257");
		}
		
		int lengthByte = (int)(bs[0]) & 0x000000FF;
		if ( lengthByte < 10 || lengthByte > 254 ) {
			throw new IllegalArgumentException("length-byte is >=10 && <= 254");
		}
		
		if ((lengthByte + 3) > len ) {
			throw new IllegalArgumentException("(length-byte + 3) > bytes.length");
		}
		
		return Secs1s.newMessageBlock(bs);
	}
	
}
