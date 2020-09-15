package com.shimizukenta.secs.gem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.shimizukenta.secs.secs2.Secs2;

public class DynamicLink implements Serializable {
	
	private static final long serialVersionUID = 2780263993430315803L;
	
	private final DynamicCollectionEvent collectionEvent;
	private final List<Secs2> reports;
	
	public DynamicLink(DynamicCollectionEvent collectionEvent, List<? extends Secs2> reports) {
		this.collectionEvent = collectionEvent;
		this.reports = new ArrayList<>(reports);
	}
	
	public Secs2 s2f35() {
		return Secs2.list(
				collectionEvent.collectionEventId(),
				Secs2.list(reports));
	}
	
	@Override
	public int hashCode() {
		return collectionEvent.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if ( o != null && (o instanceof DynamicLink) ) {
			return ((DynamicLink)o).collectionEvent.equals(collectionEvent);
		}
		return false;
	}
}
