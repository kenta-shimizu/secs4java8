package secs;

import java.util.Optional;

public class SecsWaitReplyMessageException extends SecsException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -415832641150415192L;
	
	private SecsMessage secsMessage;
	
	public SecsWaitReplyMessageException() {
		super();
		secsMessage = null;
	}

	public SecsWaitReplyMessageException(String message) {
		super(message);
		secsMessage = null;
	}

	public SecsWaitReplyMessageException(Throwable cause) {
		super(cause);
		secsMessage = null;
	}

	public SecsWaitReplyMessageException(String message, Throwable cause) {
		super(message, cause);
		secsMessage = null;
	}

	public SecsWaitReplyMessageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		secsMessage = null;
	}
	
	public SecsWaitReplyMessageException(SecsMessage primaryMessage) {
		super(primaryMessage.toHeaderBytesString());
		secsMessage = primaryMessage;
	}
	
	public SecsWaitReplyMessageException(SecsMessage primaryMessage, Throwable cause) {
		super(primaryMessage.toHeaderBytesString(), cause);
		secsMessage = primaryMessage;
	}
	
	public Optional<SecsMessage> secsMessage() {
		return secsMessage == null ? Optional.empty() : Optional.of(secsMessage);
	}
}
