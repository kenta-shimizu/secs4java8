package com.shimizukenta.secs.gem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

public class DynamicLink implements Serializable {
	
	private static final long serialVersionUID = 2780263993430315803L;
	
	private final DynamicCollectionEvent collectionEvent;
	private final List<Secs2> reportIds;
	
	protected DynamicLink(DynamicCollectionEvent collectionEvent, List<? extends Secs2> reportIds) {
		this.collectionEvent = collectionEvent;
		this.reportIds = new ArrayList<>(reportIds);
	}
	
	/**
	 * newInstance from S2F35-Secs2-Single-Link.<br />
	 * Single-Link-Format:<br />
	 * &lt;L [2]<br />
	 * &nbsp;&nbsp;&lt;U4 collection-event-id&gt;<br />
	 * &nbsp;&nbsp;&lt;L [n]<br />
	 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;U4 report-id-1&gt;<br />
	 * &nbsp;&nbsp;&nbsp;&nbsp;...<br />
	 * &nbsp;&nbsp;&lt;<br />
	 * &gt;.
	 * 
	 * @param S2F35 Secs2 Single-Link
	 * @return DynamicLink
	 * @throws Secs2Exception
	 */
	public static DynamicLink fromS2F35Link(Secs2 secs2) throws Secs2Exception {
		DynamicCollectionEvent ce = new DynamicCollectionEvent(null, secs2.get(0));
		List<Secs2> rptids = secs2.get(1).stream().collect(Collectors.toList());
		return new DynamicLink(ce, rptids);
	}
	
	/**
	 * to S2F35-Secs2-Single-Link.<br />
	 * Single-Link-Format:<br />
	 * &lt;L [2]<br />
	 * &nbsp;&nbsp;&lt;U4 collection-event-id&gt;<br />
	 * &nbsp;&nbsp;&lt;L [n]<br />
	 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;U4 report-id-1&gt;<br />
	 * &nbsp;&nbsp;&nbsp;&nbsp;...<br />
	 * &nbsp;&nbsp;&lt;<br />
	 * &gt;.
	 * 
	 * @return S2F35-single-link
	 */
	public Secs2 toS2F35Link() {
		return Secs2.list(
				collectionEvent.collectionEventId(),
				Secs2.list(reportIds));
	}
	
	public DynamicCollectionEvent collectionEvent() {
		return collectionEvent;
	}
	
	public Secs2 collectionEventId() {
		return collectionEvent.collectionEventId();
	}
	
	public List<Secs2> reportIds() {
		return Collections.unmodifiableList(reportIds);
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
