package com.shimizukenta.secs.gem.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.gem.AliasNotFoundDynamicEventReportException;
import com.shimizukenta.secs.gem.CEED;
import com.shimizukenta.secs.gem.DRACK;
import com.shimizukenta.secs.gem.DynamicCollectionEvent;
import com.shimizukenta.secs.gem.DynamicEventReportConfig;
import com.shimizukenta.secs.gem.DynamicEventReportException;
import com.shimizukenta.secs.gem.DynamicLink;
import com.shimizukenta.secs.gem.DynamicReport;
import com.shimizukenta.secs.gem.ERACK;
import com.shimizukenta.secs.gem.LRACK;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

public abstract class AbstractDynamicEventReportConfig implements DynamicEventReportConfig, Serializable {
	
	private static final long serialVersionUID = 5357410952900950632L;
	
	private final AbstractGem gem;
	
	public AbstractDynamicEventReportConfig(AbstractGem gem) {
		this.gem = gem;
	}
	
	
	/**
	 * prototype-pattern
	 * 
	 * @param reportId
	 * @param alias
	 * @param vids
	 * @return DynamicReport instance
	 */
	protected DynamicReport createReport(Secs2 reportId, CharSequence alias, List<? extends Number> vids) {
		List<Secs2> vv = vids.stream()
				.map(Number::longValue)
				.map(gem::vId)
				.collect(Collectors.toList());
		return DynamicReport.newInstance(reportId, alias, vv);
	}
	
	/**
	 * prototype-pattern
	 * 
	 * @param alias
	 * @param ceid
	 * @return DynamicCollectionEvent instance
	 */
	protected DynamicCollectionEvent createCollectionEvent(CharSequence alias, long ceid) {
		return DynamicCollectionEvent.newInstance(alias, gem.collectionEventId(ceid));
	}
	
	/**
	 * prototype-pattern
	 * 
	 * @param ce
	 * @param reports
	 * @return DynamicLink
	 */
	protected DynamicLink createLink(DynamicCollectionEvent ce, List<? extends Secs2> reports) {
		return DynamicLink.newInstance(ce, reports);
	}
	
	/* Report */
	private final Set<DynamicReport> reports = new CopyOnWriteArraySet<>();
	
	@Override
	public DynamicReport addDefineReport(long reportId, CharSequence alias, List<? extends Number> vids) {
		return addDefineReport(gem.reportId(reportId), alias, vids);
	}
	
	@Override
	public DynamicReport addDefineReport(long reportId, List<? extends Number> vids) {
		return addDefineReport(gem.reportId(reportId), null, vids);
	}
	
	@Override
	public DynamicReport addDefineReport(CharSequence alias, List<? extends Number> vids) {
		return addDefineReport(gem.autoReportId(), alias, vids);
	}
	
	@Override
	public DynamicReport addDefineReport(List<? extends Number> vids) {
		return addDefineReport(gem.autoReportId(), null, vids);
	}
	
	private DynamicReport addDefineReport(Secs2 reportId, CharSequence alias, List<? extends Number> vids) {
		DynamicReport r = createReport(reportId, alias, vids);
		reports.add(r);
		return r;
	}
	
	@Override
	public boolean removeReport(DynamicReport report) {
		return reports.remove(report);
	}
	
	@Override
	public Optional<DynamicReport> getReport(CharSequence alias) {
		
		if ( alias == null ) {
			return Optional.empty();
		}
		
		String s = alias.toString();
		
		return reports.stream()
				.filter(r -> r.alias().isPresent())
				.filter(r -> Objects.equals(r.alias().get(), s))
				.findFirst();
	}
	
	@Override
	public Optional<DynamicReport> getReport(Secs2 reportId) {
		
		if ( reportId == null ) {
			return Optional.empty();
		}
		
		return reports.stream()
				.filter(r -> Objects.equals(r.reportId(), reportId))
				.findFirst();
	}
	
	/* Link */
	private final Set<DynamicLink> links = new CopyOnWriteArraySet<>();
	
	@Override
	public DynamicLink addLinkById(long ceid, List<? extends Number> reportIds) {
		return addLinkById(createCollectionEvent(null, ceid), reportIds);
	}
	
	@Override
	public DynamicLink addLinkById(DynamicCollectionEvent ce, List<? extends Number> reportIds) {
		List<Secs2> ss = reportIds.stream()
				.map(Number::longValue)
				.map(gem::reportId)
				.collect(Collectors.toList());
		return addLinkBySecs2(ce, ss);
	}
	
	@Override
	public DynamicLink addLinkByReport(long ceid, List<? extends DynamicReport> reports) {
		return addLinkByReport(createCollectionEvent(null, ceid), reports);
	}
	
	@Override
	public DynamicLink addLinkByReport(DynamicCollectionEvent ce, List<? extends DynamicReport> reports) {
		List<Secs2> ss = reports.stream()
				.map(r -> r.reportId())
				.collect(Collectors.toList());
		return addLinkBySecs2(ce, ss);
	}
	
