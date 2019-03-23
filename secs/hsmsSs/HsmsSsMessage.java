package secs.hsmsSs;

import java.util.Arrays;

import secs.SecsMessage;
import secs.secs2.Secs2;

public class HsmsSsMessage extends SecsMessage {

	private static final int HEAD_SIZE = 10;
	
	private byte[] head;
	private Secs2  body;
	
	public HsmsSsMessage(byte[] head, Secs2 body) {
		
		if ( head.length != HEAD_SIZE ) {
			throw new IllegalArgumentException("head size is not " + HEAD_SIZE);
		}
		
		this.head = Arrays.copyOf(head, HEAD_SIZE);
		this.body = body;
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
		
		// TODO Auto-generated method stub
		return 0;
	}
	
	protected boolean dataMessage() {
		
		//TODO
		
		return false;
	}
	
	@Override
	protected String toHeaderBytesString() {
		// TODO Auto-generated method stub
		return null;
	}

}
