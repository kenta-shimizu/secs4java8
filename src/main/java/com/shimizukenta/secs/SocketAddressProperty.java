package com.shimizukenta.secs;

import java.net.SocketAddress;

public class SocketAddressProperty extends AbstractProperty<SocketAddress> {

	public SocketAddressProperty(SocketAddress initial) {
		super(initial);
	}
	
	public SocketAddress getSocketAddress() {
		synchronized ( this ) {
			SocketAddress a = get();
			if ( a == null ) {
				throw new IllegalStateException("SocketAddress not setted");
			}
			return a;
		}
	}
}
