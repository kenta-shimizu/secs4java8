package com.shimizukenta.secs.sml;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;

import com.shimizukenta.secs.sml.impl.SmlMessageParsers;

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
	 * Parse to SML-Message from character sequence.
	 * 
	 * @param cs SML-Format-Character-Sequence
	 * @return SmlMessage
	 * @throws SmlParseException if parse failed
	 */
	public SmlMessage parse(CharSequence cs) throws SmlParseException;
	
	/**
	 * Parse to SML-Message from Reader.
	 * 
	 * @param reader SML-Format-Reader
	 * @return SMLMessage
	 * @throws SmlParseException if parse failed
	 * @throws IOException if IO failed
	 */
	public SmlMessage parse(Reader reader) throws SmlParseException, IOException;
	
	/**
	 * Parse to SML-Message from File Path.
	 * 
	 * @param path SML-Format-File-Path
	 * @return SmlMessage
	 * @throws SmlParseException if parse failed
	 * @throws IOException if IO failed
	 */
	public SmlMessage parse(Path path) throws SmlParseException, IOException;
	
	/**
	 * Parser instance getter.
	 * 
	 * @return parser
	 */
	public static SmlMessageParser getInstance() {
		return SmlMessageParsers.getInstance();
	}
	
}