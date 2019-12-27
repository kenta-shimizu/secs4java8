package secs;

import java.util.EventListener;

public interface SecsMessagePassThroughListener extends EventListener {
	
	public void passThrough(SecsMessage message);
}
