package secs.secs1;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

import secs.SecsLog;
import secs.SecsLogListener;

public class Secs1MessageBlockManager {
	
	private final Secs1CommunicatorConfig config;
	
	public Secs1MessageBlockManager(Secs1CommunicatorConfig config) {
		this.config = config;
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
	
	
	private final LinkedList<Secs1MessageBlock> blocks = new LinkedList<>();
	
	public void putBlock(Secs1MessageBlock block) {
		
		synchronized ( this ) {
			
			if ( block.deviceId() != config.deviceId() ) {
				return;
			}
			
			if ( blocks.isEmpty() ) {
				
				blocks.add(block);
				
			} else {
				
				Secs1MessageBlock ref = blocks.getLast();
				
				if ( block.sameBlock(ref) ) {
					return;
				}
				
				if ( ! block.expectBlock(ref) ) {
					blocks.clear();
				}
				
				blocks.addLast(block);
			}
			
			if ( block.ebit() ) {
				
				Secs1Message m = new Secs1Message(blocks);
				putSecs1Message(m);
				blocks.clear();
			}
		}
	}

}
