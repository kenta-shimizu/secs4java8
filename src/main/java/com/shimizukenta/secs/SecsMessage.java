package com.shimizukenta.secs;

import java.io.Serializable;

import com.shimizukenta.secs.secs2.Secs2;

public interface SecsMessage extends Serializable {
	
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
	
	public int sessionId();
	
	public byte[] header10Bytes();
	
	public String toJson();
}
