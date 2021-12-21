package com.shimizukenta.secs.sml;

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
	
}
