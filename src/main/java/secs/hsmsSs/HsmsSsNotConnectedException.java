package secs.hsmsSs;

public class HsmsSsNotConnectedException extends HsmsSsSendMessageException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1699728336797415989L;

	public HsmsSsNotConnectedException() {
		super();
	}

	public HsmsSsNotConnectedException(String message) {
		super(message);
	}

	public HsmsSsNotConnectedException(Throwable cause) {
		super(cause);
	}

	public HsmsSsNotConnectedException(String message, Throwable cause) {
		super(message, cause);
	}

	public HsmsSsNotConnectedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public HsmsSsNotConnectedException(HsmsSsMessage msg) {
		super(msg);
	}

	public HsmsSsNotConnectedException(HsmsSsMessage msg, Throwable cause) {
		super(msg, cause);
	}

}
