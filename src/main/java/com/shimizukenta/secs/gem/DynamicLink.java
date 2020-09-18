package com.shimizukenta.secs.gem;

import java.util.List;
import java.util.stream.Collectors;

import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

/**
 * This interface is implementation of Event-Report-Link in GEM (SEMI-E30)<br />
 * To get CEID, {@link #collectionEventId()}<br />
 * To get RPTIDs, {@link #reportIds()}<br />
 * To S2F35 Single Link, {@link #toS2F35Link()}
 * Instances of this class are immutable.
 * 
 * @author kenta-shimizu
 *
 */
public interface DynamicLink {
	
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
	public Secs2 toS2F35Link();
	
	/**
	 * getter Collection-Event
	 * 
	 * @return DynamicCollectionEvent
	 */
	public DynamicCollectionEvent collectionEvent();
	
	/**
	 * getter CEID SECS-II
	 * 
	 * @return CEID SECS-II
	 */
	public Secs2 collectionEventId();
	
	/**
	 * getter RPTIDs
	 * 
	 * @return List<Secs2> of RPTIDs
	 */
	public List<Secs2> reportIds();
	
	
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
		AbstractDynamicCollectionEvent ce = new SimpleDynamicCollectionEvent(null, secs2.get(0));
		List<Secs2> rptids = secs2.get(1).stream().collect(Collectors.toList());
		return new SimpleDynamicLink(ce, rptids);
	}
	

}
