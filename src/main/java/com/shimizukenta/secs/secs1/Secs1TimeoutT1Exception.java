package com.shimizukenta.secs.secs1;

public class Secs1TimeoutT1Exception extends Secs1Exception {
	
	private static final long serialVersionUID = 8783612996591113782L;
	
	public Secs1TimeoutT1Exception(int position) {
		super("T1-Timeout position=" + position);
	}

}
