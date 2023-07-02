package com.shimizukenta.secs.local.property.impl;

import com.shimizukenta.secs.local.property.DoubleCompution;

/**
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractDoubleCompution extends AbstractNumberCompution implements DoubleCompution {
	
	private static final long serialVersionUID = 3508416232137836839L;
	
	private static final Double INITIAL = Double.valueOf(0.0D);
	
	/**
	 * Constructor.
	 * 
	 */
	public AbstractDoubleCompution() {
		super(INITIAL);
	}
	
	@Override
	public boolean isDouble() {
		return true;
	}
	
}
