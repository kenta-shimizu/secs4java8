package com.shimizukenta.secs.gem.impl;

import java.io.Serializable;
import java.util.Optional;

import com.shimizukenta.secs.gem.DynamicCollectionEvent;
import com.shimizukenta.secs.secs2.Secs2;

public abstract class AbstractDynamicCollectionEvent implements DynamicCollectionEvent, Serializable {
	
	private static final long serialVersionUID = -1354409024900428639L;
	
	private final String alias;
	private final Secs2 collectionEventId;
	
	public AbstractDynamicCollectionEvent(CharSequence alias, Secs2 collectionEventId) {
		this.alias = (alias == null ? null : alias.toString());
		this.collectionEventId = collectionEventId;
	}
	
	@Override
	public Optional<String> alias() {
		return alias == null ? Optional.empty() : Optional.of(alias);
	}
	
	@Override
	public Secs2 toS2F37CollectionEvent() {
		return collectionEventId;
	}
	
	@Override
	public Secs2 collectionEventId() {
		return collectionEventId;
	}
	
	@Override
	public int hashCode() {
		return collectionEventId.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if ( o != null && (o instanceof AbstractDynamicCollectionEvent) ) {
			return ((AbstractDynamicCollectionEvent)o).collectionEventId.equals(collectionEventId);
		}
		return false;
	}
	
}
