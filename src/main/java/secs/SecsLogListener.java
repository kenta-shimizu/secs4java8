package secs;

import java.util.EventListener;

public interface SecsLogListener extends EventListener {
	
	public void receive(SecsLog log);
}
