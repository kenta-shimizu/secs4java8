package com.shimizukenta.secs.gem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.shimizukenta.secs.secs2.Secs2;

public class DynamicLink implements Serializable {
	
	private static final long serialVersionUID = 2780263993430315803L;
	
	private final DynamicCollectionEvent collectionEvent;
	private final List<DynamicReport> reports;
	
	public DynamicLink(DynamicCollectionEvent collectionEvent, List<DynamicReport> reports) {
		this.collectionEvent = collectionEvent;
		this.reports = new ArrayList<>(reports);
	}
	
	public Secs2 secs2() {
		List<Secs2> rr = reports.stream()
				.map(r -> r.reportId())
				.collect(Collectors.toList());
		
		return Secs2.list(
				collectionEvent.collectionEventId(),
				Secs2.list(rr));
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
