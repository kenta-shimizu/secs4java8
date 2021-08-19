package com.shimizukenta.secs.hsmsgs;

import java.util.Arrays;
import java.util.Objects;

import com.shimizukenta.secs.AbstractSecsMessage;
import com.shimizukenta.secs.secs2.Secs2;

/**
 * 
 * @author kenta-shimizu
 *
 */
public class HsmsGsMessage extends AbstractSecsMessage {

	private static final long serialVersionUID = -2949160778467912989L;

	private static final int HEAD_SIZE = 10;
	
	private final byte[] head;
	private final Secs2 body;
	
	public HsmsGsMessage(byte[] head, Secs2 body) {
		
		Objects.requireNonNull(head);
		Objects.requireNonNull(body);
		
		if ( head.length != HEAD_SIZE ) {
			throw new IllegalArgumentException("head size is not " + HEAD_SIZE);
		}
		
		this.head = Arrays.copyOf(head, HEAD_SIZE);
		this.body = body;
	}

	public HsmsGsMessage(byte[] head) {
		this(head, Secs2.empty());
	}
	
	@Override
	public int getStream() {
		//TODO
		return 0;
	}

	@Override
	public int getFunction() {
		//TODO
		return 0;
	}

	@Override
	public boolean wbit() {
		//TODO
		return false;
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
	
	@Override
	public int sessionId() {
		//TODO
		
		return -1;
	}

	protected boolean dataMessage() {
		
		//TODO
		
		return false;
	}
	
	@Override
	public byte[] header10Bytes() {
		return Arrays.copyOf(head, head.length);
	}
	
	@Override
	public String toString() {
		//TODO
		
		return "";
	}
	
	@Override
	public String toJson() {
		
		//TODO
		
		return "";
	}

}
