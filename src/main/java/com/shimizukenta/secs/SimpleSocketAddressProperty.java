package com.shimizukenta.secs;

import java.net.SocketAddress;

/**
 * SokectAddress value Getter, Setter, Value-Change-Observer<br />
 * 
 * @author kenta-shimizu
 *
 */
public class SimpleSocketAddressProperty extends AbstractSocketAddressProperty {
	
	private static final long serialVersionUID = -7832571895024773026L;

	public SimpleSocketAddressProperty(SocketAddress initial) {
		super(initial);
	}
	
}
