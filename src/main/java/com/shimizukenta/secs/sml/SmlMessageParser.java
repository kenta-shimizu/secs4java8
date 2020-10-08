package com.shimizukenta.secs.sml;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is implementation of SML-Parse
 * 
 * <p>
 * To get parser instance, {@link #getInstance()}<br />
 * To parse SML, {@link #parse(CharSequence)}<br />
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public class SmlMessageParser {

	protected SmlMessageParser() {
		/* Nothing */
	}
	
	private static class SingletonHolder {
		private static final SmlMessageParser inst = new SmlMessageParser();
	}
	
	/**
	 * Returns parser instance.
	 * 
	 * <p>
	 * This instance is Singleton-pattern.
	 * </p>
	 * 
	 * @return parser instance
	 */
	public static SmlMessageParser getInstance() {
		return SingletonHolder.inst;
	}
	
	/**
	 * prototype
	 * 
	 * @return
	 */
	protected SmlDataItemParser getSmlSecs2Parser() {
		return SmlDataItemParser.getInstance();
	}
	
	protected static final String GROUP_STREAM = "STREAM";
	protected static final String GROUP_FUNCTION = "FUNCTION";
	protected static final String GROUP_WBIT = "WBIT";
	protected static final String GROUP_SECS2 = "SECS2";
	protected static final String pregMessage = "[Ss](?<" + GROUP_STREAM + ">[0-9]{1,3})[Ff](?<" + GROUP_FUNCTION + ">[0-9]{1,3})\\s*(?<" + GROUP_WBIT + ">[Ww]?)\\s*(?<" + GROUP_SECS2 + ">(<.+>)?)";
	
	protected static final Pattern ptnMessage = Pattern.compile("^" + pregMessage + "$");

	/**
	 * parse to SML-Message
	 * 
	 * @param cs SML-Format-Character
	 * @return SmlMessage
	 * @throws SmlParseException
	 */
	public SmlMessage parse(CharSequence cs) throws SmlParseException  {
		
		Objects.requireNonNull(cs);
		
		String s = trimPeriod(cs);
		
		Matcher m = ptnMessage.matcher(s);
		
		if ( ! m.matches() ) {
			throw new SmlParseException("\"SxFy [W] items.\" parse failed");
		}
		
		try {
			int strm = Integer.parseInt(m.group(GROUP_STREAM));
			int func = Integer.parseInt(m.group(GROUP_FUNCTION));
			boolean wbit = ! m.group(GROUP_WBIT).isEmpty();
			String secs2 = m.group(GROUP_SECS2);
			
			return new SmlMessage(strm, func, wbit
					, getSmlSecs2Parser().parse(secs2));
		}
		catch ( NumberFormatException e) {
			throw new SmlParseException("SxFy parse failed", e);
		}
	}
	
	private String trimPeriod(CharSequence cs) throws SmlParseException {
		String s = cs.toString().replaceAll("\\r\\n|\\r|\\n|\\t", " ").trim();
		
		if ( s.endsWith(".") ) {
			
			return  s.substring(0, s.length() - 1).trim();
			
		} else {
			
			throw new SmlParseException("not end \".\"");
		}
	}

}
