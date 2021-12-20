package com.shimizukenta.secs.sml;

import com.shimizukenta.secs.secs2.Secs2;

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
	 * @param cs SML-Format-Secs2-part-Character (<A "ascii">)
	 * @return Secs2
	 * @throws SmlParseException
	 */
	public Secs2 parse(CharSequence cs) throws SmlParseException;
	
	public static SmlDataItemParser newInstance() {
		return new AbstractSmlDataItemParser() {};
	}
	
}