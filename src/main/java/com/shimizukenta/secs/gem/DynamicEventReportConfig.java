package com.shimizukenta.secs.gem;

import java.util.List;
import java.util.Optional;

import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.secs2.Secs2Exception;

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
	 * @return
	 */
	public DynamicReport addDefineReport(List<Number> vids);
	
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
	
	
	
	public DRACK s2f33DeleteAll()
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException;
	
	public DRACK s2f33Define()
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException;
	
	public LRACK s2f35()
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException;
	
	public ERACK s2f37DisableAll()
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException;
	
	public ERACK s2f37EnableAll()
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException;
	
	public ERACK s2f37Enable()
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException;
	
	
	public Optional<SecsMessage> s6f15(DynamicCollectionEvent ce)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	public Optional<SecsMessage> s6f15(CharSequence alias)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, DynamicEventReportException
			, InterruptedException;
	
	public Optional<SecsMessage> s6f17(DynamicCollectionEvent ce)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	public Optional<SecsMessage> s6f17(CharSequence alias)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, DynamicEventReportException
			, InterruptedException;
	
	public Optional<SecsMessage> s6f19(DynamicReport report)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	public Optional<SecsMessage> s6f19(CharSequence alias)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, DynamicEventReportException
			, InterruptedException;

	public Optional<SecsMessage> s6f21(DynamicReport report)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	public Optional<SecsMessage> s6f21(CharSequence alias)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, DynamicEventReportException
			, InterruptedException;

}
