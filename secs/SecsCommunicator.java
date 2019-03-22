package secs;

import java.io.Closeable;
import java.io.IOException;

public abstract class SecsCommunicator implements Closeable {

	private final SecsConfig config;
	
	private boolean opened;
	private boolean closed;
	
	public SecsCommunicator(SecsConfig config) {
		
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
	
	//TODO
	//listeners
	
}
