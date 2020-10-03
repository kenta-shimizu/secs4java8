package com.shimizukenta.secs.hsmsss;

import java.util.Arrays;
import java.util.Objects;

import com.shimizukenta.secs.AbstractSecsMessage;
import com.shimizukenta.secs.secs2.Secs2;

/**
 * This class is implementation of HSMS-SS-Message.
 * 
 * <p>
 * This instance is created from {@link HsmsSsCommunicator#createHsmsSsMessage(byte[])
 * or {@link HsmsSsCommunicator#createHsmsSsMessage(byte[], Secs2)}<br />
 * </p>
 * <p>
 * Instances of this class are immutable.<br />
 * </p>
 * 
 * @author shimizukenta
 *
 */
public class HsmsSsMessage extends AbstractSecsMessage {
	
	private static final long serialVersionUID = 7062413563321894477L;
	
	private static final int HEAD_SIZE = 10;
	
	private final byte[] head;
	private final Secs2 body;
	
	protected HsmsSsMessage(byte[] head, Secs2 body) {
		
		Objects.requireNonNull(head);
		Objects.requireNonNull(body);
		
		if ( head.length != HEAD_SIZE ) {
			throw new IllegalArgumentException("head size is not " + HEAD_SIZE);
		}
		
		this.head = Arrays.copyOf(head, HEAD_SIZE);
		this.body = body;
	}
	
	protected HsmsSsMessage(byte[] head) {
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
			return (((int)(head[0]) << 8) & 0x00007F00) | (head[1] & 0x000000FF);
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
			
			String body = secs2().toString();
			
			if ( ! body.isEmpty() ) {
				sb.append(BR).append(body);
			}
			
			sb.append(".");
		}
		
		return sb.toString();
	}

	@Override
	public String toJson() {
		
		if ( dataMessage() ) {
			
			return super.toJson();
			
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
