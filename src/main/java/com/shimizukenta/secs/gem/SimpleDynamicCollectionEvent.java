package com.shimizukenta.secs.gem;

import com.shimizukenta.secs.secs2.Secs2;

public class SimpleDynamicCollectionEvent extends AbstractDynamicCollectionEvent {
	
	private static final long serialVersionUID = -1018411326191974482L;
	
	public SimpleDynamicCollectionEvent(CharSequence alias, Secs2 collectionEventId) {
		super(alias, collectionEventId);
	}
	
}
