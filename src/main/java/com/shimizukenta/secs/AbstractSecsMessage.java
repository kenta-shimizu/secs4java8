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
	
	protected String toHeaderBytesString() {
		byte[] bs = header10Bytes();
		return new StringBuilder()
				.append("[").append(String.format("%02X", bs[0]))
				.append(" ").append(String.format("%02X", bs[1]))
				.append("|").append(String.format("%02X", bs[2]))
				.append(" ").append(String.format("%02X", bs[3]))
				.append("|").append(String.format("%02X", bs[4]))
				.append(" ").append(String.format("%02X", bs[5]))
				.append("|").append(String.format("%02X", bs[6]))
				.append(" ").append(String.format("%02X", bs[7]))
				.append(" ").append(String.format("%02X", bs[8]))
				.append(" ").append(String.format("%02X", bs[9]))
				.append("]")
				.toString();
	}
	
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
