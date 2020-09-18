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
 * This interface is implementation of Dynamic-Event-Report-Configuration in GEM (SEMI-E30)<br />
 * To create instance, call from Gem#newDynamicEventReportConfig<br />
 * Relates: S2F33, S2F35, S2F37, S6F11, S6F13, S6F15, S6F17, S6F19, S6F21
 * 
 * @author kenta-shimizu
 *
 */
public interface DynamicEventReportConfig {
	
	/**
	 * Add Define-Report.<br />
	 * Use for S2F33
	 * 
	 * @param reportId
	 * @param alias
	 * @param VIDs
	 * @return DynamicReport
	 */
	public DynamicReport addDefineReport(long reportId, CharSequence alias, List<? extends Number> vids);
	
	/**
	 * Add Define-Report.<br />
	 * Use for S2F33
	 * 
	 * @param reportId
	 * @param VIDs
	 * @return DynamicReport
	 */
	public DynamicReport addDefineReport(long reportId, List<? extends Number> vids);
	
	/**
	 * Add Define-Report.<br />
	 * Use for S2F33<br />
	 * Report-ID is AutoNumber.
	 * 
	 * @param alias
	 * @param VIDs
	 * @return DynamicReport
	 */
	public DynamicReport addDefineReport(CharSequence alias, List<? extends Number> vids);
	
	/**
	 * Add Define-Report.<br />
	 * Use for S2F33<br />
	 * Report-ID is AutoNumber.
	 * 
	 * @param VIDs
	 * @return DynamicReport
	 */
	public DynamicReport addDefineReport(List<? extends Number> vids);
	
	/**
	 * 
	 * @param DynamicReport
	 * @return true if success
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
	 * Seek in Define-Reports by report-id.<br />
	 * Used for S6F11, S6F13,...
	 * 
	 * @param report-id
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
	 * Add Event-Report-Link.<br />
	 * Use for S2F35
	 * 
	 * @param DynamicCollectionEvent
	 * @param Report-IDs
	 * @return DynamicLink
	 */
	public DynamicLink addLinkById(DynamicCollectionEvent ce, List<? extends Number> reportIds);
	
	/**
	 * Add Event-Report-Link.<br />
	 * Use for S2F35
	 * 
	 * @param Collection-Event-ID
	 * @param DynamicReports
	 * @return DynamicLink
	 */
	public DynamicLink addLinkByReport(long ceid, List<? extends DynamicReport> reports);
	
	/**
	 * Add Event-Report-Link.<br />
	 * Use for S2F35
	 * 
	 * @param DynamicCollectionEvent
	 * @param DynamicReports
	 * @return DynamicLink
	 */
	public DynamicLink addLinkByReport(DynamicCollectionEvent ce, List<? extends DynamicReport> reports);
	
	/**
	 * 
	 * @param DynamicLink
	 * @return true if success
	 */
	public boolean removeLink(DynamicLink link);
	
	/**
	 * Add Enable-Collection-Event.<br />
	 * Use for S2F37
	 * 
	 * @param Collection-Event-ID
	 * @return DynamicCollectionEvent
	 */
	public DynamicCollectionEvent addEnableCollectionEvent(long ceid);
	
	/**
	 * Add Enable-Collection-Event.<br />
	 * Use for S2F37
	 * 
	 * @param alias
	 * @param Collection-Event-ID
	 * @return DynamicCollectionEvent
	 */
	public DynamicCollectionEvent addEnableCollectionEvent(CharSequence alias, long ceid);
	
	/**
	 * 
	 * @param DynamicCollectionEvent
	 * @return true if success
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
	 * Seek in Enable-Collection-Events by Collection-Event-ID.<br />
	 * Used for S6F11, S6F13,...
	 * 
	 * @param ceid
	 * @return DynamicCollectionEvent if exist
	 */
	public Optional<DynamicCollectionEvent> getCollectionEvent(Secs2 ceid);
	
	/**
	 * Delete All Define-Report<br />
	 * DATA-ID is AutoNumber.<br />
	 * blocking-method
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
	 * Define Report<br />
	 * DATA-ID is AutoNumber.<br />
	 * blocking-method
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
	 * Link Collection Event Report<br />
	 * DATA-ID is AutoNumber.<br />
	 * blocking-method
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
	 * Disable All Collection-Event-Report<br />
	 * blocking-method
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
	 * Enable All Collection-Event-Report<br />
	 * blocking-method
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
	 * Enable Collection-Event-Report<br />
	 * blocking-method
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
	 * Event Report Request<br />
	 * blocking-method
	 * 
	 * @param DynamicCollectionEvent
	 * @return reply-message
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s6f15(DynamicCollectionEvent ce)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Event Report Request<br />
	 * Parameter "alias" is setted in #addEnableCollectionEvent<br />
	 * Seek in enable-collection-events by alias.<br />
	 * blocking-method.<br />
	 * If alias not found, throw AliasNotFoundDynamicEventReportException.
	 * 
	 * @param Enable-Collection-Event alias
	 * @return reply-message
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws DynamicEventReportException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s6f15(CharSequence alias)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, DynamicEventReportException
			, InterruptedException;
	
	/**
	 * Annotated Event Report Request<br />
	 * blocking-method
	 * 
	 * @param DynamicCollectionEvent
	 * @return reply-message
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s6f17(DynamicCollectionEvent ce)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Annotated Event Report Request<br />
	 * Parameter "alias" is setted in #addEnableCollectionEvent<br />
	 * Seek in enable-collection-events by alias.<br />
	 * blocking-method.<br />
	 * If alias not found, throw AliasNotFoundDynamicEventReportException.
	 * 
	 * @param Enable-Collection-Event alias
	 * @return reply-message
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws DynamicEventReportException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s6f17(CharSequence alias)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, DynamicEventReportException
			, InterruptedException;
	
	/**
	 * Individual Report Request<br />
	 * blocking-method
	 * 
	 * @param DynamicReport
	 * @return reply-message
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s6f19(DynamicReport report)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Individual Report Request<br />
	 * Parameter "alias" is setted in #addDefineReport<br />
	 * Seek in define-reports by alias.<br />
	 * blocking-method.<br />
	 * If alias not found, throw AliasNotFoundDynamicEventReportException.
	 * 
	 * @param Define-Report alias
	 * @return reply-message
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws DynamicEventReportException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s6f19(CharSequence alias)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, DynamicEventReportException
			, InterruptedException;
	
	/**
	 * Annotated Individual Report Request<br />
	 * blocking-method
	 * 
	 * @param DynamicReport
	 * @return reply-message
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s6f21(DynamicReport report)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Annotated Individual Report Request<br />
	 * Parameter "alias" is setted in #addDefineReport<br />
	 * Seek in define-reports by alias.<br />
	 * blocking-method.<br />
	 * If alias not found, throw AliasNotFoundDynamicEventReportException.
	 * 
	 * @param Define-Report alias
	 * @return reply-message
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws DynamicEventReportException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s6f21(CharSequence alias)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, DynamicEventReportException
			, InterruptedException;

}
