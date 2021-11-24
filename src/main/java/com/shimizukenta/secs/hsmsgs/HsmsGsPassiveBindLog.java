package com.shimizukenta.secs.hsmsgs;

import java.net.SocketAddress;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.shimizukenta.secs.AbstractSecsLog;

public final class HsmsGsPassiveBindLog extends AbstractSecsLog {
	
	private static final long serialVersionUID = -7606695183029225489L;
	
	private final SocketAddress addr;
	private final boolean isBinded;
	private final boolean isClosed;
	
	private String cacheToValueString;
	
	public HsmsGsPassiveBindLog(CharSequence subject, LocalDateTime timestamp, SocketAddress addr, boolean isBinded, boolean isClosed) {
		super(subject, timestamp, addr);
		this.addr = addr;
		this.isBinded = isBinded;
		this.isClosed = isClosed;
		this.cacheToValueString = null;
	}

	public HsmsGsPassiveBindLog(CharSequence subject, SocketAddress addr, boolean isBinded, boolean isClosed) {
		super(subject, addr);
		this.addr = addr;
		this.isBinded = isBinded;
		this.isClosed = isClosed;
		this.cacheToValueString = null;
	}
	
	@Override
	public Optional<String> optionalValueString() {
		
		synchronized ( this ) {
			
			if ( this.cacheToValueString == null ) {
				
				List<String> ll = new ArrayList<>();
				
				if ( this.addr != null ) {
					ll.add(this.addr.toString());
				}
				
				if ( this.isClosed ) {
					
					ll.add("closed");
					
				} else {
					
					ll.add("binded:" + isBinded);
				}
				
				this.cacheToValueString = ll.stream().collect(Collectors.joining(", "));
			}
			
			if ( this.cacheToValueString.isEmpty() ) {
				return Optional.empty();
			} else {
				return Optional.of(this.cacheToValueString);
			}
		}
	}
	
	public SocketAddress socketAddress() {
		return this.addr;
	}
	
	public boolean isBinded() {
		return this.isBinded;
	}
	
	public boolean isClosed() {
		return this.isClosed;
	}
	
	private static final String subjectTryBind = "HSMS-GS Passive Try-Bind";
	
	public static HsmsGsPassiveBindLog tryBind(SocketAddress addr) {
		return new HsmsGsPassiveBindLog(subjectTryBind, addr, false, false);
	}
	
	public static HsmsGsPassiveBindLog tryBind(SocketAddress addr, LocalDateTime timestamp) {
		return new HsmsGsPassiveBindLog(subjectTryBind, timestamp, addr, false, false);
	}
	
	private static final String subjectBinded = "HSMS-GS Passive Binded";
	
	public static HsmsGsPassiveBindLog binded(SocketAddress addr) {
		return new HsmsGsPassiveBindLog(subjectBinded, addr, true, false);
	}
	
	public static HsmsGsPassiveBindLog binded(SocketAddress addr, LocalDateTime timestamp) {
		return new HsmsGsPassiveBindLog(subjectBinded, timestamp, addr, true, false);
	}
	
	private static final String subjectClosed = "HSMS-GS Passive Closed";
	
	public static HsmsGsPassiveBindLog closed(SocketAddress addr) {
		return new HsmsGsPassiveBindLog(subjectClosed, addr, false, true);
	}
	
	public static HsmsGsPassiveBindLog closed(SocketAddress addr, LocalDateTime timestamp) {
		return new HsmsGsPassiveBindLog(subjectClosed, timestamp, addr, false, true);
	}
	
}
