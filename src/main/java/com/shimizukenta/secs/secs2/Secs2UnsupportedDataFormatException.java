package com.shimizukenta.secs.secs2;

public class Secs2UnsupportedDataFormatException extends Secs2BytesParseException {
	
	private static final long serialVersionUID = -305868137866435475L;

	public Secs2UnsupportedDataFormatException() {
		super();
	}

	public Secs2UnsupportedDataFormatException(String message) {
		super(message);
	}

	public Secs2UnsupportedDataFormatException(Throwable cause) {
		super(cause);
	}

	public Secs2UnsupportedDataFormatException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
