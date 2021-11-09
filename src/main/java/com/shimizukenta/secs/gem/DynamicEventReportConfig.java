package com.shimizukenta.secs.gem;

import java.util.List;
import java.util.Optional;

import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

/**
 * This interface is implementation of Dynamic-Event-Report-Configuration in GEM (SEMI-E30).
 * 
 * <p>
 * To create new instance, {@link Gem#newDynamicEventReportConfig()}<br />
 * </p>
 * <p>
 * To add Define-Report, {@link #addDefineReport(CharSequence, List)}<br />
 * To add Enable-CEID, {@link #addEnableCollectionEvent(CharSequence, long)}<br />
 * To add Link, {@link #addLinkByReport(DynamicCollectionEvent, List)}<br />
 * </p>
 * <p>
 * To S2F37 Disable-All-CEIDs, {@link #s2f37DisableAll()}<br />
 * To S2F33 Delete-All-Reports, {@link #s2f33DeleteAll()}<br />
 * To S2F33 Define-Reports, {@link #s2f33Define()}<br />
 * To S2F35 Link, {@link #s2f35()}<br />
 * To S2F37 Enable-CEIDs, {@link #s2f37Enable()}<br />
 * </p>
 * <p>
 * Relates: S2F33, S2F35, S2F37, S6F11, S6F13, S6F15, S6F17, S6F19, S6F21
 * </p>
 * 
 * @author kenta-shimizu
 *
 */
public interface DynamicEventReportConfig {
	
	/**
	 * Returns new instance.
	 * 
	 * @param gem
	 * @return newInstance
	 */
	public static DynamicEventReportConfig newInstance(AbstractGem gem) {
		
		return new AbstractDynamicEventReportConfig(gem) {
			private static final long serialVersionUID = -5375563226536904308L;
		};
	}
	
	/**
	 * Add Define-Report.
	 * 
	 * <p>
	 * Use for S2F33
	 * </p>
	 * 
	 * @param reportId
	 * @param alias of use in {@link #getReport(CharSequence)}, {@link #s6f19(CharSequence)}, {@link #s6f21(CharSequence)}
	 * @param vids
	 * @return DynamicReport
	 */
	public DynamicReport addDefineReport(long reportId, CharSequence alias, List<? extends Number> vids);
	
	/**
	 * Add Define-Report.
	 * 
	 * <p>
	 * Use for S2F33
	 * </p>
	 * 
	 * @param reportId
	 * @param vids
	 * @return DynamicReport
	 */
	public DynamicReport addDefineReport(long reportId, List<? extends Number> vids);
	
	/**
	 * Add Define-Report.
	 * 
	 * <p>
	 * Use for S2F33<br />
	 * Report-ID is AutoNumber<br />
	 * </p>
	 * 
	 * @param alias of use in {@link #getReport(CharSequence)}, {@link #s6f19(CharSequence)}, {@link #s6f21(CharSequence)}
	 * @param vids
	 * @return DynamicReport
	 */
	public DynamicReport addDefineReport(CharSequence alias, List<? extends Number> vids);
	
	/**
	 * Add Define-Report.
	 * 
	 * <p>
	 * Use for S2F33<br />
	 * Report-ID is AutoNumber.<br />
	 * </p>
	 * 
	 * @param vids
	 * @return DynamicReport
	 */
	public DynamicReport addDefineReport(List<? extends Number> vids);
	
	/**
	 * Remove Report
	 * 
	 * @param DynamicReport
	 * @return {@code true} if remove success
	 */
	public boolean removeReport(DynamicReport report);
	
	/**
	 * Seek in Define-Reports by alias.
	 * 
	 * @param alias
	 * @return DynamicReport if exist
	 */
	public Optional<DynamicReport> getReport(CharSequence alias);
	
	/**
	 * Seek in Define-Reports by report-id.
	 * 
	 * <p>
	 * Used for S6F11, S6F13, ...
	 * </p>
	 * 
	 * @param reportId
	 * @return DynamicReport if exist
	 */
	public Optional<DynamicReport> getReport(Secs2 reportId);
	
