package com.shimizukenta.secs.hsmsss;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class HsmsSsSendReplyManager {
	
	private final Collection<MsgPack> packs = new ArrayList<>();
	
	private final HsmsSsCommunicator parent;
	
	public HsmsSsSendReplyManager(HsmsSsCommunicator parent) {
		this.parent = parent;
	}
	
	public void reset() {
		synchronized ( this ) {
			packs.clear();
		}
	}
	
	public Optional<HsmsSsMessage> send(HsmsSsMessage msg) throws InterruptedException {
		
		HsmsSsMessageType type = HsmsSsMessageType.get(msg);
		
		switch ( type ) {
		case SELECT_REQ:
		case LINKTEST_REQ: {
			
			//TOOD
			
			Optional.empty();
			/* break; */
		}
		case DATA: {
			
			if ( msg.wbit() ) {
				
				//TOOD
				return Optional.empty();
				
			} else {
				
				Optional.empty();
			}
			/* break */
		}
		default: {
			
			return Optional.empty();
		}
		}
	}
	
	public Optional<HsmsSsMessage> put(HsmsSsMessage msg) {
		
		final Integer key = msg.systemBytesKey();
		
		synchronized ( this ) {
			
			for ( MsgPack p : packs ) {
				
				if ( p.key().equals(key) ) {
					
					p.put(msg);
					this.notifyAll();
					return Optional.empty();
				}
			}
			
			return Optional.of(msg);
		}
	}
	
	private static class MsgPack {
		
		private final HsmsSsMessage primary;
		private final Integer key;
		private HsmsSsMessage reply;
		
		public MsgPack(HsmsSsMessage primaryMsg) {
			this.primary = primaryMsg;
			this.key = primary.systemBytesKey();
			this.reply = null;
		}
		
		public Integer key() {
			return key;
		}
		
		public void put(HsmsSsMessage replyMsg) {
			synchronized ( this ) {
				this.reply = replyMsg;
			}
		}
		
		public HsmsSsMessage replyMsg() {
			synchronized ( this ) {
				return reply;
			}
		}
	}

}
