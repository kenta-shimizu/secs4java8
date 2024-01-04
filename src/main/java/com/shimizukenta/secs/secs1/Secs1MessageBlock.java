package com.shimizukenta.secs.secs1;

import com.shimizukenta.secs.secs1.impl.AbstractSecs1MessageBlock;

/**
 * SECS-I Message Block.
 * 
 * @author kenta-shimizu
 *
 */
public interface Secs1MessageBlock {
	
	/**
	 * Returns true if block is valid data.
	 * 
	 * <p>
	 * Valid block condition is
	 * </p>
	 * <ul>
	 * <li>bytes.ength is  {@code >=13 && <=257}</li>
	 * <li>length-byte is {@code >=10 && <=254}</li>
	 * <li>length-byte + 3 == bytes.length</li>
	 * <li>checksum is true</li>
	 * </ul>
	 * 
	 * @return true if valid block
	 */
	public boolean isValid();
	
	/**
	 * Returns Block Device-ID number.
	 * 
	 * <p>
	 * Returns -1 if block is <strong>NOT</strong> valid data.<br />
	 * </p>
	 * 
	 * @return Device-ID
	 */
	public int deviceId();
	
	/**
	 * Returns Block E-Bit.
	 * 
	 * <p>
	 * Returns false if block is <strong>NOT</strong> valid data.<br />
	 * </p>
	 * 
	 * @return true if has e-bit
	 */
	public boolean ebit();
	
	/**
	 * Returns Block number.
	 * 
	 * <p>
	 * Returns -1 if block is <strong>NOT</strong> valid data.<br />
	 * </p>
	 * 
	 * @return block number
	 */
	public int blockNumber();
	
	/**
	 * Return Block is First.
	 * 
	 * <p>
	 * Returns false if block is <strong>NOT</strong> valid data.<br />
	 * </p>
	 * 
	 * @return true if block-number is ZERO or ONE
	 */
	public boolean isFirstBlock();
	
	/**
	 * Returns Length-byte number.
	 * 
	 * <p>
	 * Returns -1 if block is <strong>NOT</strong> valid data.<br />
	 * </p>
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
	 * <p>
	 * Returns false if block is <strong>NOT</strong> valid data.<br />
	 * </p>
	 * 
	 * @return true if check-sum is valid
	 */
	public boolean checkSum();
	
	/**
	 * Returns Block system bytes is equals.
	 * 
	 * <p>
	 * Returns false if block is <strong>NOT</strong> valid data.<br />
	 * </p>
	 * 
	 * @param otherBlock the Other SECS-I Message Block
	 * @return true if system bytes is equals
	 */
	public boolean equalsSystemBytes(Secs1MessageBlock otherBlock);
	
	/**
	 * Returns nextBlock is next block.
	 * 
	 * <p>
	 * Returns false if block is <strong>NOT</strong> valid data.<br />
	 * </p>
	 * 
	 * @param nextBlock the next SECS-I Message Block
	 * @return true if nextBlock is next block.
	 */
	public boolean isNextBlock(Secs1MessageBlock nextBlock);
	
	/**
	 * Returns Secs1MessageBlock.
	 * 
	 * <p>
	 * This method can build also invalid block.
	 * </p>
	 * 
	 * @param bs block bytes.
	 * @return Secs1MessageBlock
	 * @throws NullPointerException if input null
	 */
	public static Secs1MessageBlock of(byte[] bs) {
		
		return new AbstractSecs1MessageBlock(bs) {

			private static final long serialVersionUID = -8242596923530274834L;
		};
	}
	
}
