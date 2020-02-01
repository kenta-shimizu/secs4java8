package com.shimizukenta.secs.hsmsss;

import java.util.Arrays;
import java.util.Objects;

import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

public class HsmsSsMessage extends SecsMessage {

	private static final int HEAD_SIZE = 10;
	
	private byte[] head;
	private Secs2  body;
	
	public HsmsSsMessage(byte[] head, Secs2 body) {
		
		Objects.requireNonNull(head);
		Objects.requireNonNull(body);
		
		if ( head.length != HEAD_SIZE ) {
			throw new IllegalArgumentException("head size is not " + HEAD_SIZE);
		}
		
		this.head = Arrays.copyOf(head, HEAD_SIZE);
		this.body = body;
	}
	
	public HsmsSsMessage(byte[] head) {
		this(head, Secs2.empty());
	}
	
	@Override
	public int getStream() {
		return dataMessage() ? (head[2] & 0x7F) : -1;
	}

	@Override
	public int getFunction() {
		return dataMessage() ? (head[3] & 0xFF) : -1;
	}

	@Override
	public boolean wbit() {
		return (head[2] & 0x80) == 0x80;
	}

	@Override
	public Secs2 secs2() {
		return body;
	}

	@Override
	public int deviceId() {
		if ( dataMessage() ) {
			return (((int)(head[0]) << 8) & 0x7F00) | (head[1] & 0xFF);
		} else {
			throw new IllegalStateException("HsmsSsMessage is not DataMessage");
		}
	}
	
	protected boolean dataMessage() {
		return HsmsSsMessageType.get(this) == HsmsSsMessageType.DATA;
	}
	
	@Override
	public byte[] header10Bytes() {
		return Arrays.copyOf(head, head.length);
	}
	
	@Override
	protected Integer systemBytesKey() {
		
		int key;
		key =  ((int)(head[6]) << 24) & 0xFF000000;
		key |= ((int)(head[7]) << 16) & 0x00FF0000;
		key |= ((int)(head[8]) <<  8) & 0x0000FF00;
		key |= ((int)(head[9])      ) & 0x000000FF;
		
		return Integer.valueOf(key);
	}
	
	@Override
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
	
	private static final String BR = System.lineSeparator();
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder(toHeaderBytesString());
		
		if ( dataMessage() ) {
			
			sb.append(BR)
			.append("S").append(getStream())
			.append("F").append(getFunction());
			
			if (wbit()) {
				sb.append(" W");
			}
			
			Secs2 body = secs2();
			
			try {
				if ( body.secs2Bytes().length > 0 ) {
					sb.append(BR).append(body);
				}
			}
			catch (Secs2Exception e) {
				sb.append(BR).append("<PARSE FAILED>");
			}
			
			sb.append(".");
		}
		
		return sb.toString();
	}

	@Override
	public String parseToJson() {
		
		if ( dataMessage() ) {
			
			return super.parseToJson();
			
		} else {
			
			int type;
			type =  ((int)(head[4]) << 8) & 0x0000FF00;
			type |= ((int)(head[5])     ) & 0x000000FF;
			
			int p = ((int)(head[2])) & 0x000000FF;
			int s = ((int)(head[3])) & 0x000000FF;
			
			return "{\"messageType\":" + type
					+ ",\"p\":" + p
					+ ",\"s\":" + s
					+ ",\"systemBytes\":"+ systemBytesKey().toString()
					+ "}";
		}
	}

}
