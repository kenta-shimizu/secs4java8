package com.shimizukenta.secs.sml;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

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
	 * @throws SmlParseException
	 */
	public SmlMessage parse(CharSequence cs) throws SmlParseException;
	
	/**
	 * Parse to SML-Message from Reader.
	 * 
	 * @param reader SML-Format-Reader
	 * @return SMLMessage
	 * @throws SmlParseException
	 * @throws IOException
	 */
	default public SmlMessage parse(Reader reader) throws SmlParseException, IOException {
		try (
				CharArrayWriter w = new CharArrayWriter();
				) {
			
			for ( ;; ) {
				int r = reader.read();
				
				if ( r < 0 ) {
					return parse(w.toString());
				}
				
				w.write(r);
			}
		}
	}
	
	/**
	 * Parse to SML-Message from File Path.
	 * 
	 * @param path SML-Format-File-Path
	 * @return SmlMessage
	 * @throws SmlParseException
	 * @throws IOException
	 */
	default public SmlMessage parse(Path path) throws SmlParseException, IOException {
		try (
				Reader r = Files.newBufferedReader(path);
				) {
			return parse(r);
		}
	}
	
	/**
	 * Parser instance getter.
	 * 
	 * @return parser
	 */
	public static SmlMessageParser getInstance() {
		return SmlMessageParsers.getInstance();
	}
	
}