package secs.secs1;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import secs.SecsException;
import secs.SecsMessageSendReplyManager;
import secs.SecsSendMessageConsumer;
import secs.SecsTimeout;
import secs.SecsWaitReplyMessageException;

public class Secs1MessageSendReplyManager extends SecsMessageSendReplyManager<Secs1Message> {

	protected Secs1MessageSendReplyManager(ExecutorService es
			, SecsTimeout timeout
			, SecsSendMessageConsumer<Secs1Message> sendMessageConsumer) {
		
		super(es, timeout, sendMessageConsumer);
		
	}
	
	private class InnerReplyMsg {
		
		private final boolean resetTimer;
		private final Secs1Message reply;
		
		private InnerReplyMsg(boolean reset, Secs1Message reply) {
			this.resetTimer = reset;
			this.reply = reply;
		}
	}
	
	private final Collection<Integer> resetKeys = new CopyOnWriteArraySet<>();
	
	protected void resetTimer(Secs1MessageBlock block) {
		resetTimer(block.systemBytesKey());
	}
	
	protected void resetTimer(Integer key) {
		synchronized ( this ) {
			resetKeys.add(key);
			this.notifyAll();
		}
	}
	
	@Override
	protected Optional<Secs1Message> waitUntilReply(Secs1Message primary, Integer key)
			throws SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		try {
			
			for ( ;; ) {
				
				Collection<Callable<InnerReplyMsg>> tasks = Arrays.asList(() -> {
					
					try {
						
						for ( ;; ) {
							
							if ( ! replyMap.containsKey(key) ) {
								break;
							}
							
							Secs1Message reply = replyMap.get(key);
							if ( reply != null ) {
								return new InnerReplyMsg(false, reply);
							}
							
							synchronized ( this ) {
								this.wait();
							}
						}
					}
					catch ( InterruptedException ignore ) {
					}
					
					return new InnerReplyMsg(false, null);
					
				}, () -> {
					
					try {
						
						for ( ;; ) {
							
							if ( resetKeys.contains(key) ) {
								return new InnerReplyMsg(true, null);
							}
							
							synchronized ( this ) {
								this.wait();
							}
						}
					}
					catch ( InterruptedException ignore ) {
					}
					finally {
						resetKeys.remove(key);
					}
					
					return new InnerReplyMsg(false, null);
				});
				
				long timeout = (long)(getTimeoutByMessageType(primary) * 1000.0F);
				InnerReplyMsg replyHolder = executorService().invokeAny(tasks, timeout, TimeUnit.MILLISECONDS);
				
				if ( replyHolder.resetTimer ) {
					continue;
				}
				
				Secs1Message reply = replyHolder.reply;
				if ( reply != null ) {
					return getOptionalOrThrow(primary, reply);
				}
			}
		}
		catch (TimeoutException e) {
			throwSecsWaitReplyMessageException(primary);
		}
		catch ( ExecutionException none) {
		}
		
		return Optional.empty();
	}
	
}
