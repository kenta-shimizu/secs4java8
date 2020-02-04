package com.shimizukenta.secs;

public abstract class AbstractSecsMessage implements SecsMessage {

	public AbstractSecsMessage() {
		/* Nothing */
	}
	
	protected Integer systemBytesKey() {
		byte[] bs = this.header10Bytes();
		int i = ((int)(bs[6]) << 24) & 0xFF000000;
		i |= ((int)(bs[7]) << 16) & 0x00FF0000;
		i |= ((int)(bs[8]) <<  8) & 0x0000FF00;
		i |= (int)(bs[9]) & 0x000000FF;
		return Integer.valueOf(i);
	}
	
	abstract protected String toHeaderBytesString();
	
	@Override
	public String toJson() {
		return "{\"strm\":" + getStream()
				+ ",\"func\":" + getFunction()
				+ ",\"wbit\":" + (wbit() ? "true" : "false")
				+ ",\"deviceId\":" + deviceId()
				+ ",\"systemBytes\":"+ systemBytesKey().toString()
				+ ",\"secs2\":"+ secs2().toJson()
				+ "}";
	}
}
