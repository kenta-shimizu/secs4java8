package secs;

public class SecsException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6185846155778562687L;

	public SecsException() {
		super();
	}

	public SecsException(String message) {
		super(message);
	}

	public SecsException(Throwable cause) {
		super(cause);
	}

	public SecsException(String message, Throwable cause) {
		super(message, cause);
	}

	public SecsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