	private DynamicLink addLinkBySecs2(DynamicCollectionEvent ce, List<? extends Secs2> reports) {
		DynamicLink link = createLink(ce, reports);
		links.add(link);
		return link;
	}
	
	@Override
	public boolean removeLink(DynamicLink link) {
		return links.remove(link);
	}
	
	
	/* Collection-Event */
	private final Set<DynamicCollectionEvent> events = new CopyOnWriteArraySet<>();
	
	@Override
	public DynamicCollectionEvent addEnableCollectionEvent(long ceid) {
		return addEnableCollectionEvent(null, ceid);
	}
	
	@Override
	public DynamicCollectionEvent addEnableCollectionEvent(CharSequence alias, long ceid) {
		DynamicCollectionEvent ce = createCollectionEvent(alias, ceid);
		events.add(ce);
		return ce;
	}
	
	@Override
	public boolean removeEnableCollectionEvent(DynamicCollectionEvent ce) {
		return events.remove(ce);
	}
	
	@Override
	public Optional<DynamicCollectionEvent> getCollectionEvent(CharSequence alias) {
		
		if ( alias == null ) {
			return Optional.empty();
		}
		
		String s = alias.toString();
		
		return events.stream()
				.filter(r -> r.alias().isPresent())
				.filter(ce -> Objects.equals(ce.alias().get(), s))
				.findFirst();
	}
	
	@Override
	public Optional<DynamicCollectionEvent> getCollectionEvent(Secs2 ceid) {
		
		if ( ceid == null ) {
			return Optional.empty();
		}
		
		return events.stream()
				.filter(ev -> Objects.equals(ev.collectionEventId(), ceid))
				.findFirst();
	}

	@Override
	public DRACK s2f33DeleteAll()
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException {
		
		return gem.s2f33DeleteAll();
	}
	
	@Override
	public DRACK s2f33Define()
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException {
		
		List<Secs2> rr = reports.stream()
				.map(r -> r.toS2F33Report())
				.collect(Collectors.toList());
		
		return gem.s2f33Inner(rr);
	}
	
	@Override
	public LRACK s2f35()
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException {
		
		List<Secs2> ll = links.stream()
				.map(l -> l.toS2F35Link())
				.collect(Collectors.toList());
		
		return gem.s2f35Inner(ll);
	}
	
	@Override
	public ERACK s2f37DisableAll()
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException {
		
		return gem.s2f37DisableAll();
	}
	
	@Override
	public ERACK s2f37EnableAll()
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException {
		
		return gem.s2f37EnableAll();
	}
	
	@Override
	public ERACK s2f37Enable()
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException {
		
		List<Secs2> ee = events.stream()
				.map(e -> e.collectionEventId())
				.collect(Collectors.toList());
		
		return gem.s2f37Inner(CEED.ENABLE, ee);
	}
	
	@Override
	public Optional<SecsMessage> s6f15(DynamicCollectionEvent ce)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return gem.s6f15(ce);
	}
	
	@Override
	public Optional<SecsMessage> s6f15(CharSequence alias)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, DynamicEventReportException
			, InterruptedException {
		
		DynamicCollectionEvent ce = this.getCollectionEvent(alias)
				.orElseThrow(() -> new AliasNotFoundDynamicEventReportException("\"" + Objects.toString(alias) + "\" not found"));
		return s6f15(ce);
	}
	
	@Override
	public Optional<SecsMessage> s6f17(DynamicCollectionEvent ce)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return gem.s6f17(ce);
	}
	
	@Override
	public Optional<SecsMessage> s6f17(CharSequence alias)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, DynamicEventReportException
			, InterruptedException {
		
		DynamicCollectionEvent ce = this.getCollectionEvent(alias)
				.orElseThrow(() -> new AliasNotFoundDynamicEventReportException("\"" + Objects.toString(alias) + "\" not found"));
		return s6f17(ce);
	}
	
	@Override
	public Optional<SecsMessage> s6f19(DynamicReport report)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return gem.s6f19(report);
	}
	
	@Override
	public Optional<SecsMessage> s6f19(CharSequence alias)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, DynamicEventReportException
			, InterruptedException {
		
		DynamicReport report = this.getReport(alias)
				.orElseThrow(() -> new AliasNotFoundDynamicEventReportException("\"" + Objects.toString(alias) + "\" not found"));
		return s6f19(report);
	}

	@Override
	public Optional<SecsMessage> s6f21(DynamicReport report)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return gem.s6f21(report);
	}
	
	@Override
	public Optional<SecsMessage> s6f21(CharSequence alias)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, DynamicEventReportException
			, InterruptedException {
		
		DynamicReport report = this.getReport(alias)
				.orElseThrow(() -> new AliasNotFoundDynamicEventReportException("\"" + Objects.toString(alias) + "\" not found"));
		return s6f21(report);
	}
	
}
