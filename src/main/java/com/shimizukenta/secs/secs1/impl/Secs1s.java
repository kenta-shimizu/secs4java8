package com.shimizukenta.secs.secs1.impl;

import com.shimizukenta.secs.secs1.Secs1MessageBlock;

public class Secs1s {

	private Secs1s() {
		/* Nothing */
	}
	
	public static AbstractSecs1MessageBlock newMessageBlock(byte[] bs) {
		
		return new AbstractSecs1MessageBlock(bs) {
			
			private static final long serialVersionUID = 1L;
		};
	}
	
	public static AbstractSecs1MessageBlock castOrNewMessageBlock(Secs1MessageBlock block) {
		if (block instanceof AbstractSecs1MessageBlock) {
			return (AbstractSecs1MessageBlock)block;
		} else {
			return newMessageBlock(block.getBytes());
		}
	}
	
}
