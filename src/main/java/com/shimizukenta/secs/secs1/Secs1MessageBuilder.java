package com.shimizukenta.secs.secs1;

import java.util.List;

import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2BytesParseException;

/**
 * 
 * @author kenta-shimizu
 *
 */
public interface Secs1MessageBuilder {
	
	public AbstractSecs1Message build(int strm, int func, boolean wbit) throws Secs1TooBigSendMessageException;
	
	public AbstractSecs1Message build(int strm, int func, boolean wbit, Secs2 body) throws Secs1TooBigSendMessageException;
	
	public AbstractSecs1Message build(SecsMessage primaryMsg, int strm, int func, boolean wbit) throws Secs1TooBigSendMessageException;
	
	public AbstractSecs1Message build(SecsMessage primaryMsg, int strm, int func, boolean wbit, Secs2 body) throws Secs1TooBigSendMessageException;
	
	public static AbstractSecs1Message fromBlocks(List<? extends AbstractSecs1MessageBlock> blocks) throws Secs2BytesParseException {
		return AbstractSecs1MessageBuilder.fromBlocks(blocks);
	}
	
	public static AbstractSecs1Message fromMessage(Secs1Message msg) throws Secs1TooBigSendMessageException {
		return AbstractSecs1MessageBuilder.fromMessage(msg);
	}
	
	public static AbstractSecs1Message build(byte[] header, Secs2 body) throws Secs1TooBigSendMessageException {
		return AbstractSecs1MessageBuilder.build(header, body);
	}
	
	public static AbstractSecs1Message build(byte[] header) throws Secs1TooBigSendMessageException {
		return AbstractSecs1MessageBuilder.build(header);
	}
	
}
