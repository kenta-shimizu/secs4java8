package secs;

public class SecsSendMessageException extends SecsException {

	private static final long serialVersionUID = 8538385659679021746L;

	public SecsSendMessageException() {
		super();
	}

	public SecsSendMessageException(String message) {
		super(message);
	}

	public SecsSendMessageException(Throwable cause) {
		super(cause);
	}

	public SecsSendMessageException(String message, Throwable cause) {
		super(message, cause);
	}

	public SecsSendMessageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
