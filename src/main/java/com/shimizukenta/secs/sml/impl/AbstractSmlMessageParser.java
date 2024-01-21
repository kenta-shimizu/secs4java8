package com.shimizukenta.secs.sml.impl;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.shimizukenta.secs.sml.SmlDataItemParser;
import com.shimizukenta.secs.sml.SmlMessage;
import com.shimizukenta.secs.sml.SmlMessageParser;
import com.shimizukenta.secs.sml.SmlNotFoundEndPeriodException;
import com.shimizukenta.secs.sml.SmlParseException;
import com.shimizukenta.secs.sml.SmlParseFunctionOutOfRangeException;
import com.shimizukenta.secs.sml.SmlParseStreamOutOfRangeException;

abstract public class AbstractSmlMessageParser implements SmlMessageParser {

	public AbstractSmlMessageParser() {
		/* Nothing */
	}
	
	private final SmlDataItemParser smlDataItemParser = SmlDataItemParser.newInstance();
	
	protected SmlDataItemParser getSmlSecs2Parser() {
		return this.smlDataItemParser;
	}
	
	protected static final String GROUP_STREAM = "STREAM";
	protected static final String GROUP_FUNCTION = "FUNCTION";
	protected static final String GROUP_WBIT = "WBIT";
	protected static final String GROUP_SECS2 = "SECS2";
	protected static final String pregMessage = "[Ss](?<" + GROUP_STREAM + ">[0-9]{1,3})[Ff](?<" + GROUP_FUNCTION + ">[0-9]{1,3})\\s*(?<" + GROUP_WBIT + ">[Ww]?)\\s*(?<" + GROUP_SECS2 + ">(<.+>)?)";
	
	protected static final Pattern ptnMessage = Pattern.compile("^" + pregMessage + "$");

	/**
	 * parse to SML-Message.
	 * 
	 * @param cs SML-Format-Character
	 * @return SmlMessage
	 * @throws SmlParseException
	 */
	@Override
	public SmlMessage parse(CharSequence cs) throws SmlParseException  {
		
		Objects.requireNonNull(cs);
		
		String s = trimPeriod(cs);
		
		Matcher m = ptnMessage.matcher(s);
		
		if ( ! m.matches() ) {
			throw new SmlParseException("\"SxFy [W] items.\" parse failed");
		}
		
		try {
			int strm = Integer.parseInt(m.group(GROUP_STREAM));
			if (strm > 0x0000007F) {
				throw new SmlParseStreamOutOfRangeException(strm);
			}
			
			int func = Integer.parseInt(m.group(GROUP_FUNCTION));
			if (func > 0x000000FF) {
				throw new SmlParseFunctionOutOfRangeException(func);
			}
			
			boolean wbit = ! m.group(GROUP_WBIT).isEmpty();
			String secs2 = m.group(GROUP_SECS2);
			
			return new AbstractSmlMessage(
					strm,
					func,
					wbit,
					this.getSmlSecs2Parser().parse(secs2)) {

						private static final long serialVersionUID = 5278030007936351049L;
			};
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
			
			throw new SmlNotFoundEndPeriodException();
		}
	}

}
