package com.shimizukenta.secs.gem;

import java.io.Serializable;
import java.math.BigInteger;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

public class DynamicCollectionEvent implements Serializable {
	
	private static final long serialVersionUID = -1354409024900428639L;
	
	private final String alias;
	private final Secs2 collectionEventId;
	
	protected DynamicCollectionEvent(CharSequence alias, Secs2 collectionEventId) {
		this.alias = (alias == null ? null : alias.toString());
		this.collectionEventId = collectionEventId;
	}
	
	public String alias() {
		return alias;
	}
	
	public Secs2 collectionEventId() {
		return collectionEventId;
	}
	
	private BigInteger bigInteger() throws Secs2Exception {
		return collectionEventId.getBigInteger(0);
	}
	
	@Override
	public int hashCode() {
		try {
			return bigInteger().hashCode();
		}
		catch ( Secs2Exception giveup ) {
			return collectionEventId.hashCode();
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if ( o != null && (o instanceof DynamicCollectionEvent) ) {
			try {
				return ((DynamicCollectionEvent)o).bigInteger().equals(bigInteger());
			}
			catch ( Secs2Exception giveup ) {
			}
		}
		return false;
	}
}
