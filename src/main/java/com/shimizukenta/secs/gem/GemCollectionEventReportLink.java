package com.shimizukenta.secs.gem;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.shimizukenta.secs.secs2.Secs2;

public class GemCollectionEventReportLink implements Serializable {
	
	private static final long serialVersionUID = -4924970300110178410L;
	
	private final GemCollectionEvent ce;
	private final List<GemReport> reports;
	
	public GemCollectionEventReportLink(GemCollectionEvent ce, List<GemReport> reports) {
		this.ce = Objects.requireNonNull(ce);
		this.reports = Collections.unmodifiableList(Objects.requireNonNull(reports));
	}
	
	public Secs2 secs2() {
		
		List<Secs2> rpts = reports.stream()
				.map(r -> r.reportIdSecs2())
				.collect(Collectors.toList());
		
		return Secs2.list(
				ce.secs2()
				, Secs2.list(rpts));
	}

}
