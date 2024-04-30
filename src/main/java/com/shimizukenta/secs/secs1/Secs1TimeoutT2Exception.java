package com.shimizukenta.secs.secs1;

public class Secs1TimeoutT2Exception extends Secs1Exception {
	
	private static final long serialVersionUID = -8440472823612779735L;
	
	public Secs1TimeoutT2Exception(Object obj) {
		super("T2-Timeout waiting=" + obj.toString());
	}
	
}
