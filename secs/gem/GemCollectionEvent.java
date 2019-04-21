package secs.gem;

import java.math.BigInteger;

import secs.secs2.Secs2;
import secs.secs2.Secs2Exception;

public class GemCollectionEvent {
	
	private String alias;
	private Secs2 eventId;
	
	public GemCollectionEvent(CharSequence alias, Secs2 eventId) {
		this.alias = alias.toString();
		this.eventId = eventId;
	}
	
	public GemCollectionEvent(CharSequence alias, int eventId) {
		this(alias, Secs2.uint4(eventId));
	}
	
	public String alias() {
		return alias;
	}
	
	public BigInteger eventId() throws Secs2Exception {
		return eventId.getBigInteger(0);
	}
	
	public Secs2 secs2() {
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
