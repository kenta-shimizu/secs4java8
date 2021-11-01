package com.shimizukenta.secs.secs1;

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
	 * @param otherBlock
	 * @return true if system bytes is equals
	 */
	public boolean equalsSystemBytes(Secs1MessageBlock otherBlock);
	
	/**
	 * Returns nextBlock is next block.
	 * 
	 * @param nextBlock
	 * @return true if nextBlock is next block.
	 */
	public boolean isNextBlock(Secs1MessageBlock nextBlock);
	
}