	/**
	 * Add Event-Report-Link.<br />
	 * Use for S2F35
	 * 
	 * @param Collection-Event-ID
	 * @param Report-IDs
	 * @return DynamicLink
	 */
	public DynamicLink addLinkById(long ceid, List<? extends Number> reportIds);
	
	/**
	 * Add Event-Report-Link.
	 * 
	 * <p>
	 * Use for S2F35.
	 * </p>
	 * 
	 * @param ce DynamicCollectionEvent
	 * @param reportIds Report-IDs
	 * @return DynamicLink
	 */
	public DynamicLink addLinkById(DynamicCollectionEvent ce, List<? extends Number> reportIds);
	
	/**
	 * Add Event-Report-Link.
	 * 
	 * <p>
	 * Use for S2F35.
	 * </p>
	 * 
	 * @param ceid Collection-Event-ID
	 * @param reports DynamicReports
	 * @return DynamicLink
	 */
	public DynamicLink addLinkByReport(long ceid, List<? extends DynamicReport> reports);
	
	/**
	 * Add Event-Report-Link.
	 * 
	 * <p>
	 * Use for S2F35.
	 * </p>
	 * 
	 * @param ce DynamicCollectionEvent
	 * @param reports DynamicReports
	 * @return DynamicLink
	 */
	public DynamicLink addLinkByReport(DynamicCollectionEvent ce, List<? extends DynamicReport> reports);
	
	/**
	 * Remove Link.
	 * 
	 * @param DynamicLink
	 * @return {@code true} if remove success
	 */
	public boolean removeLink(DynamicLink link);
	
	/**
	 * Add Enable-Collection-Event.
	 * 
	 * <p>
	 * Use for S2F37.
	 * </p>
	 * 
	 * @param ceid Collection-Event-ID
	 * @return DynamicCollectionEvent
	 */
	public DynamicCollectionEvent addEnableCollectionEvent(long ceid);
	
	/**
	 * Add Enable-Collection-Event.
	 * 
	 * <p>
	 * Use for S2F37.
	 * </p>
	 * 
	 * @param alias of {@link #getCollectionEvent(CharSequence)}, {@link #s6f15(CharSequence)}, {@link #s6f17(CharSequence)}
	 * @param ceid Collection-Event-ID
	 * @return DynamicCollectionEvent
	 */
	public DynamicCollectionEvent addEnableCollectionEvent(CharSequence alias, long ceid);
	
	/**
	 * Remove Enable-Collection-Event.
	 * 
	 * @param ce DynamicCollectionEvent
	 * @return {@code true} if remove success
	 */
	public boolean removeEnableCollectionEvent(DynamicCollectionEvent ce);
	
	/**
	 * Seek in Enable-Collection-Events by alias.
	 * 
	 * @param alias
	 * @return DynamicCollectionEvent if exist
	 */
	public Optional<DynamicCollectionEvent> getCollectionEvent(CharSequence alias);
	
	/**
	 * Seek in Enable-Collection-Events by Collection-Event-ID.
	 * 
	 * <p>
	 * Used for S6F11, S6F13,...
	 * </p>
	 * 
	 * @param ceid
	 * @return DynamicCollectionEvent if exist
	 */
	public Optional<DynamicCollectionEvent> getCollectionEvent(Secs2 ceid);
	
