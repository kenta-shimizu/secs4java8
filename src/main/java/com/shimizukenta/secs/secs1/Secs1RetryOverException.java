package com.shimizukenta.secs.secs1;

public class Secs1RetryOverException extends Secs1SendMessageException {
	
	private static final long serialVersionUID = -4959839153467658571L;
	
	public Secs1RetryOverException(Secs1Message msg) {
		super(msg);
	}
	
	public Secs1RetryOverException(Secs1Message msg, Throwable cause) {
		super(msg, cause);
	}
	
}
