package com.shimizukenta.secs.hsms.impl;

import java.util.Optional;

import com.shimizukenta.secs.hsms.HsmsException;
import com.shimizukenta.secs.hsms.HsmsMessage;
import com.shimizukenta.secs.hsms.HsmsSendMessageException;
import com.shimizukenta.secs.hsms.HsmsWaitReplyMessageException;
import com.shimizukenta.secs.local.property.BooleanProperty;
import com.shimizukenta.secs.local.property.ChangeListener;
import com.shimizukenta.secs.local.property.TimeoutAndUnit;
import com.shimizukenta.secs.local.property.TimeoutProperty;

public abstract class AbstractHsmsLinktest implements HsmsLinktest {
	
	private final Object sync = new Object();
	
	private final ChangeListener<TimeoutAndUnit> lstnr = n -> {
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
				
				if ( this.doLinktest().booleanValue() ) {
					
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
	
	abstract protected TimeoutProperty timer();
	abstract protected BooleanProperty doLinktest();
	
	abstract protected Optional<HsmsMessage> send()
			throws HsmsSendMessageException,
			HsmsWaitReplyMessageException,
			HsmsException,
			InterruptedException;
	
}
