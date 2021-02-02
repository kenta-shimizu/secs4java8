package com.shimizukenta.secs;

import java.net.SocketAddress;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractSecsConnectionLog extends AbstractSecsLog implements SecsConnectionLog {
	
	private static final long serialVersionUID = 100182214359308094L;
	
	private final SocketAddress local;
	private final SocketAddress remote;
	private final boolean isConneting;
	
	private String cacheToStringValue;
	
	public AbstractSecsConnectionLog(CharSequence subject, LocalDateTime timestamp, SocketAddress local, SocketAddress remote, boolean isConnecting) {
		super(subject, timestamp);
		this.local = local;
		this.remote = remote;
		this.isConneting = isConnecting;
		this.cacheToStringValue = null;
	}
	
	public AbstractSecsConnectionLog(CharSequence subject, SocketAddress local, SocketAddress remote, boolean isConnecting) {
		super(subject);
		this.local = local;
		this.remote = remote;
		this.isConneting = isConnecting;
		this.cacheToStringValue = null;
	}
	
	@Override
	public Optional<String> optionalValueString() {
		
		synchronized ( this ) {
			
			if ( this.cacheToStringValue == null ) {
				
				List<String> ll = new ArrayList<>();
				
				if ( this.local != null ) {
					ll.add("local:" + this.local.toString());
				}
				
				if ( this.remote != null ) {
					ll.add("remote:" + this.remote.toString());
				}
				
				ll.add("isOpen:" + this.isConneting);
				
				this.cacheToStringValue = ll.stream().collect(Collectors.joining(", "));
			}
			
			if ( this.cacheToStringValue.isEmpty() ) {
				return Optional.empty();
			} else {
				return Optional.of(this.cacheToStringValue);
			}
		}
	}
	
	@Override
	public SocketAddress local() {
		return this.local;
	}
	
	@Override
	public SocketAddress remote() {
		return this.remote;
	}
	
	@Override
	public boolean isConnecting() {
		return this.isConneting;
	}
	
}
