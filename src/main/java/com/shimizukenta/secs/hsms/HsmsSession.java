package com.shimizukenta.secs.hsms;

public interface HsmsSession extends HsmsCommunicator {
	
	public boolean select() throws InterruptedException;
	
	public boolean deselect() throws InterruptedException;
	
	public boolean separate() throws InterruptedException;
	
}
