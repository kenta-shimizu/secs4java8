package com.shimizukenta.secs.gem;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.shimizukenta.secs.SecsCommunicator;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;

public class Gem {

	private final SecsCommunicator comm;
	private final GemConfig config;
	
	public Gem(SecsCommunicator communicator, GemConfig config) {
		this.comm = communicator;
		this.config = config;
	}
	
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
	public Optional<? extends SecsMessage> s1f1()
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return comm.send(1, 1, true);
	}
	
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
	public Optional<? extends SecsMessage> s1f2(SecsMessage primaryMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		Secs2 ss;
		
		if ( comm.isEquip() ) {
			
			ss = Secs2.list(
					Secs2.ascii(config.mdln())
					, Secs2.ascii(config.softrev())
					);
			
		} else {
			
			ss = Secs2.list();
		}
		
		return comm.send(primaryMsg, 1, 2, false, ss);
	}
	
	/**
	 * Establish Communications Request<br />
	 * blocking-method
	 * 
	 * @return Reply-Message
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<? extends SecsMessage> s1f13()
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		Secs2 ss;
		
		if ( comm.isEquip() ) {
			
			ss = Secs2.list(
					Secs2.ascii(config.mdln())
					, Secs2.ascii(config.softrev())
					);
			
		} else {
			
			ss = Secs2.list();
		}
		
		return comm.send(1, 13, true, ss);
	}
	
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
	public Optional<? extends SecsMessage> s1f14(SecsMessage primaryMsg, COMMACK commack)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		Secs2 ss;
		
		if ( comm.isEquip() ) {
			
			ss = Secs2.list(
					Secs2.ascii(config.mdln())
					, Secs2.ascii(config.softrev())
					);
			
		} else {
			
			ss = Secs2.list();
		}
		
		return comm.send(primaryMsg, 1, 14, false, Secs2.list(commack.secs2(), ss));
	}
	
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
			, InterruptedException {
		
		Secs2 ss = comm.send(1, 15, true)
				.filter(msg -> msg.getStream() == 1)
				.filter(msg -> msg.getFunction() == 16)
				.map(msg -> msg.secs2())
				.orElseThrow(() -> new Secs2Exception("S1F16 parse failed"));
		
		return OFLACK.get(ss);
	}
	
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
	public Optional<? extends SecsMessage> s1f16(SecsMessage primaryMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return comm.send(primaryMsg, 1, 16, false, OFLACK.OK.secs2());
	}
	
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
			, InterruptedException {
		
		Secs2 ss = comm.send(1, 17, true)
				.filter(msg -> msg.getStream() == 1)
				.filter(msg -> msg.getFunction() == 18)
				.map(msg -> msg.secs2())
				.orElseThrow(() -> new Secs2Exception("S1F18 parse failed"));
		
		return ONLACK.get(ss);
	}
	
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
	public Optional<? extends SecsMessage> s1f18(SecsMessage primaryMsg, ONLACK onlack)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return comm.send(primaryMsg, 1, 18, false, onlack.secs2());
	}
	
	/**
	 * Date and Time Request<br />
	 * blocking-method
	 * 
	 * @return TIME
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public String s2f17()
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException {
		
		Secs2 ss = comm.send(2, 17, true)
				.filter(msg -> msg.getStream() == 2)
				.filter(msg -> msg.getFunction() == 18)
				.map(msg -> msg.secs2())
				.orElseThrow(() -> new Secs2Exception("s2f18 parse failed"));
		
		return ss.getAscii();
	}
	
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
	public Optional<? extends SecsMessage> s2f22(SecsMessage primaryMsg, CMDA cmda)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return comm.send(primaryMsg, 2, 22, false, cmda.secs2());
	}
	
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
	public Optional<? extends SecsMessage> s2f28(SecsMessage primaryMsg, CMDA cmda)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return comm.send(primaryMsg, 2, 28, false, cmda.secs2());
	}
	
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
	public Optional<? extends SecsMessage> s2f32(SecsMessage primaryMsg, TIACK tiack)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return comm.send(primaryMsg, 2, 32, false, tiack.secs2());
	}
	
	/**
	 * Define Report Delete All<br />
	 * blocking-method
	 * 
	 * @param DATAID
	 * @return DRACK
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public DRACK s2f33DeleteAll(Secs2 dataId)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException {
		
		return s2f33(dataId, Collections.emptyList());
	}
	
	/**
	 * Define Report<br />
	 * blocking-method
	 * 
	 * @param DATAID
	 * @param GemReport-list
	 * @return DRACK
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public DRACK s2f33Define(Secs2 dataId, List<GemReport> reports)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException {
		
		return s2f33(dataId, reports.stream().map(r -> r.secs2()).collect(Collectors.toList()));
	}
	
	/**
	 * Define Report<br />
	 * blocking-method
	 * 
	 * @param DATAID
	 * @param Report-list
	 * @return DRACK
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public DRACK s2f33(Secs2 dataId, List<Secs2> reports)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException {
		
		Secs2 ss = Secs2.list(dataId, Secs2.list(reports));
		
		Secs2 r = comm.send(2, 33, true, ss)
				.filter(msg -> msg.getStream() == 2)
				.filter(msg -> msg.getFunction() == 34)
				.map(msg -> msg.secs2())
				.orElseThrow(() -> new Secs2Exception("s2f34 parse failed"));
		
		return DRACK.get(r);
	}
	
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
	public Optional<? extends SecsMessage> s2f34(SecsMessage primaryMsg, DRACK drack)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return comm.send(primaryMsg, 2, 34, false, drack.secs2());
	}
	
	/**
	 * Link Event Report<br />
	 * blocking-method
	 * 
	 * @param DATAID
	 * @param CollectionEvent-ReportID-Link list
	 * @return LRACK
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public LRACK s2f35Link(Secs2 dataId, List<GemCollectionEventReportLink> links)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException {
		
		return s2f35(dataId, links.stream().map(lk -> lk.secs2()).collect(Collectors.toList()));
	}
	
	/**
	 * Link Event Report<br />
	 * blocking-method
	 * 
	 * @param DATAID
	 * @param CollectionEvent-ReportID-Link list
	 * @return LRACK
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public LRACK s2f35(Secs2 dataId, List<Secs2> links)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException {
		
		Secs2 ss = Secs2.list(dataId, Secs2.list(links));
		
		Secs2 r = comm.send(2, 35, true, ss)
				.filter(msg -> msg.getStream() == 2)
				.filter(msg -> msg.getFunction() == 36)
				.map(msg -> msg.secs2())
				.orElseThrow(() -> new Secs2Exception("s2f36 parse failed"));
		
		return LRACK.get(r);
	}
	
	/**
	 * Link Event Report Acknowledge<br />
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
	public Optional<? extends SecsMessage> s2f36(SecsMessage primaryMsg, LRACK lrack)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return comm.send(primaryMsg, 2, 36, false, lrack.secs2());
	}
	
	/**
	 * Eable Event Report<br />
	 * blocking-method
	 * 
	 * @param List of Events
	 * @return ERACK
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public ERACK s2f37Enable(List<GemCollectionEvent> events)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException {
		
		return s2f37p(CEED.ENABLE, events);
	}
	
	/**
	 * Disable Event Report<br />
	 * blocking-method
	 * 
	 * @param List of Events
	 * @return ERACK
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public ERACK s2f37Disable(List<GemCollectionEvent> events)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException {
		
		return s2f37p(CEED.DISABLE, events);
	}
	
	/**
	 * Enable All Event Report<br />
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
			, InterruptedException {
	
		return s2f37(CEED.ENABLE, Collections.emptyList());
	}
	
	/**
	 * Disable All Event Report<br />
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
			, InterruptedException {
	
		return s2f37(CEED.DISABLE, Collections.emptyList());
	}
	
	private ERACK s2f37p(CEED ceed, List<GemCollectionEvent> ceids)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException {
		
		return s2f37(ceed
				, ceids.stream()
				.map(v -> v.secs2())
				.collect(Collectors.toList()));
	}
	
	/**
	 * Enable/Disable Event Report<br />
	 * blocking-method
	 * 
	 * @param CEED
	 * @param CEIDs
	 * @return ERACK
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public ERACK s2f37(CEED ceed, List<Secs2> ceids)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException {
		
		Secs2 ss = Secs2.list(
				ceed.secs2()
				, Secs2.list(ceids));
		
		Secs2 r = comm.send(2, 37, true, ss)
				.filter(msg -> msg.getStream() == 2)
				.filter(msg -> msg.getFunction() == 38)
				.map(msg -> msg.secs2())
				.orElseThrow(() -> new Secs2Exception("s2f38 parse failed"));
		
		return ERACK.get(r);
	}
	
	/**
	 * Enable/Disable Event Report Acknowledge<br />
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
	public Optional<? extends SecsMessage> s2f38(SecsMessage primaryMsg, ERACK erack)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return comm.send(primaryMsg, 2, 38, false, erack.secs2());
	}
	
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
	public Optional<? extends SecsMessage> s2f40(SecsMessage primaryMsg, GRANT grant)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return comm.send(primaryMsg, 2, 40, false, grant.secs2());
	}
	
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
	public Optional<? extends SecsMessage> s3f16(SecsMessage primaryMsg, GRANT grant)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return comm.send(primaryMsg, 3, 16, false, grant.secs2());
	}
	
	/**
	 * Alarm Report Ack<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<? extends SecsMessage> s5f2(SecsMessage primaryMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return comm.send(primaryMsg, 5, 2, false, ACKC5.OK.secs2());
	}
	
	/**
	 * Enable/Disable Alarm Ack<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<? extends SecsMessage> s5f4(SecsMessage primaryMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return comm.send(primaryMsg, 5, 4, false, ACKC5.OK.secs2());
	}
	
	/**
	 * Trace Data Ack<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<? extends SecsMessage> s6f2(SecsMessage primaryMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s6fxa(primaryMsg, 2);
	}
	
	/**
	 * Discrete Variable Data Send Ack<br />
	 * blocking-method
	 * 
	 * @param primary-messag0
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<? extends SecsMessage> s6f4(SecsMessage primaryMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s6fxa(primaryMsg, 4);
	}
	
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
	public Optional<? extends SecsMessage> s6f6(SecsMessage primaryMsg, GRANT6 grant6)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return comm.send(primaryMsg, 6, 6, false, grant6.secs2());
	}
	
	/**
	 * Formatted Variable Ack<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<? extends SecsMessage> s6f10(SecsMessage primaryMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s6fxa(primaryMsg, 10);
	}
	
	/**
	 * Event Report Ack<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<? extends SecsMessage> s6f12(SecsMessage primaryMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s6fxa(primaryMsg, 12);
	}
	
	/**
	 * Annotated Event Report Ack<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<? extends SecsMessage> s6f14(SecsMessage primaryMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s6fxa(primaryMsg, 14);
	}
	
	/**
	 * Notification Report Send Ack<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<? extends SecsMessage> s6f26(SecsMessage primaryMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s6fxa(primaryMsg, 26);
	}
	
	private Optional<? extends SecsMessage> s6fxa(SecsMessage primaryMsg, int func)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return comm.send(primaryMsg, 6, func, false, ACKC6.OK.secs2());
	}
	
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
	public Optional<? extends SecsMessage> s7f4(SecsMessage primaryMsg, ACKC7 ackc7)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s7fx(primaryMsg, 4, ackc7);
	}
	
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
	public Optional<? extends SecsMessage> s7f12(SecsMessage primaryMsg, ACKC7 ackc7)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s7fx(primaryMsg, 12, ackc7);
	}
	
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
	public Optional<? extends SecsMessage> s7f14(SecsMessage primaryMsg, ACKC7 ackc7)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s7fx(primaryMsg, 14, ackc7);
	}
	
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
	public Optional<? extends SecsMessage> s7f16(SecsMessage primaryMsg, ACKC7 ackc7)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s7fx(primaryMsg, 16, ackc7);
	}
	
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
	public Optional<? extends SecsMessage> s7f18(SecsMessage primaryMsg, ACKC7 ackc7)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s7fx(primaryMsg, 18, ackc7);
	}
	
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
	public Optional<? extends SecsMessage> s7f24(SecsMessage primaryMsg, ACKC7 ackc7)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s7fx(primaryMsg, 24, ackc7);
	}
	
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
	public Optional<? extends SecsMessage> s7f32(SecsMessage primaryMsg, ACKC7 ackc7)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s7fx(primaryMsg, 32, ackc7);
	}
	
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
	public Optional<? extends SecsMessage> s7f38(SecsMessage primaryMsg, ACKC7 ackc7)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s7fx(primaryMsg, 38, ackc7);
	}
	
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
	public Optional<? extends SecsMessage> s7f40(SecsMessage primaryMsg, ACKC7 ackc7)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s7fx(primaryMsg, 40, ackc7);
	}
	
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
	public Optional<? extends SecsMessage> s7f42(SecsMessage primaryMsg, ACKC7 ackc7)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s7fx(primaryMsg, 42, ackc7);
	}
	
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
	public Optional<? extends SecsMessage> s7f44(SecsMessage primaryMsg, ACKC7 ackc7)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s7fx(primaryMsg, 44, ackc7);
	}
	
	private Optional<? extends SecsMessage> s7fx(SecsMessage primaryMsg, int func, ACKC7 ackc7)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return comm.send(primaryMsg, 7, func, false, ackc7.secs2());
	}
	
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
	public Optional<? extends SecsMessage> s9f1(SecsMessage refMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s9fx(refMsg, 1);
	}
	
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
	public Optional<? extends SecsMessage> s9f3(SecsMessage refMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s9fx(refMsg, 3);
	}
	
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
	public Optional<? extends SecsMessage> s9f5(SecsMessage refMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s9fx(refMsg, 5);
	}
	
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
	public Optional<? extends SecsMessage> s9f7(SecsMessage refMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s9fx(refMsg, 7);
	}
	
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
	public Optional<? extends SecsMessage> s9f9(SecsMessage refMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s9fx(refMsg, 9);
	}
	
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
	public Optional<? extends SecsMessage> s9f11(SecsMessage refMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s9fx(refMsg, 11);
	}
	
	private Optional<? extends SecsMessage> s9fx(SecsMessage refMsg, int func)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return comm.send(9, func, false, Secs2.binary(refMsg.header10Bytes()));
	}
	
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
	public Optional<? extends SecsMessage> s10f2(SecsMessage primaryMsg, ACKC10 ackc10)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s10fx(primaryMsg, 2, ackc10);
	}

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
	public Optional<? extends SecsMessage> s10f4(SecsMessage primaryMsg, ACKC10 ackc10)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s10fx(primaryMsg, 4, ackc10);
	}

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
	public Optional<? extends SecsMessage> s10f6(SecsMessage primaryMsg, ACKC10 ackc10)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s10fx(primaryMsg, 6, ackc10);
	}
	
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
	public Optional<? extends SecsMessage> s10f10(SecsMessage primaryMsg, ACKC10 ackc10)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s10fx(primaryMsg, 10, ackc10);
	}
	
	private Optional<? extends SecsMessage> s10fx(SecsMessage primaryMsg, int func, ACKC10 ackc10)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return comm.send(9, func, false, ackc10.secs2());
	}
	
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
	public Optional<? extends SecsMessage> s13f12(SecsMessage primaryMsg, GRANT grant)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return comm.send(primaryMsg, 13, 12, false, grant.secs2());
	}

}
