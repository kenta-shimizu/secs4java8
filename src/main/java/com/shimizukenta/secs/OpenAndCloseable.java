package com.shimizukenta.secs;

import java.io.Closeable;
import java.io.IOException;

/**
 * This interface has Open/Close and isOpen/isClosed methods.
 * 
 * @author kenta-shimizu
 *
 */
public interface OpenAndCloseable extends Closeable {
	
	/**
	 * open.
	 * 
	 * @throws IOException if open failed
	 */
	public void open() throws IOException;
	
	/**
	 * Return true if opened and not closed, otherwise false.
	 * 
	 * @return true if opened and not closed, otherwise false
	 */
	public boolean isOpen();
	
	/**
	 * Return true if closed, otherwise false.
	 * 
	 * @return true if closed, otherwise false
	 */
	public boolean isClosed();
}
