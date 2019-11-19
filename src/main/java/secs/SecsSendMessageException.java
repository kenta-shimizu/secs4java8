package secs;

import java.util.Optional;

public class SecsSendMessageException extends SecsException {

	private static final long serialVersionUID = 8538385659679021746L;
	
	private SecsMessage secsMessage;

	public SecsSendMessageException() {
		super();
		secsMessage = null;
	}

	public SecsSendMessageException(String message) {
		super(message);
		secsMessage = null;
	}

	public SecsSendMessageException(Throwable cause) {
		super(cause);
		secsMessage = null;
	}

	public SecsSendMessageException(String message, Throwable cause) {
		super(message, cause);
		secsMessage = null;
	}

	public SecsSendMessageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		secsMessage = null;
	}
	
	public SecsSendMessageException(SecsMessage msg) {
		super(msg.toHeaderBytesString());
		secsMessage = msg;
	}
	
	public SecsSendMessageException(SecsMessage msg, Throwable cause) {
		super(msg.toHeaderBytesString(), cause);
		secsMessage = msg;
	}
	
	public Optional<SecsMessage> secsMessage() {
		return secsMessage == null ? Optional.empty() : Optional.of(secsMessage);
	}

}
