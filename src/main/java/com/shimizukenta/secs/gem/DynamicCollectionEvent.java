package com.shimizukenta.secs.gem;

import java.io.Serializable;
import java.util.Optional;

import com.shimizukenta.secs.secs2.Secs2;

public class DynamicCollectionEvent implements Serializable {
	
	private static final long serialVersionUID = -1354409024900428639L;
	
	private final String alias;
	private final Secs2 collectionEventId;
	
	protected DynamicCollectionEvent(CharSequence alias, Secs2 collectionEventId) {
		this.alias = (alias == null ? null : alias.toString());
		this.collectionEventId = collectionEventId;
	}
	
	/**
	 * Alias getter
	 * 
	 * @return has valus if aliased.
	 */
	public Optional<String> alias() {
		return alias == null ? Optional.empty() : Optional.of(alias);
	}
	
	/**
	 * newInstance from S2F37 Secs2 Single-Collection-Event.<br />
	 * Single-Collection-Event-Format:<br />
	 * &lt;U4 ceid&gt;
	 * 
	 * @param S2F37 Secs2 Single-Collection-Event
	 * @return DynamicCollectionEvent
	 */
	public static DynamicCollectionEvent fromS2F37CollectionEvent(Secs2 secs2) {
		return new DynamicCollectionEvent(null, secs2);
	}
	
	public Secs2 toS2F37CollectionEvent() {
		return collectionEventId;
	}
	
	/**
	 * CEID getter
	 * 
	 * @return SECS-II CEID
	 */
	public Secs2 collectionEventId() {
		return collectionEventId;
	}
	
	@Override
	public int hashCode() {
		return collectionEventId.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if ( o != null && (o instanceof DynamicCollectionEvent) ) {
			return ((DynamicCollectionEvent)o).collectionEventId.equals(collectionEventId);
		}
		return false;
	}
	
}
