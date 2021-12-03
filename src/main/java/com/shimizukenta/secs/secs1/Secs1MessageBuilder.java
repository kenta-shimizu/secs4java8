package com.shimizukenta.secs.secs1;

import java.util.List;

import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

/**
 * 
 * @author kenta-shimizu
 *
 */
public interface Secs1MessageBuilder {
	
	public AbstractSecs1Message build(int strm, int func, boolean wbit) throws Secs1SendMessageException;
	
	public AbstractSecs1Message build(int strm, int func, boolean wbit, Secs2 body) throws Secs1SendMessageException;
	
	public AbstractSecs1Message build(SecsMessage primaryMsg, int strm, int func, boolean wbit) throws Secs1SendMessageException;
	
	public AbstractSecs1Message build(SecsMessage primaryMsg, int strm, int func, boolean wbit, Secs2 body) throws Secs1SendMessageException;
	
	public AbstractSecs1Message fromBlocks(List<? extends AbstractSecs1MessageBlock> blocks) throws Secs2Exception;
	
	public AbstractSecs1Message fromMessage(Secs1Message msg) throws Secs1SendMessageException;
	
	public static AbstractSecs1Message build(byte[] header, Secs2 body) throws Secs1TooBigSendMessageException {
		return AbstractSecs1MessageBuilder.build(header, body);
	}
	
}
