package secs.hsmsSs;

import java.util.Optional;
import java.util.concurrent.Callable;

import secs.SecsException;

public class HsmsSsCircuitAssurance implements Callable<Object> {
	
	private final HsmsSsCommunicator parent;
	private boolean resetted;

	public HsmsSsCircuitAssurance(HsmsSsCommunicator parent) {
		this.parent = parent;
		this.resetted = false;
	}

	/**
	 * until linktest-failed
	 * 
	 */
	@Override
	public Object call() throws Exception {
		
		final Object rtn = new Object();
		
		try {
			
			for ( ;; ) {
				
				Optional<Long> op = parent.hsmsSsConfig().linktest().map(v -> (long)(v * 1000.0F));
				
				synchronized ( this ) {
					
					if ( resetted ) {
						resetted = false;
					}
					
					if ( op.isPresent() ) {
						this.wait(op.get().longValue());
					} else {
						this.wait();
					}
					
					if ( resetted ) {
						resetted = false;
						continue;
					}
				}
				
				if ( linktest() ) {
					return rtn;
				}
			}
		}
		catch ( InterruptedException ignore ) {
		}
		
		return rtn;
	}
	
	public void reset() {
		synchronized ( this ) {
			resetted = true;
			this.notifyAll();
		}
	}
	
	private boolean linktest() throws InterruptedException, SecsException {
		return ! parent.send(parent.createLinktestRequest()).isPresent();
	}

}
