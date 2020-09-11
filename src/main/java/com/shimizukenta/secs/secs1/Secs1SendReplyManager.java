package com.shimizukenta.secs.secs1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeoutException;

import com.shimizukenta.secs.AbstractSecsInnerEngine;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.SecsWaitReplyMessageException;
import com.shimizukenta.secs.secs2.Secs2Exception;

public class Secs1SendReplyManager extends AbstractSecsInnerEngine {
	
	private final Collection<Pack> packs = new ArrayList<>();
	private final BlockingQueue<Secs1MessageBlock> sendBlockQueue = new LinkedBlockingQueue<>();
	private final LinkedList<Secs1MessageBlock> recvBlocks = new LinkedList<>();
	
	private final ReplyStatus resetTimerStatus;
	private final ReplyStatus terminateStatus;
	
	private final Secs1Communicator parent;
	
	protected Secs1SendReplyManager(Secs1Communicator parent) {
		super(parent);
		
		this.parent = parent;
		
		this.resetTimerStatus = new ReplyStatus();
		this.resetTimerStatus.resetTimer = true;
		
		this.terminateStatus = new ReplyStatus();
		this.terminateStatus.terminate = true;
	}
	
	public Optional<Secs1Message> send(Secs1Message msg)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		Pack p = entry(msg);
		
		parent.circuitNotifyAll();
		
