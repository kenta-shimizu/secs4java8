package secs.secs1;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

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
