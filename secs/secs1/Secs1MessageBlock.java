package secs.secs1;

import java.util.Arrays;

public class Secs1MessageBlock {
	
	private final byte[] bytes;
	
	public Secs1MessageBlock(byte[] bs) {
		this.bytes = Arrays.copyOf(bs, bs.length);
	}
	
	public int deviceId() {
		return ((bytes[1] << 8) & 0x7F00) | (bytes[2] & 0xFF);
	}
	
	public int getStream() {
		return bytes[3] & 0x7F;
	}
	
	public int getFunction() {
		return bytes[4] & 0xFF;
	}
	
	public boolean wbit() {
		return (bytes[3] & 0x80) == 0x80;
	}
	
	public boolean ebit() {
		return (bytes[5] & 0x80) == 0x80;
	}
	
	public int blockNumber() {
		return ((bytes[5] << 8) & 0x7F00) | (bytes[6] & 0xFF);
	}
	
	protected boolean sameBlock(Secs1MessageBlock ref) {
		
		return (ref.bytes[1] == bytes[1]
				&& ref.bytes[2] == bytes[2]
				&& ref.bytes[3] == bytes[3]
				&& ref.bytes[4] == bytes[4]
				&& ref.bytes[5] == bytes[5]
				&& ref.bytes[6] == bytes[6]
				&& ref.bytes[7] == bytes[7]
				&& ref.bytes[8] == bytes[8]
				&& ref.bytes[9] == bytes[9]
				&& ref.bytes[10] == bytes[10]);
	}
	
	protected boolean expectBlock(Secs1MessageBlock ref) {
		
		int expectBlockNumber = ref.blockNumber() + 1;
		
		if ( blockNumber() != expectBlockNumber ) {
			return false;
		}
		
		return (ref.bytes[1] == bytes[1]
				&& ref.bytes[2] == bytes[2]
				&& ref.bytes[3] == bytes[3]
				&& ref.bytes[4] == bytes[4]
				&& ref.bytes[7] == bytes[7]
				&& ref.bytes[8] == bytes[8]
				&& ref.bytes[9] == bytes[9]
				&& ref.bytes[10] == bytes[10]);
	}
	
}
