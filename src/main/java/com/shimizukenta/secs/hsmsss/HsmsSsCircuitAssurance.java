package com.shimizukenta.secs.hsmsss;

import java.util.concurrent.Callable;

import com.shimizukenta.secs.AbstractSecsInnerManager;

public class HsmsSsCircuitAssurance extends AbstractSecsInnerManager implements Callable<Object> {
	
	private final HsmsSsCommunicator parent;
	private boolean resetted;

	public HsmsSsCircuitAssurance(HsmsSsCommunicator parent) {
		super(parent);
		this.parent = parent;
		this.resetted = false;
	}

	/**
	 * until linktest-failed
	 * 
	 */
	@Override
	public Object call() throws Exception {
		
		try {
			
			for ( ;; ) {
				
				long t = parent.hsmsSsConfig().linktest()
						.map(v -> (long)(v * 1000.0F))
						.orElse(-1L);
				
				synchronized ( this ) {
					
					if ( resetted ) {
						resetted = false;
					}
					
					if ( t > 0 ) {
						this.wait(t);
					} else {
						this.wait();
					}
					
					if ( resetted ) {
						resetted = false;
						continue;
					}
				}
				
				if ( ! parent.linktest() ) {
					break;
				}
			}
		}
		catch ( InterruptedException ignore ) {
		}
		
		return null;
	}
	
	public void reset() {
		synchronized ( this ) {
			resetted = true;
			this.notifyAll();
		}
	}
	
}
