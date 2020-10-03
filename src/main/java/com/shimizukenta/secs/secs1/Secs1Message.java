package com.shimizukenta.secs.secs1;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.shimizukenta.secs.AbstractSecsMessage;
import com.shimizukenta.secs.secs2.Secs2;


/**
 * This class is implementation of SECS-I-Message.
 * 
 * <p>
 * This instance is created from {@link Secs1Communicator#createSecs1Message(byte[])
 * or {@link Secs1Communicator#createSecs1Message(byte[], Secs2)}
 * </p>
 * <p>
 * Instances of this class are immutable.<br />
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public class Secs1Message extends AbstractSecsMessage {
	
	private static final long serialVersionUID = -5892491853890818330L;

	private static final int HEAD_SIZE = 10;
	
	private final byte[] head;
	private final Secs2  body;
	
	public Secs1Message(byte[] head, Secs2 body) {
		super();
		
		this.head = Arrays.copyOf(Objects.requireNonNull(head), HEAD_SIZE);
		this.body = Objects.requireNonNull(body);
		
		if ( head.length != HEAD_SIZE ) {
			throw new IllegalArgumentException("head size is not " + HEAD_SIZE);
		}
	}
	
	public Secs1Message(byte[] head) {
		this(head, Secs2.empty());
	}
	
	@Override
	public int getStream() {
		return head[2] & 0x7F;
	}

	@Override
	public int getFunction() {
		return head[3] & 0xFF;
	}
	
	public boolean rbit() {
		return (head[0] & 0x80) == 0x80;
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
		return (((int)(head[0]) << 8) & 0x00007F00) | (head[1] & 0x000000FF);
	}
	
	@Override
	public byte[] header10Bytes() {
		return Arrays.copyOf(head, head.length);
	}
	
	private static final String BR = System.lineSeparator();
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder(toHeaderBytesString())
				.append(BR)
				.append("S").append(getStream()).append("F").append(getFunction());
		
		if (wbit()) {
			sb.append(" W");
		}
		
		String ss = body.toString();
		if ( ss.length() > 0 ) {
			sb.append(BR).append(ss);
		}
		
		return sb.append(".").toString();
	}
	
	public List<Secs1MessageBlock> toBlocks() throws Secs1SendMessageException {
		return Secs1MessageBlockConverter.toBlocks(this);
	}
}