		try {
			
			notifyLog("Secs1-Message entry-send", msg);
			notifyTrySendMessagePassThrough(msg);
			waitUntilSended(p);
			notifySendedMessagePassThrough(msg);
			
			if ( msg.wbit() ) {
				
				return Optional.of(reply(p));
				
			} else {
				
				return Optional.empty();
			}
		}
		finally {
			remove(p);
		}
	}
	
	private void waitUntilSended(Pack p)
			throws SecsSendMessageException, SecsException, InterruptedException {
		
		final Callable<Pack> sendedTask = () -> {
			try {
				synchronized ( packs ) {
					for ( ;; ) {
						if ( p.isSended() ) {
							break;
						}
						packs.wait();
					}
				}
			}
			catch ( InterruptedException ignore ) {
			}
			
			return p;
		};
		
		final Callable<Pack> failedTask = () -> {
			try {
				synchronized ( packs ) {
					for ( ;; ) {
						if ( p.failedCause() != null ) {
							break;
						}
						packs.wait();
					}
				}
			}
			catch ( InterruptedException ignore ) {
			}
			
			return p;
		};
		
		final Callable<Pack> terminateTask = () -> {
			try {
				synchronized ( packs ) {
					for ( ;; ) {
						if ( packs.isEmpty() ) {
							break;
						}
						packs.wait();
					}
				}
			}
			catch ( InterruptedException ignore ) {
			}
			
			return null;
		};
		
		try {
			Pack pp = executeInvokeAny(sendedTask, failedTask, terminateTask);
			
			if ( pp == null ) {
				throw new Secs1DetectTerminateException(p.primaryMsg());
			}
			
			if ( pp.failedCause() != null ) {
				throw new Secs1SendMessageException(pp.primaryMsg(), pp.failedCause());
			}
		}
		catch ( ExecutionException e ) {
			
			Throwable t = e.getCause();
			
			if ( t instanceof RuntimeException ) {
				throw (RuntimeException)t;
			}
			
			throw new Secs1SendMessageException(p.primaryMsg(), e.getCause());
		}
		
	}
	
	private Secs1Message reply(Pack p)
			throws SecsWaitReplyMessageException, SecsException, InterruptedException {
		
		final Callable<ReplyStatus> replyTask = () -> {
			try {
				synchronized ( packs ) {
					for ( ;; ) {
						Secs1Message reply = p.replyMsg();
						if ( reply != null ) {
							return new ReplyStatus(reply);
						}
						packs.wait();
					}
				}
			}
			catch ( InterruptedException ignore ) {
			}
			
			return terminateStatus;
		};
		
		final Callable<ReplyStatus> resetTimerTask = () -> {
			try {
				synchronized (resetTimerStatus) {
					resetTimerStatus.wait();
					return resetTimerStatus;
				}
			}
			catch ( InterruptedException ignore ) {
			}
			
			return terminateStatus;
		};
		
		final Callable<ReplyStatus> terminateTask = () -> {
			try {
				synchronized ( packs ) {
					for ( ;; ) {
						if ( packs.isEmpty() ) {
							break;
						}
						packs.wait();
					}
				}
			}
			catch ( InterruptedException ignore ) {
			}
			
			return terminateStatus;
		};
		
		try {
			
			for ( ;; ) {
				
				ReplyStatus r = executeInvokeAny(
						replyTask, resetTimerTask, terminateTask,
						parent.secs1Config().timeout().t3());
				
				if ( r.resetTimer() ) {
					continue;
				}
				
				if ( r.terminate() ) {
					throw new Secs1DetectTerminateException(p.primaryMsg());
				}
				
				return r.reply();
			}
		}
		catch ( TimeoutException e ) {
			throw new Secs1TimeoutT3Exception(p.primaryMsg(), e);
		}
		catch ( ExecutionException e ) {
			
			Throwable t = e.getCause();
			
			if ( t instanceof RuntimeException ) {
				throw (RuntimeException)t;
			}
			
			throw new SecsException(e);
		}
	}
	
	public Secs1MessageBlock pollBlock() {
		return sendBlockQueue.poll();
	}
	
	private void put(Secs1Message msg) throws InterruptedException {
		
		final Integer key = msg.systemBytesKey();
		
		synchronized ( packs ) {
			
			for ( Pack p : packs ) {
				if ( p.key().equals(key) ) {
					p.put(msg);
					packs.notifyAll();
					return;
				}
			}
		}
		
		notifyReceiveMessage(msg);
	}
	
	public void clear() {
		synchronized ( packs ) {
			sendBlockQueue.clear();
			packs.clear();
			packs.notifyAll();
		}
	}
	
	private Pack entry(Secs1Message msg) throws Secs1SendMessageException {
		
		synchronized ( packs ) {
			
			List<Secs1MessageBlock> blocks = msg.toBlocks();
			
			Pack p = new Pack(msg);
			packs.add(p);
			
			blocks.forEach(sendBlockQueue::offer);
			
			return p;
		}
	}
	
	private void remove(Pack p) {
		synchronized ( packs ) {
			packs.remove(p);
		}
	}
	
	public void sended(Secs1MessageBlock block) {
		
		notifyLog("Secs1-Message-Block sended", block);
		
		if ( block.ebit() ) {
			final Integer key = block.systemBytesKey();
			
			synchronized ( packs ) {
				for ( Pack p : packs ) {
					if ( p.key().equals(key) ) {
						p.sended();
					}
				}
				
				packs.notifyAll();
			}
		}
	}
	
	public void sendFailed(Secs1MessageBlock block, Exception e) {
		
		final Integer key = block.systemBytesKey();
		
		synchronized ( packs ) {
			
			for ( Pack p : packs ) {
				if ( p.key().equals(key) ) {
					p.failedCause(e);
				}
			}
			
			sendBlockQueue.removeIf(q -> q.systemBytesKey().equals(key));
			
			packs.notifyAll();
		}
	}
	
	public void received(Secs1MessageBlock block) throws InterruptedException {
		
		notifyLog("Secs1-Message-Block received", block);
		
		if ( recvBlocks.isEmpty() ) {
			
			if ( block.isFirst() ) {
				
				recvBlocks.add(block);
				
			} else {
				
				return;
			}
			
		} else {
			
			Secs1MessageBlock prevBlock = recvBlocks.getLast();
			
			if ( prevBlock.sameSystemBytes(block) ) {
				
				if ( prevBlock.isNextBlock(block) ) {
					
					recvBlocks.add(block);
					
				} else {
					
					return;
				}
				
			} else {
				
				if ( block.isFirst() ) {
					
					recvBlocks.clear();
					recvBlocks.add(block);
					
				} else {
					
					return;
				}
			}
		}
		
		if ( block.ebit() ) {
			
			try {
				Secs1Message msg = Secs1MessageBlockConverter.toSecs1Message(recvBlocks);
				notifyReceiveMessagePassThrough(msg);
				notifyLog("Secs1-Message received", msg);
				put(msg);
			}
			catch ( Secs2Exception e ) {
				notifyLog(e);
			}
			
			recvBlocks.clear();
			
		} else {
			
			synchronized ( resetTimerStatus ) {
				resetTimerStatus.notifyAll();
			}
		}
	}
	
	private class Pack {
		
		private final Secs1Message primary;
		private final Integer key;
		private Secs1Message reply;
		private boolean sended;
		private Exception failedCause;
		
		public Pack(Secs1Message primaryMsg) {
			this.primary = primaryMsg;
			this.key = primaryMsg.systemBytesKey();
			this.reply = null;
			this.sended = false;
			this.failedCause = null;
		}
		
		public Integer key() {
			return key;
		}
		
		public void put(Secs1Message replyMsg) {
			synchronized ( this ) {
				this.reply = replyMsg;
			}
		}
		
		public void sended() {
			synchronized ( this ) {
				this.sended = true;
			}
		}
		
		public boolean isSended() {
			synchronized ( this ) {
				return sended;
			}
		}
		
		public Secs1Message primaryMsg() {
			return primary;
		}
		
		public Secs1Message replyMsg() {
			synchronized ( this ) {
				return reply;
			}
		}
		
		public Exception failedCause() {
			synchronized ( this ) {
				return failedCause;
			}
		}
		
		public void failedCause(Exception e) {
			synchronized ( this ) {
				failedCause = e;
			}
		}
		
		@Override
		public int hashCode() {
			return key.hashCode();
		}
		
		@Override
		public boolean equals(Object o) {
			if ((o != null) && (o instanceof Pack)) {
				return ((Pack)o).key.equals(key);
			} else {
				return false;
			}
		}
	}
	
	private class ReplyStatus {
		
		private Secs1Message reply;
		private boolean resetTimer;
		private boolean terminate;
		
		public ReplyStatus() {
			this(null);
		}
		
		public ReplyStatus(Secs1Message msg) {
			reply = msg;
			resetTimer = false;
			terminate = false;
		}
		
		public Secs1Message reply() {
			return reply;
		}
		
		public boolean resetTimer() {
			return resetTimer;
		}
		
		public boolean terminate() {
			return terminate;
		}
	}
	
}
