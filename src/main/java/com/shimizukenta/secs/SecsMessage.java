package com.shimizukenta.secs;

import com.shimizukenta.secs.secs2.Secs2;

/**
 * This interface is implementation of Send and Receive Message.
 * 
 * <p>
 * SecsMessage contains Stream, Function, WBit, Datta<br />
 * </p>
 * <ul>
 * <li>To get SECS-II-Stream-Number, {@link #getStream()}</li>
 * <li>To get SECS-II-Function-Number, {@link #getFunction()}</li>
 * <li>To get SECS-II-WBit, {@link #wbit()}</li>
 * <li>To get SECS-II-Data, {@link #secs2()}</li>
 * <li>To get Header-10-bytes, {@link #header10Bytes()}</li>
 * <li>To get Message-Device-ID, {@link #deviceId()}</li>
 * <li>To get Message-Session-ID, {@link #sessionId()}</li>
 * </ul>
 * <p>
 * Instances of this class are immutable.<br />
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public interface SecsMessage {
	
	/**
	 * Returns Message Stream number.
	 * 
	 * @return stream-number. -1 if not Data-Message
	 */
	public int getStream();
	
	/**
	 * Returns Message Function number.
	 * 
	 * @return function-number. -1 if not Data-Message
	 */
	public int getFunction();
	
	/**
	 * Returns Message W-Bit.
	 * 
	 * @return true if has W-Bit
	 */
	public boolean wbit();
	
	/**
	 * Returns Message SESC-II Body Data.
	 * 
	 * @return Secs2 Body Data
	 */
	public Secs2 secs2();
	
	/**
	 * Returns Message Device-ID.
	 * 
	 * @return device-id
	 */
	public int deviceId();
	
	/**
	 * Returns Message Session-ID.
	 * 
	 * @return session-id
	 */
	public int sessionId();
	
	/**
	 * Returns Message Header 10 bytes.
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
