package secs.secs1;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import secs.SecsMessage;
import secs.secs2.Secs2;

public class Secs1Message extends SecsMessage {
	
	private static final int HEAD_SIZE = 10;
	
	private byte[] head;
	private Secs2  body;
	private List<Secs1MessageBlock> blocks;
	
	public Secs1Message(byte[] head, Secs2 body) {
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
		this(head, Secs2.empty());
	}
	
	protected Secs1Message(List<Secs1MessageBlock> blocks) {
		super();
		
		this.head = null;
		this.body = null;
		this.blocks = Collections.unmodifiableList(blocks);
	}
	
	private synchronized byte[] head() {
		if ( this.head == null ) {
			
			//TODO
		}
		
		return this.head;
	}
	
	private synchronized Secs2 body() {
		if ( this.body == null ) {
			
			//TODO
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
	public Secs2 secs2() {
		return body();
	}
	
	@Override
	public int deviceId() {
		byte[] b = head();
		return ((b[0] << 8) & 0x7F00) | (b[1] & 0xFF);
	}
	
	private synchronized List<Secs1MessageBlock> parseBlocks() {
		
		if ( this.blocks == null ) {
			
			//TODO
		}
		
		return blocks;
	}
	
	public List<Secs1MessageBlock> blocks() {
		return parseBlocks();
	}
	
	@Override
	protected Integer systemBytesKey() {
		
		byte[] bs = head();
		
		int key;
		key =  (bs[6] << 24) & 0xFF000000;
		key |= (bs[7] << 16) & 0x00FF0000;
		key |= (bs[8] <<  8) & 0x0000FF00;
		key |= (bs[9]      ) & 0x000000FF;
		
		return Integer.valueOf(key);
	}
	
	@Override
	protected String toHeaderBytesString() {
		
		// TODO Auto-generated method stub
		return null;
	}

}
