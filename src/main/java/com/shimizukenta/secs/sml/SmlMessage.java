package com.shimizukenta.secs.sml;

import com.shimizukenta.secs.secs2.Secs2;

/**
 * This interface is implementation of SML (Peer-Group).
 * 
 * <p>
 * This instance is creatad from {@link SmlMessageParser#parse(CharSequence)}<br />
 * </p>
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
	int getStream();

	/**
	 * Returns SML SECS-II-Function-Number.
	 * 
	 * @return function-number
	 */
	int getFunction();

	/**
	 * Returns SML SECS-II-WBit.
	 * 
	 * @return {@code true} if Wbit is {@code 1}
	 */
	boolean wbit();

	/**
	 * Returns SML SECS-II-Data.
	 * 
	 * @return SECS-II-Data
	 */
	Secs2 secs2();

}