package com.shimizukenta.secs.secs1.impl;

import java.util.List;

import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.secs1.Secs1MessageBlock;
import com.shimizukenta.secs.secs2.Secs2;

/**
 * 
 * @author kenta-shimizu
 *
 */
public interface Secs1MessageBuilder {
	
	public AbstractSecs1Message build(int strm, int func, boolean wbit);
	
	public AbstractSecs1Message build(int strm, int func, boolean wbit, Secs2 body);
	
	public AbstractSecs1Message build(SecsMessage primaryMsg, int strm, int func, boolean wbit);
	
	public AbstractSecs1Message build(SecsMessage primaryMsg, int strm, int func, boolean wbit, Secs2 body);
	
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
