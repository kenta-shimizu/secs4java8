package com.shimizukenta.secs.sml.impl;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;

import com.shimizukenta.secs.sml.SmlMessage;
import com.shimizukenta.secs.sml.SmlMessageParser;
import com.shimizukenta.secs.sml.SmlParseException;

/**
 * SmlMessageParser Singleton-pattern getInstance.
 * 
 * @author kenta-shimizu
 *
 */
public final class SmlMessageParsers {

	private SmlMessageParsers() {
		/* Nothing */
	}
	
	private static final class SingletonHolder {
		private static final SmlMessageParser inst = new AbstractSmlMessageParser() {};
	}
	
	public static SmlMessageParser getInstance() {
		return SingletonHolder.inst;
	}
	
	public static SmlMessage parse(CharSequence cs) throws SmlParseException {
		return getInstance().parse(cs);
	}
	
	public static SmlMessage parse(Reader reader) throws SmlParseException, IOException {
		return getInstance().parse(reader);
	}
	
	public static SmlMessage parse(Path path) throws SmlParseException, IOException {
		return getInstance().parse(path);
	}
	
}
