package com.shimizukenta.secs;

import com.shimizukenta.secs.secs2.Secs2;

public interface SecsMessage {
	
	/**
	 * 
	 * @return stream-number. -1 if not Data-Message
	 */
	public int getStream();
	
	/**
	 * 
	 * @return function-number. -1 if not Data-Message
	 */
	public int getFunction();
	
	/**
	 * 
	 * @return true if has wbit
	 */
	public boolean wbit();
	
	public Secs2 secs2();
	
	public int deviceId();
	
	default public int sessionId() {
		return deviceId();
	}
	
	public byte[] header10Bytes();
	
	public String toJson();
}
