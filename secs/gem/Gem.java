package secs.gem;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import secs.SecsCommunicator;
import secs.SecsException;
import secs.SecsMessage;
import secs.SecsSendMessageException;
import secs.SecsWaitReplyMessageException;
import secs.secs2.Secs2;
import secs.secs2.Secs2Exception;

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
	public Optional<SecsMessage> s1f1()
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return comm.send(1, 1, true).map(x -> (SecsMessage)x);
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
	public Optional<SecsMessage> s1f2(SecsMessage primaryMsg)
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
		
		return comm.send(primaryMsg, 1, 2, false, ss).map(x -> (SecsMessage)x);
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
	public Optional<SecsMessage> s1f13()
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
		
		return comm.send(1, 13, true, ss).map(x -> (SecsMessage)x);
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
	public Optional<SecsMessage> s1f14(SecsMessage primaryMsg, COMMACK commack)
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
		
		return comm.send(1, 14, false, Secs2.list(commack.secs2(), ss))
				.map(x -> (SecsMessage)x);
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
	public Optional<SecsMessage> s1f16(SecsMessage primaryMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return comm.send(primaryMsg, 1, 16, false, OFLACK.OK.secs2())
				.map(x -> (SecsMessage)x);
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
	public Optional<SecsMessage> s1f18(SecsMessage primaryMsg, ONLACK onlack)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return comm.send(primaryMsg, 1, 18, false, onlack.secs2())
				.map(x -> (SecsMessage)x);
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
		
		return comm.send(2, 17, true)
				.filter(msg -> {
					
					int strm = msg.getStream();
					int func = msg.getFunction();
					
					return strm == 2 && func == 18;
				})
				.map(msg -> msg.secs2())
				.map(ss -> {
					
					try {
						return ss.getAscii();
					}
					catch ( Secs2Exception giveup ) {
					}
					
					return null;
				})
				.orElseThrow(() -> new Secs2Exception("s2f18 parse failed"));
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
	public Optional<SecsMessage> s2f32(SecsMessage primaryMsg, TIACK tiack)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return comm.send(primaryMsg, 2, 32, false, tiack.secs2())
				.map(x -> (SecsMessage)x);
	}
	
	
	//TODO
	//s2f33
	
	
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
			, InterruptedException {
		
		return comm.send(primaryMsg, 2, 34, false, drack.secs2())
				.map(x -> (SecsMessage)x);
	}

	
	//TODO
	//s2f35

	
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
	public Optional<SecsMessage> s2f36(SecsMessage primaryMsg, LRACK lrack)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return comm.send(primaryMsg, 2, 36, false, lrack.secs2())
				.map(x -> (SecsMessage)x);
	}
	
	
	//TODO
	//comments
	public ERACK s2f37Enable(List<GemCollectionEvent> events)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException {
		
		return s2f37p(CEED.ENABLE, events);
	}
	
	//TODO
	//comment
	public ERACK s2f37Disable(List<GemCollectionEvent> events)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException {
		
		return s2f37p(CEED.DISABLE, events);
	}
	
	//TODO
	//comment
	public ERACK s2f37EnableAll()
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException {
	
		return s2f37(CEED.ENABLE, Collections.emptyList());
	}
	
	//TODO
	//comment
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
	
	//TODO
	//comment
	public ERACK s2f37(CEED ceed, List<Secs2> ceids)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException {
		
		Secs2 ss = Secs2.list(
				ceed.secs2()
				, Secs2.list(ceids));
		
		return comm.send(2, 37, true, ss)
				.filter(msg -> msg.getStream() == 2)
				.filter(msg -> msg.getFunction() == 38)
				.map(msg -> msg.secs2())
				.map(v -> {
					try {
						return ERACK.get(v);
					}
					catch (Secs2Exception giveup) {
					}
					
					return null;
				})
				.orElseThrow(() -> new Secs2Exception("s2f38 parse failed"));
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
	public Optional<SecsMessage> s2f38(SecsMessage primaryMsg, ERACK erack)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return comm.send(primaryMsg, 2, 38, false, erack.secs2())
				.map(x -> (SecsMessage)x);
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
	public Optional<SecsMessage> s5f2(SecsMessage primaryMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return comm.send(primaryMsg, 5, 2, false, ACKC5.OK.secs2())
				.map(x -> (SecsMessage)x);
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
	public Optional<SecsMessage> s5f4(SecsMessage primaryMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return comm.send(primaryMsg, 5, 4, false, ACKC5.OK.secs2())
				.map(x -> (SecsMessage)x);
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
	public Optional<SecsMessage> s6f2(SecsMessage primaryMsg)
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
	public Optional<SecsMessage> s6f4(SecsMessage primaryMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s6fxa(primaryMsg, 4);
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
	public Optional<SecsMessage> s6f10(SecsMessage primaryMsg)
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
	public Optional<SecsMessage> s6f12(SecsMessage primaryMsg)
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
	public Optional<SecsMessage> s6f14(SecsMessage primaryMsg)
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
	public Optional<SecsMessage> s6f26(SecsMessage primaryMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s6fxa(primaryMsg, 26);
	}
	
	private Optional<SecsMessage> s6fxa(SecsMessage primaryMsg, int func)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return comm.send(primaryMsg, 6, func, false, ACKC6.OK.secs2())
				.map(x -> (SecsMessage)x);
	}
	
	
	
	/**
	 * Unknown Device ID<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s9f1(SecsMessage primaryMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s9fx(primaryMsg, 1);
	}
	
	/**
	 * Unknown Stream<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s9f3(SecsMessage primaryMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s9fx(primaryMsg, 3);
	}
	
	/**
	 * Unknown Function<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s9f5(SecsMessage primaryMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s9fx(primaryMsg, 5);
	}
	
	/**
	 * Illegal Data<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s9f7(SecsMessage primaryMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s9fx(primaryMsg, 7);
	}
	
	/**
	 * Transaction Timeout<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s9f9(SecsMessage primaryMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s9fx(primaryMsg, 9);
	}
	
	/**
	 * Data Too Long<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s9f11(SecsMessage primaryMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s9fx(primaryMsg, 11);
	}
	
	private Optional<SecsMessage> s9fx(SecsMessage primaryMsg, int func)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return comm.send(9, func, false, Secs2.binary(primaryMsg.header10Bytes()))
				.map(x -> (SecsMessage)x);
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
	public Optional<SecsMessage> s10f2(SecsMessage primaryMsg, ACKC10 ackc10)
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
	public Optional<SecsMessage> s10f4(SecsMessage primaryMsg, ACKC10 ackc10)
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
	public Optional<SecsMessage> s10f6(SecsMessage primaryMsg, ACKC10 ackc10)
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
	public Optional<SecsMessage> s10f10(SecsMessage primaryMsg, ACKC10 ackc10)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return s10fx(primaryMsg, 10, ackc10);
	}
	
	private Optional<SecsMessage> s10fx(SecsMessage primaryMsg, int func, ACKC10 ackc10)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		return comm.send(9, func, false, ackc10.secs2())
				.map(x -> (SecsMessage)x);
	}
	
}
