package com.shimizukenta.secs.gem;

import java.util.Objects;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;
import com.shimizukenta.secs.secs2.Secs2IllegalDataFormatException;
import com.shimizukenta.secs.secs2.Secs2Item;

/**
 * CEED.
 * 
 * @author kenta-shimizu
 *
 */
public enum CEED {
	
	/**
	 * ENABLE.
	 * 
	 */
	ENABLE(Secs2.bool(true)),
	
	/**
	 * DISABLE.
	 * 
	 */
	DISABLE(Secs2.bool(false)),
	
	;
	
	private final Secs2 v;
	private CEED(Secs2 v) {
		this.v = v;
	}
	
	/**
	 * Returns Secs2 of CEED.
	 * 
	 * @return Secs2 of CEED
	 */
	public Secs2 secs2() {
		return v;
	}
	
	/**
	 * Returns CEED from Secs2.
	 * 
	 * @param value the Secs2
	 * @return CEED
	 * @throws Secs2Exception if parse failed
	 */
	public static CEED get(Secs2 value) throws Secs2Exception {
		
		if ( value.secs2Item() == Secs2Item.BOOLEAN ) {
			
			if ( Objects.equals(value, CEED.DISABLE.secs2()) ) {
				return CEED.DISABLE;
			} else {
				return CEED.ENABLE;
			}
			
		} else {
			
			throw new Secs2IllegalDataFormatException("CEED require BOOLEAN");
		}
	}
}
