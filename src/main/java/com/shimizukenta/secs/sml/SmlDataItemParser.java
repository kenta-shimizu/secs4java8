package com.shimizukenta.secs.sml;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.sml.impl.AbstractSmlDataItemParser;

/**
 * This interface is implementation of SML-SECS-II-Data-Parse.
 * 
 * @author kenta-shimizu
 *
 */
public interface SmlDataItemParser {

	/**
	 * parse to Secs2
	 * 
	 * @param cs SML-Format-Secs2-part-Character ({@code <A "ascii">})
	 * @return Secs2
	 * @throws SmlParseException if parse failed
	 */
	public Secs2 parse(CharSequence cs) throws SmlParseException;
	
	/**
	 * Returns SmlDataItemParser instance.
	 * 
	 * @return SmlDataItemParser instance
	 */
	public static SmlDataItemParser newInstance() {
		return new AbstractSmlDataItemParser() {};
	}
	
}