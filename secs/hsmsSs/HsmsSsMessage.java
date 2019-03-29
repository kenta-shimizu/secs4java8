package secs.hsmsSs;

import java.util.Arrays;
import java.util.Objects;

import secs.SecsMessage;
import secs.secs2.Secs2;

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
		return (head[2] & 0x7F);
	}

	@Override
	public int getFunction() {
		return (head[3] & 0xFF);
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
		
		// TODO Auto-generated method stub
		return null;
	}

}
