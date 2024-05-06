package com.shimizukenta.secs.hsms;

/**
 * HSMS Session interface.
 * 
 * @author kenta-shimizu
 *
 */
public interface HsmsSession extends HsmsGemAccessor, HsmsMessageReceiveObservable, HsmsCommunicateStateDetectable {
	
	/**
	 * SELECT.REQ.
	 * 
	 * @return true if SELECT Success, otherwise false
	 * @throws InterruptedException if interrupted
	 */
	public boolean select() throws InterruptedException;
	
	/**
	 * DESELECT.REQ.
	 * 
	 * @return true if DESELECT Success, otherwise false
	 * @throws InterruptedException if interrupted
	 */
	public boolean deselect() throws InterruptedException;
	
	/**
	 * SEPARATE.REQ.
	 * 
	 * @return true if SEPARATE Success, otherwise false
	 * @throws InterruptedException if interrupted
	 */
	public boolean separate() throws InterruptedException;
	
}
