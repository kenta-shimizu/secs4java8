package com.shimizukenta.secs.gem;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

import com.shimizukenta.secs.secs2.Secs2;

public class DynamicEventReportConfig {
	
	private final AbstractGem gem;
	
	public DynamicEventReportConfig(AbstractGem gem) {
		this.gem = gem;
	}
	
	/* Report */
	private final Set<DynamicReport> reports = new CopyOnWriteArraySet<>();
	
	public DynamicReport addReport(long reportId, CharSequence alias, List<? extends Number> vids) {
		return addReport(gem.reportId(reportId), alias, vids);
	}
	
	public DynamicReport addReport(long reportId, List<? extends Number> vids) {
		return addReport(gem.reportId(reportId), null, vids);
	}
	
	public DynamicReport addReport(CharSequence alias, List<? extends Number> vids) {
		return addReport(gem.autoReportId(), alias, vids);
	}
	
	public DynamicReport addReport(List<Number> vids) {
		return addReport(gem.autoReportId(), null, vids);
	}
	
	private DynamicReport addReport(Secs2 reportId, CharSequence alias, List<? extends Number> vids) {
		List<Secs2> vv = vids.stream()
				.map(Number::longValue)
				.map(gem::vId)
				.collect(Collectors.toList());
		DynamicReport r = new DynamicReport(reportId, alias, vv);
		reports.add(r);
		return r;
	}
	
	public boolean removeReport(DynamicReport report) {
		return reports.remove(report);
	}
	
	public Optional<DynamicReport> getReport(CharSequence alias) {
		
		if ( alias == null ) {
			return Optional.empty();
		}
		
		String s = alias.toString();
		
		return reports.stream()
				.filter(r -> Objects.equals(r.alias(), s))
				.findFirst();
	}
	
	
	/* Collection-Event */
	private final Set<DynamicCollectionEvent> events = new CopyOnWriteArraySet<>();
	
	//TODO
	//add
	
	public boolean removeCollectionEvent(DynamicCollectionEvent ce) {
		return events.remove(ce);
	}
	
	public Optional<DynamicCollectionEvent> getCollectionEvent(CharSequence alias) {
		
		if ( alias == null ) {
			return Optional.empty();
		}
		
		String s = alias.toString();
		
		return events.stream()
				.filter(ce -> Objects.equals(ce.alias(), s))
				.findFirst();
	}
	
	
	/* Link */
	private final Set<DynamicLink> links = new CopyOnWriteArraySet<>();
	
	//TODO
	//link
	//add
	
	public boolean removeLink(DynamicLink link) {
		return links.remove(link);
	}
	
	
	//TODO
	//s2f33
	//s2f35
	//s2f37enables
	
}
