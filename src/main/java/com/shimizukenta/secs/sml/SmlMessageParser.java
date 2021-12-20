package com.shimizukenta.secs.sml;

/**
 * This instance is implementation of SML-Parse.
 * 
 * <ul>
 * <li>To get parser instance, {@link #getInstance()}</li>
 * <li>To parse SML, {@link #parse(CharSequence)}</li>
 * </ul>
 * 
 * @author kenta-shimizu
 *
 */
public interface SmlMessageParser {

	/**
	 * parse to SML-Message.
	 * 
	 * @param cs SML-Format-Character
	 * @return SmlMessage
	 * @throws SmlParseException
	 */
	public SmlMessage parse(CharSequence cs) throws SmlParseException;
	
	/**
	 * Parser instance getter.
	 * 
	 * @return parser
	 */
	public static SmlMessageParser getInstance() {
		return SmlMessageParsers.getInstance();
	}
	
}