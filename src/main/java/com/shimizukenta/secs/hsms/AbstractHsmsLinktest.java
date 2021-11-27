package com.shimizukenta.secs.hsms;

import java.util.Optional;

import com.shimizukenta.secs.PropertyChangeListener;
import com.shimizukenta.secs.ReadOnlyTimeProperty;

public abstract class AbstractHsmsLinktest implements HsmsLinktest {
	
	private final Object sync = new Object();
	
	private final PropertyChangeListener<Number> lstnr = n -> {
		synchronized ( this.sync ) {
			this.sync.notifyAll();
		}
	};
	
	private boolean resetted;
	
	public AbstractHsmsLinktest() {
		this.resetted = false;
	}
	
	@Override
	public void testing()
			throws HsmsSendMessageException,
			HsmsWaitReplyMessageException,
			HsmsException,
			InterruptedException {
		
		try {
			this.timer().addChangeListener(lstnr);
			
			for ( ;; ) {
				
				if ( this.timer().gtZero() ) {
					
					synchronized ( this.sync ) {
						
						this.resetted = false;
						
						this.timer().wait(this.sync);
						
						if ( this.resetted ) {
							continue;
						}
					}
					
					if ( ! this.send().isPresent() ) {
						throw new HsmsException();
					}
					
				} else {
					
					synchronized ( this.sync ) {
						this.sync.wait();
					}
				}
				
				this.resetted = false;
			}
		}
		finally {
			this.timer().removeChangeListener(lstnr);
		}
	}
	
	@Override
	public void resetTimer() {
		synchronized ( this.sync ) {
			this.resetted = true;
			this.sync.notifyAll();
		}
	}
	
	abstract protected ReadOnlyTimeProperty timer();
	
	abstract protected Optional<HsmsMessage> send()
			throws HsmsSendMessageException,
			HsmsWaitReplyMessageException,
			HsmsException,
			InterruptedException;
	
}
