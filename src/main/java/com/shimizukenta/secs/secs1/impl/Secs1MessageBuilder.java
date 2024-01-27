package com.shimizukenta.secs.secs1.impl;

import java.util.List;

import com.shimizukenta.secs.impl.SecsMessageBuilder;
import com.shimizukenta.secs.secs1.Secs1Communicator;
import com.shimizukenta.secs.secs1.Secs1MessageBlock;
import com.shimizukenta.secs.secs2.Secs2;

/**
 * SECS-I-Message builder.
 * 
 * @author kenta-shimizu
 *
 */
public interface Secs1MessageBuilder extends SecsMessageBuilder<AbstractSecs1Message, Secs1Communicator> {
	
	public static AbstractSecs1Message build(byte[] header) {
		return AbstractSecs1MessageBuilder.build(header);
	}
	
	public static AbstractSecs1Message build(byte[] header, Secs2 body) {
		return AbstractSecs1MessageBuilder.build(header, body);
	}
	
	public static AbstractSecs1Message fromBlocks(List<? extends Secs1MessageBlock> blocks) {
		return AbstractSecs1MessageBuilder.fromBlocks(blocks);
	}
	
}
