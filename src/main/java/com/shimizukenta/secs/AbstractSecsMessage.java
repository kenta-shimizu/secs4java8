package com.shimizukenta.secs;

import java.io.Serializable;

public abstract class AbstractSecsMessage implements SecsMessage, Serializable {
	
	private static final long serialVersionUID = 6003913058174391972L;
	
	private final Object sync = new Object();
	private Integer systemBytesKey;
	private String toHeaderBytesString;
	private String toJson;
	
	protected AbstractSecsMessage() {
		this.systemBytesKey = null;
		this.toHeaderBytesString = null;
		this.toJson = null;
	}
	
	/**
	 * System-Bytes-getter, using Map's key.
	 * 
	 * @return {@code Integer} of Message System-Bytes
	 */
	public Integer systemBytesKey() {
		
		synchronized ( sync ) {
			
			if ( this.systemBytesKey == null ) {
				
				byte[] bs = this.header10Bytes();
				int i = ((int)(bs[6]) << 24) & 0xFF000000;
				i |= ((int)(bs[7]) << 16) & 0x00FF0000;
				i |= ((int)(bs[8]) <<  8) & 0x0000FF00;
				i |= (int)(bs[9]) & 0x000000FF;
				
				this.systemBytesKey = Integer.valueOf(i);
			}
			
			return this.systemBytesKey;
		}
	}
	
	/**
	 * Returns Header 10 bytes String.
	 * 
	 * @return Header 10 bytes String
	 */
	public String toHeaderBytesString() {
		
		synchronized ( sync ) {
			
			if ( this.toHeaderBytesString == null ) {
				
				byte[] bs = header10Bytes();
				
				this.toHeaderBytesString = "[" + String.format("%02X", bs[0])
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
			
			return this.toHeaderBytesString;
		}
	}
	
	@Override
	public int sessionId() {
		return deviceId();
	}

	@Override
	public String toJson() {
		
		synchronized ( sync ) {
			
			if ( this.toJson == null ) {
				
				this.toJson = "{\"strm\":" + getStream()
				+ ",\"func\":" + getFunction()
				+ ",\"wbit\":" + (wbit() ? "true" : "false")
				+ ",\"deviceId\":" + deviceId()
				+ ",\"systemBytes\":"+ systemBytesKey().toString()
				+ ",\"secs2\":"+ secs2().toJson()
				+ "}";
			}
			
			return this.toJson;
		}
	}
}
