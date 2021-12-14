package com.shimizukenta.secs.util;

/**
 * 
 * @author kenta-shimizu
 *
 */
public class SxFyAndEntityMessageReceiveListener {
	
	private final int strm;
	private final int func;
	private final EntityMessageReceiveListener lstnr;
	private final int proxyHashCode;
	
	public SxFyAndEntityMessageReceiveListener(
			int strm,
			int func,
			EntityMessageReceiveListener listener) {
		
		this.strm = strm;
		this.func = func;
		this.lstnr = listener;
		this.proxyHashCode = ((strm << 8) | func);
	}
	
	public int getStream() {
		return this.strm;
	}
	
	public int getFunction() {
		return this.func;
	}
	
	public EntityMessageReceiveListener getMessageReceiveListener() {
		return this.lstnr;
	}
	
	@Override
	public int hashCode() {
		return this.proxyHashCode;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o != null && (o instanceof SxFyAndEntityMessageReceiveListener)) {
			SxFyAndEntityMessageReceiveListener a = (SxFyAndEntityMessageReceiveListener)o;
			return (a.strm == this.strm) && (a.func == this.func);
		} else {
			return false;
		}
	}
	
}
