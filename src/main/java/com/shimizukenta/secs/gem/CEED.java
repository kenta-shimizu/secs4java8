package com.shimizukenta.secs.gem;

import java.util.Objects;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;
import com.shimizukenta.secs.secs2.Secs2IllegalDataFormatException;
import com.shimizukenta.secs.secs2.Secs2Item;

public enum CEED {
	
	ENABLE(Secs2.bool(true)),
	DISABLE(Secs2.bool(false)),
	
	;
	
	private final Secs2 v;
	private CEED(Secs2 v) {
		this.v = v;
	}
	
	public Secs2 secs2() {
		return v;
	}
	
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
