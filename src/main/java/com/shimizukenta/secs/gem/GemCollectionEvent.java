package com.shimizukenta.secs.gem;

import java.math.BigInteger;
import java.util.Objects;

import com.shimizukenta.secs.secs2.AbstractSecs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

public class GemCollectionEvent {
	
	private AbstractSecs2 eventId;
	private String alias;
	
	public GemCollectionEvent(AbstractSecs2 eventId, CharSequence alias) {
		this.eventId = Objects.requireNonNull(eventId);
		this.alias = Objects.requireNonNull(alias).toString();
	}
	
	public GemCollectionEvent(AbstractSecs2 eventId) {
		this(eventId, "");
	}
	
	public GemCollectionEvent(int eventId, CharSequence alias) {
		this(AbstractSecs2.uint4(eventId), alias);
	}
	
	public GemCollectionEvent(int eventId) {
		this(eventId, "");
	}
	
	public String alias() {
		return alias;
	}
	
	public BigInteger eventId() throws Secs2Exception {
		return eventId.getBigInteger(0);
	}
	
	public AbstractSecs2 secs2() {
		return eventId;
	}
	
	@Override
	public int hashCode() {
		try {
			return eventId().hashCode();
		}
		catch ( Secs2Exception e ) {
			return e.hashCode();
		}
	}
	
	@Override
	public boolean equals(Object o) {
		
		if ( o instanceof GemCollectionEvent ) {
			
			try {
				return ((GemCollectionEvent) o).eventId().equals(eventId());
			}
			catch ( Secs2Exception e ) {
				return false;
			}
			
		} else {
			
			return false;
		}
	}
	
}
