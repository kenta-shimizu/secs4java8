package com.shimizukenta.secs.util;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import com.shimizukenta.secs.SecsCommunicator;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.secs2.Secs2Exception;

public abstract class AbstractEntityEventAdapter implements EntityEventAdapter {
	
	private final Collection<EntityCommunicatableStateChangeListener> communicatableLstners = new CopyOnWriteArrayList<>();
	private final Set<SxFyAndEntityMessageReceiveListener> msgRecvLstnrs = new CopyOnWriteArraySet<>();
	
	private final Object syncReplySxF0 = new Object();
	private boolean isReplySxF0;
	private final Object syncReplyS9Fy = new Object();
	private boolean isReplyS9Fy;
	
	public AbstractEntityEventAdapter() {
		this.isReplySxF0 = false;
		this.isReplyS9Fy = false;
	}
	
	@Override
	public void changed(boolean communicatable, SecsCommunicator communicator) {
		for ( EntityCommunicatableStateChangeListener l : this.communicatableLstners ) {
			l.changed(communicatable, communicator);
		}
	}
	
	@Override
	public void received(SecsMessage message, SecsCommunicator communicator) {
		
		try {
			
			if  ( equalsDeviceIdOrSessionId(message, communicator) ) {
				
				final int strm = message.getStream();
				final int func = message.getFunction();
				
				final EntityMessageReceiveListener l = this.msgRecvLstnrs.stream()
						.filter(x -> x.getStream() == strm)
						.filter(x -> x.getFunction() == func)
						.map(x -> x.getMessageReceiveListener())
						.findAny()
						.orElse(null);
				
				if ( l == null ) {
					
					final boolean wbit = message.wbit();
					
					if (this.msgRecvLstnrs.stream().anyMatch(x -> x.getStream() == strm)) {
						
						if ( wbit && this.isReplySxF0() ) {
							communicator.send(message, strm, 0, false);
						}
						
						if ( this.isReplyS9Fy() ) {
							communicator.gem().s9f5(message);
						}
						
					} else {
						
						if ( wbit && this.isReplySxF0() ) {
							communicator.send(message, 0, 0, false);
						}
						
						if ( this.isReplyS9Fy() ) {
							communicator.gem().s9f3(message);
						}
					}
					
				} else {
					
					try {
						l.received(message, communicator);
					}
					catch ( Secs2Exception e ) {
						communicator.gem().s9f7(message);
					}
				}
				
			} else {
				
				if ( this.isReplyS9Fy() ) {
					communicator.gem().s9f1(message);
				}
			}
		}
		catch ( InterruptedException ignore ) {
		}
		catch ( SecsException giveup ) {
		}
	}
	
	private static boolean equalsDeviceIdOrSessionId(SecsMessage message, SecsCommunicator communicator) {
		
		int msgId = message.deviceId();
		int commId = communicator.deviceId();
		
		if ( msgId < 0 ) {
			msgId = message.sessionId();
		}
		
		if ( commId < 0 ) {
			commId = communicator.sessionId();
		}
		
		if ( msgId < 0 || commId < 0 ) {
			return false;
		} else {
			return msgId == commId;
		}
	}
	
	@Override
	public void setReplySxF0(boolean doReply) {
		synchronized ( this.syncReplySxF0 ) {
			this.isReplySxF0 = doReply;
		}
	}
	
	protected boolean isReplySxF0() {
		synchronized ( this .syncReplySxF0 ) {
			return this.isReplySxF0;
		}
	}
	
	@Override
	public void setReplyS9Fy(boolean doReply) {
		synchronized ( this.syncReplyS9Fy ) {
			this.isReplyS9Fy = doReply;
		}
	}
	
	protected boolean isReplyS9Fy() {
		synchronized ( this.syncReplyS9Fy ) {
			return this.isReplyS9Fy;
		}
	}
	
	@Override
	public boolean addCommunicatableStateChangeListener(EntityCommunicatableStateChangeListener listener) {
		return this.communicatableLstners.add(listener);
	}
	
	@Override
	public boolean removeCommunicatableStateChangeListener(EntityCommunicatableStateChangeListener listener) {
		return this.communicatableLstners.remove(listener);
	}
	
	@Override
	public boolean addMessageReceiveListener(int strm, int func, EntityMessageReceiveListener listener) {
		return this.msgRecvLstnrs.add(new SxFyAndEntityMessageReceiveListener(strm, func, listener));
	}
	
	@Override
	public boolean removeMessageReceiveListener(int strm, int func) {
		return this.msgRecvLstnrs.removeIf(l -> {
			return l.getStream() == strm && l.getFunction() == func;
		});
	}
	
	@Override
	public void adaptToSecsCommunicator(SecsCommunicator comm) {
		comm.addSecsCommunicatableStateChangeListener(this);
		comm.addSecsMessageReceiveListener(this);
	}
	
}
