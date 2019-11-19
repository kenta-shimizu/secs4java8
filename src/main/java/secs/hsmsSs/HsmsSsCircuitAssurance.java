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
				
				if ( ! linktest() ) {
					break;
				}
			}
		}
		catch ( SecsException ignore ) {
			/* Linktest failed */
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
	
	/**
	 * 
	 * @return true if success
	 * @throws InterruptedException
	 * @throws SecsException
	 */
	private boolean linktest() throws InterruptedException, SecsException {
		return parent.send(parent.createLinktestRequest()).isPresent();
	}

}
