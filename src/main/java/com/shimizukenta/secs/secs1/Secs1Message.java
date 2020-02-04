package com.shimizukenta.secs.secs1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.shimizukenta.secs.AbstractSecsMessage;
import com.shimizukenta.secs.secs2.AbstractSecs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

public class Secs1Message extends AbstractSecsMessage {
	
	private static final int HEAD_SIZE = 10;
	private static final int BODY_SIZE = 244;
	
	private byte[] head;
	private AbstractSecs2  body;
	private List<Secs1MessageBlock> blocks;
	
	public Secs1Message(byte[] head, AbstractSecs2 body) {
		super();
		
		Objects.requireNonNull(head);
		Objects.requireNonNull(body);
		
		if ( head.length != HEAD_SIZE ) {
			throw new IllegalArgumentException("head size is not " + HEAD_SIZE);
		}
		
		this.head = Arrays.copyOf(head, HEAD_SIZE);
		this.body = body;
		this.blocks = null;
	}
	
	public Secs1Message(byte[] head) {
		this(head, AbstractSecs2.empty());
	}
	
	public Secs1Message(List<Secs1MessageBlock> blocks) {
		super();
		
		this.head = null;
		this.body = null;
		this.blocks = Collections.unmodifiableList(blocks);
	}
	
	private synchronized byte[] head() {
		
		if ( this.head == null ) {
			this.head = Arrays.copyOfRange(blocks.get(0).bytes(), 1, 11);
		}
		
		return this.head;
	}
	
	private synchronized AbstractSecs2 body() {
		
		if ( this.body == null ) {
			
			try (
					ByteArrayOutputStream st = new ByteArrayOutputStream();
					) {
				
				for ( Secs1MessageBlock block : blocks ) {
					
					byte[] bs = block.bytes();
					int len = bs.length;
					
					st.write(bs, 11, (len - 13));
				}
				
				this.body = AbstractSecs2.parse(st.toByteArray());
				
			}
			catch ( IOException e ) {
				this.body = null;
			}
		}
		
		return this.body;
	}
	
	@Override
	public int getStream() {
		return head()[2] & 0x7F;
	}

	@Override
	public int getFunction() {
		return head()[3] & 0xFF;
	}
	
	public boolean rbit() {
		return (head()[0] & 0x80) == 0x80;
	}

	@Override
	public boolean wbit() {
		return (head()[2] & 0x80) == 0x80;
	}
	
	@Override
	public AbstractSecs2 secs2() {
		return body();
	}
	
	@Override
	public int deviceId() {
		byte[] bs = head();
		return (((int)(bs[0]) << 8) & 0x7F00) | (bs[1] & 0xFF);
	}
	
	private synchronized List<Secs1MessageBlock> parseBlocks() throws Secs2Exception {
		
		if ( this.blocks == null ) {
			
			byte[] headBytes = header10Bytes();
			byte[] bodyBytes = body.secs2Bytes();
			
			int bodyLength = bodyBytes.length;
			
			if ( bodyLength > (BODY_SIZE * (0x7FFF - 1)) ) {
				throw new Secs2Exception("AbstractSecs2 overflow");
			}
			
			int blockNum = 1;
			int offset = 0;
			
			List<Secs1MessageBlock> list = new ArrayList<>();
			
			for ( ;; ) {
				
				headBytes[4] = (byte)(blockNum >> 8);
				headBytes[5] = (byte)blockNum;
				
				if ( (offset + BODY_SIZE) > bodyLength ) {
					
					headBytes[4] |= (byte)0x80;
					
					list.add(new Secs1MessageBlock(headBytes
							, Arrays.copyOfRange(bodyBytes, offset, bodyLength)));
					
					break;
					
				} else {
					
					list.add(new Secs1MessageBlock(headBytes
							, Arrays.copyOfRange(bodyBytes, offset, offset + BODY_SIZE)));
					
					offset += BODY_SIZE;
					blockNum += 1;
				}
			}
			
			this.blocks = Collections.unmodifiableList(list);
		}
		
		return this.blocks;
	}
	
	public List<Secs1MessageBlock> blocks() throws Secs2Exception {
		return parseBlocks();
	}
	
	@Override
	public byte[] header10Bytes() {
		byte[] bs = head();
		return Arrays.copyOf(bs, bs.length);
	}
	
	@Override
	protected Integer systemBytesKey() {
		
		byte[] bs = head();
		
		int key;
		key =  ((int)(bs[6]) << 24) & 0xFF000000;
		key |= ((int)(bs[7]) << 16) & 0x00FF0000;
		key |= ((int)(bs[8]) <<  8) & 0x0000FF00;
		key |= ((int)(bs[9])      ) & 0x000000FF;
		
		return Integer.valueOf(key);
	}
	
	@Override
	protected String toHeaderBytesString() {
		
		byte[] bs = head();
		
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
		
		StringBuilder sb = new StringBuilder(toHeaderBytesString())
				.append(BR)
				.append("S").append(getStream()).append("F").append(getFunction());
		
		if (wbit()) {
			sb.append(" W");
		}
		
		AbstractSecs2 body = secs2();
		
		try {
			if ( body.secs2Bytes().length > 0 ) {
				sb.append(BR).append(body);
			}
		}
		catch (Secs2Exception e) {
			sb.append(BR).append("<PARSE FAILED>");
		}
		
		return sb.append(".").toString();
	}

}
