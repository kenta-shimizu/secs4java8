package com.shimizukenta.secs.sml;

import java.io.Serializable;

import com.shimizukenta.secs.secs2.Secs2;

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
	
	public int getStream() {
		return strm;
	}
	
	public int getFunction() {
		return func;
	}
	
	public boolean wbit() {
		return wbit;
	}
	
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
