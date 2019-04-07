package secs.secs1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import secs.SecsLog;
import secs.SecsLogListener;

public class Secs1MessageBlockManager {
	
	private final Secs1CommunicatorConfig config;
	private final ExecutorService executorService;
	
	public Secs1MessageBlockManager(Secs1CommunicatorConfig config, ExecutorService executorService) {
		this.config = config;
		this.executorService = executorService;
	}
	
	private final Collection<Secs1MessageReceiveListener> lstnrs = new CopyOnWriteArrayList<>();
	
	public boolean addSecs1MessageReceiveListener(Secs1MessageReceiveListener lstnr) {
		return lstnrs.add(lstnr);
	}
	
	public boolean removeSecs1MessageReceiveListener(Secs1MessageReceiveListener lstnr) {
		return lstnrs.remove(lstnr);
	}
	
	private void putSecs1Message(Secs1Message msg) {
		lstnrs.forEach(lstnr -> {
			lstnr.receive(msg);
		});
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
	
	private final Collection<Integer> resetKeys = new ArrayList<>();
	
	private final Map<Integer, LinkedList<Secs1MessageBlock>> blockMap = new HashMap<>();
	
	public void putBlock(Secs1MessageBlock block) {
		
		synchronized ( this ) {
			
			if ( isDoubleBlock(block) ) {
				putLog(new Secs1MessageBlockLog("Secs1MessageBlock receive double", block));
				return;
			}
			
			final Integer systemBytesKey = block.systemBytesKey();
			
			final LinkedList<Secs1MessageBlock> ll = blockMap.computeIfAbsent(systemBytesKey, key -> new LinkedList<>());
			
			if ( ll.isEmpty() ) {
				
				if ( block.isFirstBlock() ) {
					
					ll.add(block);
					
				} else {
					
					blockMap.remove(systemBytesKey);
					
					putLog(new Secs1MessageBlockLog("Secs1MessageBlock receive not 1st-Block", block));
					
					return;
				}
				
			} else {
				
				if ( block.isFirstBlock() ) {
					
					ll.clear();
					ll.add(block);
					
				} else {
					
					if ( ll.getLast().expectBlock(block) ) {
						
						ll.add(block);
						
						synchronized ( resetKeys ) {
							resetKeys.add(systemBytesKey);
							resetKeys.notifyAll();
						}
						
					} else {
						
						putLog(new Secs1MessageBlockLog("Secs1MessageBlock receive unexpected-block", block));
						
						return;
					}
				}
			}
			
			if ( block.ebit() ) {
				
				putSecs1Message(new Secs1Message(ll));
				blockMap.remove(systemBytesKey);
				
			} else {
				
				synchronized ( resetKeys ) {
					resetKeys.remove(systemBytesKey);
				}
				
				executorService.execute(() -> {
					
					try {
						
						for ( ;; ) {
							
							Collection<Callable<Boolean>> tasks = Arrays.asList(() -> {
								
								/* reset */
								synchronized ( resetKeys ) {
									
									try {
										
										for ( ;; ) {
											
											if ( resetKeys.contains(systemBytesKey) ) {
												
												resetKeys.remove(systemBytesKey);
												
												return Boolean.TRUE;
											}
											
											resetKeys.wait();
										}
									}
									catch ( InterruptedException ignore ) {
									}
								}
								
								return Boolean.FALSE;
							});
							
							long t4 = (long)(config.timeout().t4() * 1000.0F);
							boolean f = executorService.invokeAny(tasks, t4, TimeUnit.MILLISECONDS);
							
							if ( f ) {
								return;
							}
						}
					}
					catch ( TimeoutException e ) {
						
						putLog(new Secs1MessageBlockLog("Secs1MessageBlock receive T4-timeout", block));
						
						blockMap.remove(systemBytesKey);
					}
					catch ( ExecutionException none ) {
					}
					catch ( InterruptedException ignore ) {
					}
					finally {
						synchronized ( resetKeys ) {
							resetKeys.remove(systemBytesKey);
						}
					}
				});
			}
		}
	}
	
	private boolean isDoubleBlock(Secs1MessageBlock ref) {
		
		return blockMap.getOrDefault(ref.systemBytesKey(), new LinkedList<>()).stream()
				.anyMatch(block -> block.sameBlock(ref));
	}
	
}
