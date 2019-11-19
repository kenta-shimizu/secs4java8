package secs;

public interface SecsSendMessageConsumer<T> {
	public void send(T msg) throws SecsSendMessageException, SecsException, InterruptedException;
}
