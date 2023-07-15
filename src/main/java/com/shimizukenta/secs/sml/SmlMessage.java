package com.shimizukenta.secs.sml;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;

import com.shimizukenta.secs.secs2.Secs2;

/**
 * This interface is implementation of SML (Peer-Group).
 * 
 * <ul>
 * <li>This instance get from {@link SmlMessage#of(CharSequence)}</li>
 * <li>This instance get from {@link SmlMessage#of(java.io.Reader)}</li>
 * <li>This instance get from {@link SmlMessage#of(java.nio.file.Path)}</li>
 * <li>This instance get from {@link SmlMessageParser#parse(CharSequence)}</li>
 * </ul>
 * <ul>
 * <li>To get SECS-II-Stream-Number, {@link #getStream()}</li>
 * <li>To get SECS-II-Function-Number, {@link #getFunction()}</li>
 * <li>To get SECS-II-WBit, {@link #wbit()}</li>
 * <li>To get SECS-II-Data, {@link #secs2()}</li>
 * </ul>
 * <p>
 * Instances of this class are immutable.<br />
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public interface SmlMessage {

	/**
	 * Returns SML SECS-II-Stream-Number.
	 * 
	 * @return stream-number
	 */
	public int getStream();

	/**
	 * Returns SML SECS-II-Function-Number.
	 * 
	 * @return function-number
	 */
	public int getFunction();

	/**
	 * Returns SML SECS-II-WBit.
	 * 
	 * @return {@code true} if Wbit is {@code 1}
	 */
	public boolean wbit();

	/**
	 * Returns SML SECS-II-Data.
	 * 
	 * @return SECS-II-Data
	 */
	public Secs2 secs2();
	
	/**
	 * Returns SmlMessage instance of character sequence.
	 * 
	 * @param cs the SML character sequence
	 * @return SML-Message instance.
	 * @throws SmlParseException if parse failed
	 */
	public static SmlMessage of(CharSequence cs) throws SmlParseException {
		return SmlMessageParser.getInstance().parse(cs);
	}
	
	/**
	 * Returns SmlMessage instance of Reader.
	 * 
	 * @param reader the reader
	 * @return SmlMessage
	 * @throws SmlParseException if parse failed
	 * @throws IOException if IO failed
	 */
	public static SmlMessage of(Reader reader) throws SmlParseException, IOException {
		return SmlMessageParser.getInstance().parse(reader);
	}
	
	/**
	 * Returns SmlMessage instance of File-path.
	 * 
	 * @param path SML file path
	 * @return SmlMessage
	 * @throws SmlParseException if parse failed
	 * @throws IOException if IO failed
	 */
	public static SmlMessage of(Path path) throws SmlParseException, IOException {
		return SmlMessageParser.getInstance().parse(path);
	}

	/**
	 * Returns SmlMessage instance from character sequence.
	 * 
	 * @param cs the SML character sequence
	 * @return SML-Message instance.
	 * @throws SmlParseException if parse failed
	 */
	@Deprecated
	public static SmlMessage from(CharSequence cs) throws SmlParseException {
		return SmlMessageParser.getInstance().parse(cs);
	}
	
	/**
	 * Returns SmlMessage instance from Reader.
	 * 
	 * @param reader the reader
	 * @return SmlMessage
	 * @throws SmlParseException if parse failed
	 * @throws IOException if IO failed
	 */
	@Deprecated
	public static SmlMessage from(Reader reader) throws SmlParseException, IOException {
		return SmlMessageParser.getInstance().parse(reader);
	}
	
	/**
	 * Returns SmlMessage instance from File-path.
	 * 
	 * @param path the SMl file path
	 * @return SmlMessage
	 * @throws SmlParseException if parse failed
	 * @throws IOException if IO failed
	 */
	@Deprecated
	public static SmlMessage from(Path path) throws SmlParseException, IOException {
		return SmlMessageParser.getInstance().parse(path);
	}

}
