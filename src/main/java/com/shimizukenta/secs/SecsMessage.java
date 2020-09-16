package com.shimizukenta.secs;

import com.shimizukenta.secs.secs2.Secs2;

/**
 * This interface is implementation of Send and Recieve Message.<br />
 * SecsMessage contains<br />
 * <ul>
 * <li>SECS-II Stream</li>
 * <li>SECS-II Function</li>
 * <li>W-Bit</li>
 * <li>SECS-II Data</li>
 * <li>Header-10-bytes</li>
 * </ul>
 * Instances of this class are immutable.
 * 
 * @author kenta-shimizu
 *
 */
public interface SecsMessage {
	
	/**
	 * Message Stream getter
	 * 
	 * @return stream-number. -1 if not Data-Message
	 */
	public int getStream();
	
	/**
	 * Message Function getter
	 * 
	 * @return function-number. -1 if not Data-Message
	 */
	public int getFunction();
	
	/**
	 * Message W-Bit getter
	 * 
	 * @return true if has wbit
	 */
	public boolean wbit();
	
	/**
	 * Message SESC-II data getter
	 * 
	 * @return Secs2
	 */
	public Secs2 secs2();
	
	/**
	 * Message Device-ID getter.
	 * 
	 * @return device-id
	 */
	public int deviceId();
	
	/**
	 * Message Session-ID getter.
	 * 
	 * @return session-id
	 */
	public int sessionId();
	
	/**
	 * Message Header 10 bytes getter
	 * 
	 * @return header-10-bytes
	 */
	public byte[] header10Bytes();
	
	/**
	 * parse to JSON-string
	 * 
	 * @return json
	 */
	public String toJson();
}
