package com.shimizukenta.secs.local.property.impl;

import com.shimizukenta.secs.local.property.LogicalCompution;

/**
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractLogicalCompution extends AbstractBooleanCompution implements LogicalCompution {
	
	private static final long serialVersionUID = -2490720320353424853L;
	
	/**
	 * Constructor.
	 * 
	 */
	public AbstractLogicalCompution() {
		super(Boolean.FALSE);
	}
	
}
