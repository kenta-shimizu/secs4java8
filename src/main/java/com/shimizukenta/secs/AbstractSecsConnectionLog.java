package com.shimizukenta.secs;

import java.net.SocketAddress;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractSecsConnectionLog extends AbstractSecsLog {
	
	private static final long serialVersionUID = 100182214359308094L;
	
	private final SocketAddress local;
	private final SocketAddress remote;
	private final boolean isConneting;
	
	private String cacheValueString;
	
	public AbstractSecsConnectionLog(CharSequence subject, LocalDateTime timestamp, SocketAddress local, SocketAddress remote, boolean isConnecting) {
		super(subject, timestamp);
		this.local = local;
		this.remote = remote;
		this.isConneting = isConnecting;
		this.cacheValueString = null;
	}
	
	public AbstractSecsConnectionLog(CharSequence subject, SocketAddress local, SocketAddress remote, boolean isConnecting) {
		super(subject);
		this.local = local;
		this.remote = remote;
		this.isConneting = isConnecting;
		this.cacheValueString = null;
	}
	
	@Override
	protected Optional<String> toStringValue() {
		
		synchronized ( this ) {
			
			if ( this.cacheValueString == null ) {
				
				List<String> ll = new ArrayList<>();
				
				if ( this.local != null ) {
					ll.add("local:" + this.local.toString());
				}
				
				if ( this.remote != null ) {
					ll.add("remote:" + this.remote.toString());
				}
				
				ll.add("isOpen: " + this.isConneting);
				
				this.cacheValueString = ll.stream().collect(Collectors.joining(", "));
			}
			
			if ( this.cacheValueString.isEmpty() ) {
				return Optional.empty();
			} else {
				return Optional.of(this.cacheValueString);
			}
		}
	}
	
	public SocketAddress local() {
		return this.local;
	}
	
	public SocketAddress remote() {
		return this.remote;
	}
	
	public boolean isConnecting() {
		return this.isConneting;
	}
	
}
