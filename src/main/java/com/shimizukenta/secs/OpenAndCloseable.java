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
	 * @throws IOException
	 */
	public void open() throws IOException;
	
	/**
	 * Returns is-open.
	 * 
	 * @return {@code true} if opened and not closed
	 */
	public boolean isOpen();
	
	/**
	 * Returns is-closed.
	 * 
	 * @return {@code true} if closed
	 */
	public boolean isClosed();
}
