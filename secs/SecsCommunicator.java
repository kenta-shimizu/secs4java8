package secs;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class SecsCommunicator implements Closeable {

	private final SecsCommunicatorConfig config;
	
	private boolean opened;
	private boolean closed;
	
	public SecsCommunicator(SecsCommunicatorConfig config) {
		
		this.config = config;
		
		opened = false;
		closed = false;
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
	
	protected int deviceId() {
		return config.deviceId();
	}
	
	
	//TODO
	//send
	
	
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
	
	
	//TOOD
	//communication
	
}
