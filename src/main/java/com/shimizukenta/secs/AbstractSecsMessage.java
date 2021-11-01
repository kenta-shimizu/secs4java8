package com.shimizukenta.secs;

import java.io.Serializable;

public abstract class AbstractSecsMessage implements SecsMessage, Serializable {
	
	private static final long serialVersionUID = 6003913058174391972L;
	
	private final Object sync = new Object();
	
	private Integer cacheSystemBytesKey;
	private String cacheToJson;
	private String cacheToHeaderBytesString;
	private String cacheToString;
	
	protected AbstractSecsMessage() {
		this.cacheSystemBytesKey = null;
		this.cacheToJson = null;
		this.cacheToHeaderBytesString = null;
		this.cacheToString = null;
	}
	
	/**
	 * System-Bytes-getter, using Map's key.
	 * 
	 * @return {@code Integer} of Message System-Bytes
	 */
	public Integer systemBytesKey() {
		
		synchronized ( sync ) {
			
			if ( this.cacheSystemBytesKey == null ) {
				
				byte[] bs = this.header10Bytes();
				int i = ((int)(bs[6]) << 24) & 0xFF000000;
				i |= ((int)(bs[7]) << 16) & 0x00FF0000;
				i |= ((int)(bs[8]) <<  8) & 0x0000FF00;
				i |= (int)(bs[9]) & 0x000000FF;
				
				this.cacheSystemBytesKey = Integer.valueOf(i);
			}
			
			return this.cacheSystemBytesKey;
		}
	}
	
	abstract protected String toJsonProxy();
	
	@Override
	public String toJson() {
		synchronized ( sync ) {
			if ( this.cacheToJson == null ) {
				this.cacheToJson = this.toJsonProxy();
			}
			return this.cacheToJson;
		}
	}
	
	/**
	 * Returns Header 10 bytes String.
	 * 
	 * @return Header 10 bytes String
	 */
	protected String toHeaderBytesString() {
		
		synchronized ( sync ) {
			
			if ( this.cacheToHeaderBytesString == null ) {
				
				byte[] bs = header10Bytes();
				
				this.cacheToHeaderBytesString = "[" + String.format("%02X", bs[0])
						+ " " + String.format("%02X", bs[1])
						+ "|" + String.format("%02X", bs[2])
						+ " " + String.format("%02X", bs[3])
						+ "|" + String.format("%02X", bs[4])
						+ " " + String.format("%02X", bs[5])
						+ "|" + String.format("%02X", bs[6])
						+ " " + String.format("%02X", bs[7])
						+ " " + String.format("%02X", bs[8])
						+ " " + String.format("%02X", bs[9])
						+ "]";
			}
			
			return this.cacheToHeaderBytesString;
		}
	}
	
	abstract protected String toStringProxy();
	
	@Override
	public String toString() {
		synchronized ( sync ) {
			if ( this.cacheToString == null ) {
				this.cacheToString = this.toStringProxy();
			}
			return this.cacheToString;
		}
	}
	
}
