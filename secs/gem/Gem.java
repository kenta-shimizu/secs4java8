package secs.gem;

import java.util.Optional;

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
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s1f1()
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
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
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s1f2(SecsMessage primaryMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
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
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s1f13()
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
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
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s1f14(SecsMessage primaryMsg, COMMACK commack)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
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
		
		return comm.send(1, 14, false, Secs2.list(Secs2.binary(commack.code()), ss))
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
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s1f16(SecsMessage primaryMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException {
		
		return comm.send(primaryMsg, 1, 16, false, Secs2.binary(OFLACK.OK.code()))
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
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s1f18(SecsMessage primaryMsg, ONLACK onlack)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException {
		
		return comm.send(primaryMsg, 1, 18, false, Secs2.binary(onlack.code()))
				.map(x -> (SecsMessage)x);
	}

	
	
	//TOOD
	//s2f17
	//s2f18
	//s2f31
	//s2f32
	
	//TODO
	//s2f33
	//s2f34
	//s2f35
	//s2f36
	//s2f37
	//s2f38
	
	//TODO
	//s5f2
	//s5f4
	
	//TODO
	//s6f2
	//s6f4
	//s6f6
	//s6f8
	//s6f10
	//s6f12
	
	
	/**
	 * Unknown Device ID<br />
	 * blocking-method
	 * 
	 * @param primary-message
	 * @return Optional.empty
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s9f1(SecsMessage primaryMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
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
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s9f3(SecsMessage primaryMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
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
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s9f5(SecsMessage primaryMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
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
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s9f7(SecsMessage primaryMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
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
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s9f9(SecsMessage primaryMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
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
	 * @throws Secs2Exception
	 * @throws InterruptedException
	 */
	public Optional<SecsMessage> s9f11(SecsMessage primaryMsg)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException {
		
		return s9fx(primaryMsg, 11);
	}
	
	private Optional<SecsMessage> s9fx(SecsMessage primaryMsg, int func)
			throws SecsSendMessageException
			, SecsWaitReplyMessageException
			, SecsException
			, Secs2Exception
			, InterruptedException {
		
		return comm.send(9, func, false, Secs2.binary(primaryMsg.header10Bytes()))
				.map(x -> (SecsMessage)x);
	}
	
	/* HOOK s9f13 */
	
	
	//TODO
	//s10f1
	//s10f2
	//s10f3
	//s10f4
	
}
