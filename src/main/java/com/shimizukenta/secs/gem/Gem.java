package com.shimizukenta.secs.gem;

import java.util.Optional;

import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

/**
 * This interface is implementation of GEM (SEMI-E30, partially)<br />
 * Call from SecsCommunicator#gem
 * 
 * @author kenta-shimizu
 *
 */
public interface Gem {
	
	/**
	 * 
	 * @return Auto-Data-ID from AtomicLong#incrementAndGet
	 */
	public Secs2 autoDataId();
	
	/**
	 * 
	 * @param id
	 * @return Data-ID
	 */
	public Secs2 dataId(long id);
	
	/**
	 * Are You Online?<br />
	 * blocking-method
	 * 
	 * @return Reply-Message
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s1f1()
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Create DynamicEventReportConfig
	 * 
	 * @return DynamicEventReportConfig new-Instance
	 */
	public DynamicEventReportConfig newDynamicEventReportConfig();
	
	/**
	 * On Line Data<br />
	 * MDLN, SOFTREV is reference GemConfig value<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s1f2(SecsMessage primaryMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Establish Communications Request<br />
	 * blocking-method
	 * 
	 * @return COMMACK
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public COMMACK s1f13()
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException;
	
	/**
	 * Establish Communications Request Acknowledge<br />
	 * blocking-mothod
	 * 
	 * @param primary-message
	 * @param COMMACK
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s1f14(SecsMessage primaryMsg, COMMACK commack)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Request OFF-LINE<br />
	 * blocking-method
	 * 
	 * @return OFLACK
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public OFLACK s1f15()
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException;
	
	/**
	 * OFF-LINE Acknowledge<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @return Optional.empty()
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s1f16(SecsMessage primaryMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Request ON-LINE<br />
	 * blocking-method
	 * 
	 * @return ONLACK
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public ONLACK s1f17()
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException;
	
	/**
	 * ON-LINE Acknowledge<br />
	 * blocking-mehod
	 * 
	 * @param primary-message
	 * @param onlack
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s1f18(SecsMessage primaryMsg, ONLACK onlack)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Date and Time Request<br />
	 * blocking-method
	 * 
	 * @return Clock
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public Clock s2f17()
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException;
	
	/**
	 * Date and Time Response />
	 * blocking-method
	 * 
	 * @param SimpleClock
	 * @return Optional.empty()
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s2f18(SecsMessage primaryMsg, Clock c)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException;
	
	/**
	 * Now Date and Time Response />
	 * blocking-method
	 * 
	 * @param SimpleClock
	 * @return Optional.empty()
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s2f18Now(SecsMessage primaryMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException;
	
	/**
	 * Remote Command Acknowledge<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @param CMDA
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s2f22(SecsMessage primaryMsg, CMDA cmda)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Initiate Processing Acknowledge<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @param CMDA
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s2f28(SecsMessage primaryMsg, CMDA cmda)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Date and Time Set Request<br />
	 * blocking-method
	 * 
	 * @param CLOCK
	 * @return TIACK
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public TIACK s2f31(Clock c)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException;
	
	/**
	 * Now Date and Time Set Request<br />
	 * blocking-method
	 * 
	 * @return TIACK
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public TIACK s2f31Now()
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException;
	
	/**
	 * Date and Time Set Acknowledge<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @param TIACK
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s2f32(SecsMessage primaryMsg, TIACK tiack)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
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
	 * @param DynamicEventReportConfig
	 * @return DRACK
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public DRACK s2f33Define(DynamicEventReportConfig config)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException;
	
	/**
	 * Define Report Acknowledge<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @param DRACK
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s2f34(SecsMessage primaryMsg, DRACK drack)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Link Collection Event Report<br />
	 * DATA-ID is AutoNumber.<br />
	 * blocking-method
	 * 
	 * @param DynamicEventReportConfig
	 * @return LRACK
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public LRACK s2f35(DynamicEventReportConfig config)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException;
	
	/**
	 * Link Collection Event Report Acknowledge<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @param LRACK
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s2f36(SecsMessage primaryMsg, LRACK lrack)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
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
	 * @param DynamicEventReportConfig
	 * @return ERACK
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public ERACK s2f37Enable(DynamicEventReportConfig config)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException;
	
	/**
	 * Enable/Disable CollectionEvent Report Acknowledge<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @param ERACK
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s2f38(SecsMessage primaryMsg, ERACK erack)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Multi-block Grant<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @param GRANT
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s2f40(SecsMessage primaryMsg, GRANT grant)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Matls Multi-block Grant<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @param GRANT
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s3f16(SecsMessage primaryMsg, GRANT grant)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Alarm Report Ack<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @param ACKC5
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s5f2(SecsMessage primaryMsg, ACKC5 ackc5)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Enable/Disable Alarm Ack<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @param ACKC5
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s5f4(SecsMessage primaryMsg, ACKC5 ackc5)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Trace Data Ack<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @param ACKC6
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s6f2(SecsMessage primaryMsg, ACKC6 ackc6)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Discrete Variable Data Send Ack<br />
	 * blocking-method
	 * 
	 * @param primary-messag0
	 * @param ACKC6
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s6f4(SecsMessage primaryMsg, ACKC6 ackc6)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Multi-block Grant<br />
	 * blocking-method
	 * 
	 * @param primaryMsg
	 * @param GRANT6
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s6f6(SecsMessage primaryMsg, GRANT6 grant6)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Formatted Variable Ack<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @param ACKC6
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s6f10(SecsMessage primaryMsg, ACKC6 ackc6)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * CollectionEvent Report Ack<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @param ACKC6
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s6f12(SecsMessage primaryMsg, ACKC6 ackc6)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Annotated CollectionEvent Report Ack<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @param ACKC6
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s6f14(SecsMessage primaryMsg, ACKC6 ackc6)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
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
	 * Notification Report Send Ack<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @param ACKC6
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s6f26(SecsMessage primaryMsg, ACKC6 ackc6)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Process Program Send Acknowledge<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @param ACKC7
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s7f4(SecsMessage primaryMsg, ACKC7 ackc7)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Matl/Process Matrix Update Ack<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @param ACKC7
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s7f12(SecsMessage primaryMsg, ACKC7 ackc7)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Delete Matl/Process Matrix Entry Acknowledge<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @param ACKC7
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s7f14(SecsMessage primaryMsg, ACKC7 ackc7)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Matrix Mode Select Ack<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @param ACKC7
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s7f16(SecsMessage primaryMsg, ACKC7 ackc7)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Delete Process Program Acknowledge<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @param ACKC7
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s7f18(SecsMessage primaryMsg, ACKC7 ackc7)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Formatted Process Program Acknowledge<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @param ACKC7
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s7f24(SecsMessage primaryMsg, ACKC7 ackc7)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Verification Request Acknowledge<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @param ACKC7
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s7f32(SecsMessage primaryMsg, ACKC7 ackc7)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Large PP Send Ack<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @param ACKC7
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s7f38(SecsMessage primaryMsg, ACKC7 ackc7)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Large Formatted PP Ack<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @param ACKC7
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s7f40(SecsMessage primaryMsg, ACKC7 ackc7)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Large PP Req Ack<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @param ACKC7
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s7f42(SecsMessage primaryMsg, ACKC7 ackc7)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Large Formatted PP Req Ack<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @param ACKC7
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s7f44(SecsMessage primaryMsg, ACKC7 ackc7)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Unknown Device ID<br />
	 * blocking-method
	 * 
	 * @param reference-message
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s9f1(SecsMessage refMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Unknown Stream<br />
	 * blocking-method
	 * 
	 * @param reference-message
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s9f3(SecsMessage refMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Unknown Function<br />
	 * blocking-method
	 * 
	 * @param reference-message
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s9f5(SecsMessage refMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Illegal Data<br />
	 * blocking-method
	 * 
	 * @param reference-message
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s9f7(SecsMessage refMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Transaction Timeout<br />
	 * blocking-method
	 * 
	 * @param reference-message
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s9f9(SecsMessage refMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Data Too Long<br />
	 * blocking-method
	 * 
	 * @param reference-message
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s9f11(SecsMessage refMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	
	/* HOOK s9f13 */
	
	
	/**
	 * Terminal Request Acknowledge<br />
	 * blocking-method
	 * 
	 * @param primaryMsg
	 * @param ackc10
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s10f2(SecsMessage primaryMsg, ACKC10 ackc10)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;

	/**
	 * Terminal Display, Single Acknowledge<br />
	 * blocking-method
	 * 
	 * @param primaryMsg
	 * @param ackc10
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s10f4(SecsMessage primaryMsg, ACKC10 ackc10)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;

	/**
	 * Terminal Display, Multi-Block Acknowledge<br />
	 * blocking-method
	 * 
	 * @param primaryMsg
	 * @param ackc10
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s10f6(SecsMessage primaryMsg, ACKC10 ackc10)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Broadcast Acknowledge<br />
	 * blocking-method
	 * 
	 * @param primaryMsg
	 * @param ackc10
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s10f10(SecsMessage primaryMsg, ACKC10 ackc10)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
	/**
	 * Data Set Obj Multi-Block Grant<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @param GRANT
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s13f12(SecsMessage primaryMsg, GRANT grant)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException;
	
}
