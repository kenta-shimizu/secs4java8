package com.shimizukenta.secs.gem;

import java.util.List;
import java.util.stream.Collectors;

import com.shimizukenta.secs.secs2.Secs2;

public class SimpleDynamicEventReportConfig extends AbstractDynamicEventReportConfig {
	
	private static final long serialVersionUID = -1826433965205479540L;
	
	private final SimpleGem gem;
	
	public SimpleDynamicEventReportConfig(SimpleGem gem) {
		super(gem);
		this.gem = gem;
	}
	
	@Override
	protected AbstractDynamicReport createReport(Secs2 reportId, CharSequence alias, List<? extends Number> vids) {
		List<Secs2> vv = vids.stream()
				.map(Number::longValue)
				.map(gem::vId)
				.collect(Collectors.toList());
		return new SimpleDynamicReport(reportId, alias, vv);
	}
	
	@Override
	protected AbstractDynamicCollectionEvent createCollectionEvent(CharSequence alias, long ceid) {
		return new SimpleDynamicCollectionEvent(alias, gem.collectionEventId(ceid));
	}
	
	@Override
	protected AbstractDynamicLink createLink(DynamicCollectionEvent ce, List<? extends Secs2> reports) {
		return new SimpleDynamicLink(ce, reports);
	}
	
}
