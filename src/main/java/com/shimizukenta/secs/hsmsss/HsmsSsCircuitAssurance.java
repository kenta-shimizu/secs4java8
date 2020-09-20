package com.shimizukenta.secs.hsmsss;

import java.util.concurrent.Callable;

import com.shimizukenta.secs.AbstractSecsInnerEngine;

public class HsmsSsCircuitAssurance extends AbstractSecsInnerEngine implements Callable<Void> {
	
	private final AbstractHsmsSsCommunicator parent;
	private boolean resetted;

	public HsmsSsCircuitAssurance(AbstractHsmsSsCommunicator parent) {
		super(parent);
		this.parent = parent;
		this.resetted = false;
	}

	/**
	 * until linktest-failed
	 * 
	 */
	@Override
	public Void call() throws Exception {
		
		try {
			
			synchronized ( this ) {
				
				for ( ;; ) {
					
					resetted = false;
					
					parent.hsmsSsConfig().linktest().wait(this);
					
					if ( ! resetted ) {
						
						if ( ! parent.linktest() ) {
							break;
						}
					}
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