	/**
	 * S2F33, Delete All Define-Report.
	 * 
	 * <p>
	 * DATA-ID is AutoNumber.<br />
	 * blocking-method.<br />
	 * </p>
	 * 
	 * @return DRACK
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public DRACK s2f33DeleteAll()
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException;
	
	/**
	 * S2F33, Define Report.
	 * 
	 * <p>
	 * DATA-ID is AutoNumber.<br />
	 * blocking-method<br />
	 * </p>
	 * 
	 * @return DRACK
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public DRACK s2f33Define()
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException;
	
	/**
	 * S2F35, Link Collection Event Report.
	 * 
	 * <p>
	 * DATA-ID is AutoNumber.<br />
	 * blocking-method<br />
	 * </p>
	 * 
	 * @return LRACK
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public LRACK s2f35()
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException;
	
	/**
	 * S2F37, Disable All Collection-Event-Report.
	 * 
	 * <p>
	 * blocking-method.
	 * </p>
	 * 
	 * @return ERACK
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public ERACK s2f37DisableAll()
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException;
	
	/**
	 * S2F37, Enable All Collection-Event-Report.
	 * 
	 * <p>
	 * blocking-method
	 * </p>
	 * 
	 * @return ERACK
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public ERACK s2f37EnableAll()
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException;
	
	/**
	 * S2F37, Enable Collection-Event-Report.
	 * 
	 * <p>
	 * blocking-method
	 * </p>
	 * 
	 * @return ERACK
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public ERACK s2f37Enable()
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException;
	
	/**
	 * S6F15, Event Report Request.
	 * 
	 * <p>
	 * blocking-method
	 * </p>
	 * 
	 * @param ce DynamicCollectionEvent
	 * @return reply-message
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<? extends SecsMessage> s6f15(DynamicCollectionEvent ce)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * S6F15, Event Report Request.
	 * 
	 * <p>
	 * Parameter "alias" is setted in #addEnableCollectionEvent<br />
	 * Seek in enable-collection-events by alias.<br />
	 * blocking-method.<br />
	 * If alias not found, throw AliasNotFoundDynamicEventReportException.<br />
	 * </p>
	 * 
	 * @param alias of Enable-Collection-Event
	 * @return reply-message
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws DynamicEventReportException
	 * @throws InterruptedException
	 */
	public Optional<? extends SecsMessage> s6f15(CharSequence alias)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, DynamicEventReportException
			, InterruptedException;
	
	/**
	 * S6F17, Annotated Event Report Request.
	 * 
	 * <p>
	 * blocking-method
	 * </p>
	 * 
	 * @param ce DynamicCollectionEvent
	 * @return reply-message
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<? extends SecsMessage> s6f17(DynamicCollectionEvent ce)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * S6F17, Annotated Event Report Request.
	 * 
	 * <p>
	 * Parameter "alias" is setted in #addEnableCollectionEvent<br />
	 * Seek in enable-collection-events by alias.<br />
	 * blocking-method.<br />
	 * If alias not found, throw AliasNotFoundDynamicEventReportException.
	 * </p>
	 * 
	 * @param alias of Enable-Collection-Event
	 * @return reply-message
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws DynamicEventReportException
	 * @throws InterruptedException
	 */
	public Optional<? extends SecsMessage> s6f17(CharSequence alias)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, DynamicEventReportException
			, InterruptedException;
	
	/**
	 * S6F19, Individual Report Request
	 * 
	 * <p>
	 * blocking-method
	 * </p>
	 * 
	 * @param report DynamicReport
	 * @return reply-message
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<? extends SecsMessage> s6f19(DynamicReport report)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * S6F19, Individual Report Request.
	 * 
	 * <p>
	 * Parameter "alias" is setted in #addDefineReport<br />
	 * Seek in define-reports by alias.<br />
	 * blocking-method.<br />
	 * If alias not found, throw AliasNotFoundDynamicEventReportException.<br />
	 * </p>
	 * 
	 * @param alias of Define-Report
	 * @return reply-message
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws DynamicEventReportException
	 * @throws InterruptedException
	 */
	public Optional<? extends SecsMessage> s6f19(CharSequence alias)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, DynamicEventReportException
			, InterruptedException;
	
	/**
	 * S6F21, Annotated Individual Report Request.
	 * 
	 * <p>
	 * blocking-method
	 * </p>
	 * 
	 * @param report DynamicReport
	 * @return reply-message
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<? extends SecsMessage> s6f21(DynamicReport report)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * S6F21, Annotated Individual Report Request.
	 * 
	 * <p>
	 * Parameter "alias" is setted in #addDefineReport<br />
	 * Seek in define-reports by alias.<br />
	 * blocking-method.<br />
	 * If alias not found, throw AliasNotFoundDynamicEventReportException.<br />
	 * </p>
	 * 
	 * @param alias of Define-Report
	 * @return reply-message
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws DynamicEventReportException
	 * @throws InterruptedException
	 */
	public Optional<? extends SecsMessage> s6f21(CharSequence alias)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, DynamicEventReportException
			, InterruptedException;

}
