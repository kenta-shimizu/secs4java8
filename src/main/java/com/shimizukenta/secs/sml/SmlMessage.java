package com.shimizukenta.secs.sml;

import java.io.Serializable;

import com.shimizukenta.secs.secs2.Secs2;

/**
 * This class is implementation of SML (Peer-Group).
 * 
 * <p>
 * This instance is creatad from {@link SmlMessageParser#parse(CharSequence)}<br />
 * </p>
 * <p>
 * Instances of this class are immutable.<br />
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public class SmlMessage implements Serializable {
	
	private static final long serialVersionUID = 630517337022434278L;
	
	private final int strm;
	private final int func;
	private final boolean wbit;
	private final Secs2 secs2;
	
	protected SmlMessage(int strm, int func, boolean wbit, Secs2 secs2) {
		this.strm = strm;
		this.func = func;
		this.wbit = wbit;
		this.secs2 = secs2;
	}
	
	/**
	 * Returns SML SECS-II-Stream-Number.
	 * 
	 * @return stream-number
	 */
	public int getStream() {
		return strm;
	}
	
	/**
	 * Returns SML SECS-II-Function-Number.
	 * 
	 * @return function-number
	 */
	public int getFunction() {
		return func;
	}
	
	/**
	 * Returns SML SECS-II-WBit.
	 * 
	 * @return {@code true} if Wbit is {@code 1}
	 */
	public boolean wbit() {
		return wbit;
	}
	
	/**
	 * Returns SML SECS-II-Data.
	 * 
	 * @return SECS-II-Data
	 */
	public Secs2 secs2() {
		return secs2;
	}
	
	private static final String BR = System.lineSeparator();
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder("S")
				.append(strm)
				.append("F")
				.append(func);
		
		if (wbit) {
			sb.append(" W");
		}
		
		String body = secs2.toString();
		
		if ( ! body.isEmpty() ) {
			sb.append(BR).append(body);
		}
		
		return sb.append(".").toString();
	}
}
