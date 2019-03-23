package secs;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SecsMessageAnswerManager<T extends SecsMessage> {
	
	private final ExecutorService execServ;
	private final Map<Integer, T> map = new HashMap<>();
	
	public SecsMessageAnswerManager(ExecutorService es) {
		this.execServ = es;
	}
	
	protected ExecutorService executorService() {
		return execServ;
	}
	
	/**
	 * 
	 * @param primary-message
	 * @throws InterruptedException
	 */
	public void entry(T msg) {
		synchronized ( this ) {
			map.put(msg.systemBytesKey(), null);
		}
	}
	
	/**
	 * Blocking-method<br />
	 * wait until Receive Answer-Message
	 * 
	 * @param primary-message
	 * @param timeout-seconds
	 * @return 2nd-message
	 * @throws TimeoutException
	 * @throws InterruptedException
	 */
	public Optional<T> waitAnswer(T msg, float timeout_seconds) throws TimeoutException, InterruptedException {
		
		if ( isWaitAnswerMessage(msg) ) {
			
			Future<T> f = execServ.submit(() -> {
				
				final Integer key = msg.systemBytesKey();
				
				try {
					synchronized ( this ) {
						
						for ( ;; ) {
							
							if ( ! map.containsKey(key) ) {
								break;
							}
							
							T t = map.get(key);
							if ( t != null ) {
								return t;
							}
							
							this.wait();
						}
					}
				}
				catch ( InterruptedException ignore ) {
				}
				finally {
					map.remove(key);
				}
				
				return null;
			});
			
			try {
				long tm = (long)(timeout_seconds * 1000.0F);
				T t = f.get(tm, TimeUnit.MILLISECONDS);
				if ( t != null ) {
					return Optional.of(t);
				}
			}
			catch ( InterruptedException e ) {
				f.cancel(true);
				throw e;
			}
			catch ( ExecutionException giveup ) {
			}
		}
		
		return Optional.empty();
	}
	
	/**
	 * 
	 * 
	 * @param msg
	 * @return Optional.empty() if message is answer-message, else Optional#exist
	 */
	public Optional<T> put(T msg) {
		
		synchronized ( this ) {
			
			final Integer key = msg.systemBytesKey();
			
			if ( map.containsKey(key) ) {
				
				map.put(key, msg);
				this.notifyAll();
				
				return Optional.empty();
				
			} else {
				
				return Optional.of(msg);
				
			}
		}
	}
	
	/**
	 * 
	 * @param msg
	 */
	public void reset(T msg) {
		synchronized ( this ) {
			map.remove(msg.systemBytesKey());
			this.notifyAll();
		}
	}
	
	protected boolean isWaitAnswerMessage(T msg) {
		return msg.wbit();
	}
	
}
