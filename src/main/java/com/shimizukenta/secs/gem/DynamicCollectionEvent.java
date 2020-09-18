package com.shimizukenta.secs.gem;

import java.util.Optional;

import com.shimizukenta.secs.secs2.Secs2;

/**
 * This interface is implementation of Enable-CEID in GEM (SEMI-E30)<br />
 * To get alias, {@link #alias()}<br />
 * To get CEID, {@link #collectionEventId()}<br />
 * To S2F37 Single CEID, {@link #toS2F37CollectionEvent()}<br />
 * Instances of this class are immutable.
 * 
 * @author kenta-shimizu
 *
 */
public interface DynamicCollectionEvent {
	
	/**
	 * Alias getter
	 * 
	 * @return has valus if aliased.
	 */
	public Optional<String> alias();
	
	/**
	 * CEID getter to S2F37
	 * 
	 * @return SECS-II CEID
	 */
	public Secs2 toS2F37CollectionEvent();
	
	/**
	 * CEID getter
	 * 
	 * @return SECS-II CEID
	 */
	public Secs2 collectionEventId();
	
	
	/**
	 * newInstance from S2F37 Secs2 Single-Collection-Event.<br />
	 * Single-Collection-Event-Format:<br />
	 * &lt;U4 ceid&gt;
	 * 
	 * @param S2F37 Secs2 Single-Collection-Event
	 * @return DynamicCollectionEvent
	 */
	public static DynamicCollectionEvent fromS2F37CollectionEvent(Secs2 secs2) {
		return new SimpleDynamicCollectionEvent(null, secs2);
	}
	
}
