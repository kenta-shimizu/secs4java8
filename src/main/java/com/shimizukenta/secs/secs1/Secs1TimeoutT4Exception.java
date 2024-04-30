package com.shimizukenta.secs.secs1;

public class Secs1TimeoutT4Exception extends Secs1Exception {
	
	private static final long serialVersionUID = -8074724615175453917L;
	
	public Secs1TimeoutT4Exception(Secs1MessageBlock prevBlock) {
		super("previous block: " + prevBlock.toString());
	}

}
