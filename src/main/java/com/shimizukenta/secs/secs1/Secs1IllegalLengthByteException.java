package com.shimizukenta.secs.secs1;

public class Secs1IllegalLengthByteException extends Secs1Exception {
	
	private static final long serialVersionUID = -6680007447463657332L;
	
	public Secs1IllegalLengthByteException(int length) {
		super("length=" + length);
	}
	
}
