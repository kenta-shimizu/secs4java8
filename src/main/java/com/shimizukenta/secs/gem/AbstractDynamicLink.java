package com.shimizukenta.secs.gem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.shimizukenta.secs.secs2.Secs2;

public abstract class AbstractDynamicLink implements DynamicLink, Serializable {
	
	private static final long serialVersionUID = 2780263993430315803L;
	
	private final DynamicCollectionEvent collectionEvent;
	private final List<Secs2> reportIds;
	
	public AbstractDynamicLink(DynamicCollectionEvent collectionEvent, List<? extends Secs2> reportIds) {
		this.collectionEvent = collectionEvent;
		this.reportIds = new ArrayList<>(reportIds);
	}
	
	@Override
	public Secs2 toS2F35Link() {
		return Secs2.list(
				collectionEvent.collectionEventId(),
				Secs2.list(reportIds));
	}
	
	@Override
	public DynamicCollectionEvent collectionEvent() {
		return collectionEvent;
	}
	
	@Override
	public Secs2 collectionEventId() {
		return collectionEvent.collectionEventId();
	}
	
	@Override
	public List<Secs2> reportIds() {
		return Collections.unmodifiableList(reportIds);
	}
	
	@Override
	public int hashCode() {
		return collectionEvent.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if ( o != null && (o instanceof AbstractDynamicLink) ) {
			return ((AbstractDynamicLink)o).collectionEvent.equals(collectionEvent);
		}
		return false;
	}
}
