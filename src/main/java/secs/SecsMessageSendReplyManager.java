package secs;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class SecsMessageSendReplyManager<T extends SecsMessage> {
	
	private enum SendStatus {
		
		YET,
		COMPLETED,
		FAILED,
		;
	}
	
	protected final Map<Integer, SendStatus> sendedMap = new HashMap<>();
	protected final Map<Integer, T> replyMap = new HashMap<>();
	
	private final ExecutorService execServ;
	private final SecsTimeout secsTimeout;
	private final SecsSendMessageConsumer<T> sendMessageConsumer;
	
	/**
	 * 
	 * @param es
	 * @param sendMessageConsumer
	 */
	protected SecsMessageSendReplyManager(ExecutorService es, SecsTimeout timeout
			, SecsSendMessageConsumer<T> sendMessageConsumer) {
		
		this.execServ = es;
		this.secsTimeout = timeout;
		this.sendMessageConsumer = sendMessageConsumer;
	}
	
	protected ExecutorService executorService() {
		return execServ;
	}
	
	protected SecsTimeout secsTimeout() {
		return secsTimeout;
	}
	
	/* Secs-Log-Listener */
	private final Collection<SecsLogListener> logListeners = new CopyOnWriteArrayList<>();
	
	public boolean addSecsLogListener(SecsLogListener lstnr) {
		return logListeners.add(lstnr);
	}
	
	public boolean removeSecsLogListener(SecsLogListener lstnr) {
		return logListeners.remove(lstnr);
	}
	
	protected void putLog(SecsLog log) {
		logListeners.forEach(lstnr -> {
			lstnr.receive(log);
		});
	}
	
	/**
	 * blocking-method<br />
	 * wait until receive reply-message
	 * 
	 * @param msg
	 * @return reply-message if exist
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsSendMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<T> send(T msg)
			throws SecsWaitReplyMessageException
			, SecsSendMessageException
			, SecsException
			, InterruptedException {
		
		final Integer key  = msg.systemBytesKey();
		
		try {
			synchronized ( this ) {
				
				/* entry */
				sendedMap.put(key, SendStatus.YET);
				replyMap.put(key, null);
				
				/* send */
				sendMessageConsumer.send(msg);
				
				/* wait until send completed */
				for ( ;; ) {
					
					SendStatus status = sendedMap.get(key);
					
					if ( status == SendStatus.COMPLETED ) {
						
						break;
						
					} else if ( status == SendStatus.FAILED ) {
						
						throwSecsSendMessageException(msg);
					}
					
					this.wait();
				}
				
				if ( ! isWaitReplyMessage(msg) ) {
					return Optional.empty();
				}
			}
			
			/* wait until reply */
			return waitUntilReply(msg, key);
			
		}
		finally {
			synchronized ( this ) {
				sendedMap.remove(key);
				replyMap.remove(key);
			}
		}
	}
	
	protected Optional<T> waitUntilReply(T primary, Integer key)
			throws SecsWaitReplyMessageException
			, SecsException
			, InterruptedException {
		
		
		try {
			Collection<Callable<T>> tasks = Arrays.asList(() -> {
				
				try {
					synchronized ( this ) {
						
						for ( ;; ) {
							
							if ( ! replyMap.containsKey(key) ) {
								break;
							}
							
							T reply = replyMap.get(key);
							if ( reply != null ) {
								return reply;
							}
							
							this.wait();
						}
					}
				}
				catch ( InterruptedException ignore ) {
				}
				
				return null;
			});
			
			long timeout = (long)(getTimeoutByMessageType(primary) * 1000.0F);
			T reply = executorService().invokeAny(tasks, timeout, TimeUnit.MILLISECONDS);
			
			if ( reply != null ) {
				return getOptionalOrThrow(primary, reply);
			}
		}
		catch (TimeoutException e) {
			throwSecsWaitReplyMessageException(primary);
		}
		catch ( ExecutionException none) {
		}
		
		return Optional.empty();
	}
	
	public void notifySendCompleted(T msg) {
		notifySendCompleted(msg.systemBytesKey());
	}
	
	protected void notifySendCompleted(Integer key) {
		synchronized ( this ) {
			sendedMap.put(key, SendStatus.COMPLETED);
			this.notifyAll();
		}
	}
	
	public void notifySendFailed(T msg) {
		notifySendFailed(msg.systemBytesKey());
	}
	
	public void notifySendFailed(Integer key) {
		synchronized ( this ) {
			sendedMap.put(key, SendStatus.FAILED);
			this.notifyAll();
		}
	}
	
	/**
	 * put all-receive-message
	 * 
	 * @param msg
	 * @return Optional.empty() if message is primary-message, else Optional#exist
	 */
	public Optional<T> receive(T msg) {
		
		final Integer key = msg.systemBytesKey();
		
		synchronized ( this ) {
			
			if ( replyMap.containsKey(key) ) {
				
				replyMap.put(key, msg);
				
				this.notifyAll();
				
				return Optional.empty();
				
			} else {
				
				return Optional.of(msg);
				
			}
		}
	}
	
	/**
	 * prototype (base SECS-I)
	 * 
	 * @param msg
	 * @return true if wait reply-message
	 */
	protected boolean isWaitReplyMessage(T msg) {
		return msg.wbit();
	}
	
	/**
	 * prototype (base SECS-I)
	 * 
	 * @param msg
	 * @return timeout-seconds
	 */
	protected float getTimeoutByMessageType(T msg) {
		return secsTimeout().t3();
	}
	
	/**
	 * prototype
	 * 
	 * @throws SecsSendMessageException
	 */
	protected void throwSecsSendMessageException(T msg) throws SecsSendMessageException {
		throw new SecsSendMessageException();
	}
	
	/**
	 * prototype
	 * 
	 * @param msg
	 * @throws SecsWaitReplyMessageException
	 */
	protected void throwSecsWaitReplyMessageException(T msg) throws SecsWaitReplyMessageException {
		throw new SecsWaitReplyMessageException(msg);
	}
	
	/**
	 * prototype (base SECS-I)
	 * 
	 * @param primary-message
	 * @param reply-reply-message
	 * @return reply-message
	 * @throws SecsWaitReplyMessageException
	 */
	protected Optional<T> getOptionalOrThrow(T primary, T reply) throws SecsWaitReplyMessageException {
		return Optional.of(reply);
	}
	
}
