package com.shimizukenta.secs.secs1;

/**
 * Secs1NotReceiveAckException.
 * 
 * @author kenta-shimizu
 *
 */
public class Secs1NotReceiveAckException extends Secs1Exception {
	
	private static final long serialVersionUID = 466143337209338862L;
	
	/**
	 * Constructor.
	 * 
	 * @param block the Secs1MessageBlock
	 * @param b the receive byte
	 */
	public Secs1NotReceiveAckException(Secs1MessageBlock block, byte b) {
		super("block: " + block.toString() + ", receive byte: " + String.format("%02X", b));
	}
	
}
