package com.shimizukenta.secs.gem;

import java.util.Optional;

import com.shimizukenta.secs.secs2.Secs2;

/**
 * This interface is implementation of Enable-CEID in GEM (SEMI-E30).
 * 
 * <p>
 * To get alias, {@link #alias()}<br />
 * To get CEID, {@link #collectionEventId()}<br />
 * To S2F37 Single CEID, {@link #toS2F37CollectionEvent()}<br />
 * </p>
 * <p>
 * Instances of this class are immutable.
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public interface DynamicCollectionEvent {
	
	/**
	 * Returns new instance.
	 * 
	 * @param alias
	 * @param collectionEventId
	 * @return newInstance
	 */
	public static DynamicCollectionEvent newInstance(CharSequence alias, Secs2 collectionEventId) {
		
		return new AbstractDynamicCollectionEvent(alias, collectionEventId) {
			private static final long serialVersionUID = 3280261182801909513L;
		};
	}
	
	/**
	 * Returns Alias
	 * 
	 * @return has valus if aliased.
	 */
	public Optional<String> alias();
	
	/**
	 * CEID getter to S2F37.
	 * 
	 * @return SECS-II CEID
	 */
	public Secs2 toS2F37CollectionEvent();
	
	/**
	 * Returns CEID
	 * 
	 * @return SECS-II CEID
	 */
	public Secs2 collectionEventId();
	
	
	/**
	 * newInstance from S2F37 Secs2 Single-Collection-Event.
	 * 
	 * <p>
	 * Single-Collection-Event-Format:<br />
	 * &lt;U4 ceid&gt;
	 * </p>
	 * 
	 * @param secs2 S2F37 Secs2 Single-Collection-Event
	 * @return DynamicCollectionEvent
	 */
	public static DynamicCollectionEvent fromS2F37CollectionEvent(Secs2 secs2) {
		return newInstance(null, secs2);
	}
	
}
