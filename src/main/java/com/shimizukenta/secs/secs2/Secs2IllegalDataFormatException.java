package com.shimizukenta.secs.secs2;

public class Secs2IllegalDataFormatException extends Secs2Exception {
	
	private static final long serialVersionUID = -2801232481812100207L;
	
	public Secs2IllegalDataFormatException() {
		super();
	}

	public Secs2IllegalDataFormatException(String message) {
		super(message);
	}

	public Secs2IllegalDataFormatException(Throwable cause) {
		super(cause);
	}

	public Secs2IllegalDataFormatException(String message, Throwable cause) {
		super(message, cause);
	}

}
