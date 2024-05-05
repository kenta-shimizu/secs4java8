package com.shimizukenta.secs.impl;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.shimizukenta.secs.AbstractSecsCommunicatorConfig;
import com.shimizukenta.secs.SecsCommunicator;
import com.shimizukenta.secs.gem.Gem;
import com.shimizukenta.secs.gem.impl.AbstractGem;

/**
 * This abstract class is implementation of SECS-communicate.
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractSecsCommunicator extends AbstractBaseCommunicator implements SecsCommunicator, SecsMessageSendableImpl, SecsMessagePassThroughObservableImpl, SecsLogObservableImpl {
	
//	private final BooleanProperty communicatable = BooleanProperty.newInstance(false);
	
	private final AbstractSecsCommunicatorConfig config;
	private final Gem gem;
	
	public AbstractSecsCommunicator(AbstractSecsCommunicatorConfig config) {
		super();
		
		this.config = config;
		this.gem = new AbstractGem(this, config.gem()) {};
	}
	
	@Override
	public void open() throws IOException {
		
		synchronized (this.syncOpen) {
			super.open();
		}
	}
	
	@Override
	public void close() throws IOException {
		super.close();
	}
	
	@Override
	public Gem gem() {
		return gem;
	}
	
	@Override
	public boolean isEquip() {
		return config.isEquip().booleanValue();
	}
	
	
	@Override
	public void openAndWaitUntilCommunicatable() throws IOException, InterruptedException {
		this.openIfNotOpen();
		this.waitUntilCommunicatable();
	}
	
	@Override
	public void openAndWaitUntilCommunicatable(long timeout, TimeUnit unit) throws IOException, InterruptedException, TimeoutException {
		this.openIfNotOpen();
		this.waitUntilCommunicatable(timeout, unit);
	}
	
	private final Object syncOpen = new Object();
	
	private void openIfNotOpen() throws IOException {
		synchronized ( this.syncOpen ) {
			if (! this.isOpen()) {
				this.open();
			}
		}
	}
	
	
	/* Logger */
	public boolean offerThrowableToLog(Throwable t) {
		return this.secsLogObserver().offerThrowable(t);
	}
	
}
