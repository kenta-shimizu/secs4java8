package com.shimizukenta.secs.gem;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.shimizukenta.secs.secs2.Secs2Exception;

public class GemEventReportLinkBuilder {
	
	private final List<GemCollectionEvent> events = new CopyOnWriteArrayList<>();
	private final List<GemReport> reports = new CopyOnWriteArrayList<>();
	private final Map<String, List<String>> linkMap = new ConcurrentHashMap<>();
	
	public GemEventReportLinkBuilder() {
		/* Nothing */
	}
	
	public boolean addCollectionEvent(GemCollectionEvent ce) {
		return events.add(ce);
	}
	
	public boolean removeCollectionEvent(GemCollectionEvent ce) {
		return events.remove(ce);
	}
	
	public List<GemCollectionEvent> collectionEvents() {
		return Collections.unmodifiableList(events);
	}
	
	public Optional<GemCollectionEvent> getCollectionEvent(BigInteger n) {
		
		Objects.requireNonNull(n);
		
		return events.stream()
				.filter(ev -> {
					try {
						return ev.eventId().equals(n);
					}
					catch ( Secs2Exception e ) {
						return false;
					}
				})
				.findFirst();
	}
	
	public Optional<GemCollectionEvent> getCollectionEvent(CharSequence alias) {
		
		String s = Objects.requireNonNull(alias).toString();
		
		return events.stream()
				.filter(ev -> ! ev.alias().isEmpty())
				.filter(ev -> ev.alias().equals(s))
				.findFirst();
	}
	
	public boolean addReport(GemReport r) {
		return reports.add(r);
	}
	
	public boolean removeReport(GemReport r) {
		return reports.remove(r);
	}
	
	public List<GemReport> reports() {
		return Collections.unmodifiableList(reports);
	}
	
	public Optional<GemReport> getReport(BigInteger n) {
		
		Objects.requireNonNull(n);
		
		return reports.stream()
				.filter(r -> {
					try {
						return r.reportId().equals(n);
					}
					catch ( Secs2Exception e ) {
						return false;
					}
				})
				.findFirst();
	}
	
	public Optional<GemReport> getReport(CharSequence alias) {
		
		String s = Objects.requireNonNull(alias).toString();
		
		return reports.stream()
				.filter(r -> ! r.alias().isEmpty())
				.filter(r -> r.alias().equals(s))
				.findFirst();
	}
	
	public void addLink(CharSequence eventAlias, CharSequence reportAlias) {
		String key = Objects.requireNonNull(eventAlias).toString();
		String value = Objects.requireNonNull(reportAlias).toString();
		if ( key.isEmpty() ) return;
		if ( value.isEmpty() ) return;
		linkMap.computeIfAbsent(key, k -> new CopyOnWriteArrayList<>()).add(value);
	}
	
	public void removeLink(CharSequence eventAlias, CharSequence reportAlias) {
		String key = Objects.requireNonNull(eventAlias).toString();
		String value = Objects.requireNonNull(reportAlias).toString();
		if ( key.isEmpty() ) return;
		List<String> vv = linkMap.get(key);
		if ( vv != null) {
			vv.remove(value);
		}
	}
	
	public List<GemCollectionEventReportLink> links() {
		
		List<GemCollectionEventReportLink> ll = new ArrayList<>();
		
		linkMap.forEach((key, values) -> {
			
			events.stream()
			.filter(ev -> ev.alias().equals(key))
			.forEach(ge -> {
				
				List<GemReport> rpts = new ArrayList<>();
				
				values.forEach(v -> {
					
					reports.stream()
					.filter(r -> r.alias().equals(v))
					.forEach(rpts::add);
				});
				
				ll.add(new GemCollectionEventReportLink(ge, rpts));
				
			});
		});
		
		return Collections.unmodifiableList(ll);
	}
	
}
