package com.shimizukenta.secs.gem;

import java.util.List;

import com.shimizukenta.secs.secs2.Secs2;

public class SimpleDynamicLink extends AbstractDynamicLink {
	
	private static final long serialVersionUID = 7815641776328684569L;
	
	public SimpleDynamicLink(DynamicCollectionEvent collectionEvent, List<? extends Secs2> reportIds) {
		super(collectionEvent, reportIds);
	}
	
}
