package com.shimizukenta.secs.gem;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.shimizukenta.secs.secs2.AbstractSecs2;

public class GemCollectionEventReportLink {
	
	private final GemCollectionEvent ce;
	private final List<GemReport> reports;
	
	public GemCollectionEventReportLink(GemCollectionEvent ce, List<GemReport> reports) {
		this.ce = Objects.requireNonNull(ce);
		this.reports = Collections.unmodifiableList(Objects.requireNonNull(reports));
	}
	
	public AbstractSecs2 secs2() {
		
		List<AbstractSecs2> rpts = reports.stream()
				.map(r -> r.reportIdSecs2())
				.collect(Collectors.toList());
		
		return AbstractSecs2.list(
				ce.secs2()
				, AbstractSecs2.list(rpts));
	}

}
