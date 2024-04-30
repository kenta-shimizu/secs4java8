package com.shimizukenta.secs.secs1;

/**
 * Secs1NotReceiveNextBlockEnqException.
 * 
 * @author kenta-shimizu
 *
 */
public class Secs1NotReceiveNextBlockEnqException extends Secs1Exception {
	
	private static final long serialVersionUID = -6660486242848040460L;
	
	/**
	 * Constructor.
	 * 
	 * @param block the Secs1MessageBlock
	 * @param b thre receive byte
	 */
	public Secs1NotReceiveNextBlockEnqException(Secs1MessageBlock block, byte b) {
		super("previous block: " + block.toString() + ", receive: " + String.format("%02X", b));
	}

}
