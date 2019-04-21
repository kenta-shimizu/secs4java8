package secs;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import secs.gem.Gem;
import secs.secs2.Secs2;
import secs.sml.SmlMessage;

public abstract class SecsCommunicator implements Closeable {

	private final SecsCommunicatorConfig config;
	private final Gem gem;
	
	protected boolean opened;
	protected boolean closed;
	private boolean lastCommunicatable;
	
	public SecsCommunicator(SecsCommunicatorConfig config) {
		
		this.config = config;
		this.gem = new Gem(this, config.gem());
		
		opened = false;
		closed = false;
		lastCommunicatable = false;
	}
	
	public void open() throws IOException {
		
		synchronized ( this ) {
			
			if ( closed ) {
				throw new IOException("Already closed");
			}
			
			if ( opened ) {
				throw new IOException("Already opened");
			}
			
			opened = true;
		}
	}

	@Override
	public void close() throws IOException {
		
		synchronized ( this ) {
			
			if ( closed ) {
				return ;
			}
			
			closed = true;
		}
	}
	
	public Gem gem() {
		return gem;
	}
	
	public int deviceId() {
		return config.deviceId();
	}
	
	public boolean isEquip() {
		return config.isEquip();
	}
	
	/**
	 * Blocking-method<br />
	 * send primary-message,<br />
	 * wait until reply-message if exist
	 * 
	 * @param strm
	 * @param func
	 * @param wbit
	 * @return reply-message if exist
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<? extends SecsMessage> send(int strm, int func, boolean wbit)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		return send(strm, func, wbit, Secs2.empty());
	}
	
	/**
	 * Blocking-method<br />
	 * send primary-message,<br />
	 * wait until reply-message if exist
	 * 
	 * @param strm
	 * @param func
	 * @param wbit
	 * @param secs2
	 * @return reply-message if exist
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	abstract public Optional<? extends SecsMessage> send(int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException;
	
	/**
	 * send reply-message
	 * 
	 * @param primary (primary-message)
	 * @param strm
	 * @param func
	 * @param wbit
	 * @return Optional.empty()
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<? extends SecsMessage> send(SecsMessage primary, int strm, int func, boolean wbit)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		return send(primary, strm, func, wbit, Secs2.empty());
	}
	
	/**
	 * send reply-message
	 * 
	 * @param primary (primary-message)
	 * @param strm
	 * @param func
	 * @param wbit
	 * @param secs2
	 * @return Optional.empty()
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	abstract public Optional<? extends SecsMessage> send(SecsMessage primary, int strm, int func, boolean wbit, Secs2 secs2)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException;
	
	/**
	 * Blocking-method<br />
	 * send primary-message,<br />
	 * wait until reply-message if exist
	 * 
	 * @param sml
	 * @return reply-message if exist
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<? extends SecsMessage> send(SmlMessage sml)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		return send(sml.getStream(), sml.getFunction(), sml.wbit(), sml.secs2());
	}
	
	/**
	 * send reply-message
	 * 
	 * @param primary-message
	 * @param sml
	 * @return Optional.empty()
	 * @throws SecsSendMessageException
	 * @throws SecsWaitReplyMessageException
	 * @throws SecsException
	 * @throws InterruptedException
	 */
	public Optional<? extends SecsMessage> send(SecsMessage primary, SmlMessage sml)
			throws SecsSendMessageException, SecsWaitReplyMessageException, SecsException
			, InterruptedException {
		
		return send(primary, sml.getStream(), sml.getFunction(), sml.wbit(), sml.secs2());
	}
	
	
	/* Secs-Message Receive Listener */
	private final Collection<SecsMessageReceiveListener> msgRecvListeners = new CopyOnWriteArrayList<>();
	
	public boolean addSecsMessageReceiveListener(SecsMessageReceiveListener lstnr) {
		return msgRecvListeners.add(lstnr);
	}
	
	public boolean removeSecsMessageReceiveListener(SecsMessageReceiveListener lstnr) {
		return msgRecvListeners.remove(lstnr);
	}
	
	protected void notifyReceiveMessage(SecsMessage msg) {
		msgRecvListeners.forEach(lstnr -> {
			lstnr.receive(msg);
		});
	}
	
	
	/* Secs-Log Receive Listener */
	private final Collection<SecsLogListener> logListeners = new CopyOnWriteArrayList<>();
	
	public boolean addSecsLogListener(SecsLogListener lstnr) {
		return logListeners.add(lstnr);
	}
	
	public boolean removeSecsLogListener(SecsLogListener lstnr) {
		return logListeners.remove(lstnr);
	}
	
	protected void notifyLog(SecsLog log) {
		logListeners.forEach(lstnr -> {
			lstnr.receive(log);
		});
	}
	
	
	/* Secs-Communicatable-State-Changed-Listener */
	private final Collection<SecsCommunicatableStateChangeListener> commStateChangeListeners = new CopyOnWriteArrayList<>();
	
	public boolean addSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeListener lstnr) {
		
		synchronized ( commStateChangeListeners ) {
			
			lstnr.changed(lastCommunicatable);
			
			return commStateChangeListeners.add(lstnr);
		}
	}
	
	public boolean removeSecsCommunicatableStateChangeListener(SecsCommunicatableStateChangeListener lstnr) {
		return commStateChangeListeners.remove(lstnr);
	}
	
	protected void notifyCommunicatableStateChange(boolean communicatable) {
		
		synchronized ( commStateChangeListeners ) {
			
			if ( lastCommunicatable != communicatable ) {
				
				lastCommunicatable = communicatable;
				
				commStateChangeListeners.forEach(lstnr -> {
					lstnr.changed(lastCommunicatable);
				});
			}
		}
	}
	
}
