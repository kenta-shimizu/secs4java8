package com.shimizukenta.secs.secs1.impl;

import java.util.List;

import com.shimizukenta.secs.secs1.Secs1Message;
import com.shimizukenta.secs.secs1.Secs1MessageBlock;
import com.shimizukenta.secs.secs2.Secs2;

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
	
	public static AbstractSecs1Message newMessage(
			byte[] header10Bytes,
			Secs2 body,
			List<? extends Secs1MessageBlock> blocks) {
		
		return new AbstractSecs1Message(header10Bytes, body, blocks) {

			private static final long serialVersionUID = -3433006089733902499L;
		};
	}
	
	public static AbstractSecs1Message castOrNewMessage(Secs1Message message) {
		if ( message instanceof AbstractSecs1Message ) {
			return (AbstractSecs1Message)message;
		} else {
			return newMessage(message.header10Bytes(), message.secs2(), message.toBlocks());
		}
	}
	
}
