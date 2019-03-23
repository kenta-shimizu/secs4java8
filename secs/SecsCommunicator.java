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
	
	
	private final Collection<SecsMessageReceiveListener> msgRecvListeners = new CopyOnWriteArrayList<>();
	
	public boolean addSecsMessageReceiveListener(SecsMessageReceiveListener lstnr) {
		return msgRecvListeners.add(lstnr);
	}
	
	public boolean removeSecsMessageReceiveListener(SecsMessageReceiveListener lstnr) {
		return msgRecvListeners.remove(lstnr);
	}
	
	protected void putReceiveMessage(SecsMessage msg) {
		msgRecvListeners.forEach(lstnr -> {
			lstnr.receive(msg);
		});
	}
	
	
	//TODO
	//log
	
	//TOOD
	//communication
	
}
