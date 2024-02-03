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
	
	public static AbstractSecs1Message buildDataMessage(byte[] header) {
		return AbstractSecs1MessageBuilder.buildDataMessage(header);
	}
	
	public static AbstractSecs1Message buildDataMessage(byte[] header, Secs2 body) {
		return AbstractSecs1MessageBuilder.buildDataMessage(header, body);
	}
	
	public static AbstractSecs1Message buildFromBlocks(List<? extends Secs1MessageBlock> blocks) {
		return AbstractSecs1MessageBuilder.buildFromBlocks(blocks);
	}
	
}
